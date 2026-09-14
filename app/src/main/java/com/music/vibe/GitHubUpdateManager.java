package com.music.vibe;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GitHubUpdateManager {

	// Default Repository Owner and Name
	public static final String DEFAULT_OWNER = "Aakashdevelopers"; 
	public static final String DEFAULT_REPO = "music_vibe";

	public static void checkUpdate(final Activity activity, final boolean showToastIfLatest) {
		checkUpdate(activity, DEFAULT_OWNER, DEFAULT_REPO, showToastIfLatest);
	}

	public static void checkUpdate(final Activity activity, final String owner, final String repo, final boolean showToastIfLatest) {
		if (activity == null || activity.isFinishing()) return;

		final ProgressDialog progressDialog = new ProgressDialog(activity);
		progressDialog.setMessage("Checking for updates...");
		progressDialog.setCancelable(false);
		progressDialog.show();

		String url = "https://api.github.com/repos/" + owner + "/" + repo + "/releases/latest";

		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url(url)
				.addHeader("Accept", "application/vnd.github.v3+json")
				.addHeader("User-Agent", "MusicVibe-App")
				.build();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(@NonNull Call call, @NonNull final IOException e) {
				activity.runOnUiThread(() -> {
					dismissProgress(progressDialog);
					if (showToastIfLatest) {
						Toast.makeText(activity, "Failed to check for updates: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
					}
				});
			}

			@Override
			public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
				final String jsonResponse = response.body() != null ? response.body().string() : "";
				
				activity.runOnUiThread(() -> {
					dismissProgress(progressDialog);
					try {
						if (!response.isSuccessful() || TextUtils.isEmpty(jsonResponse)) {
							if (showToastIfLatest) {
								Toast.makeText(activity, "No releases found or invalid repo", Toast.LENGTH_SHORT).show();
							}
							return;
						}

						JSONObject releaseObj = new JSONObject(jsonResponse);
						String tagName = releaseObj.optString("tag_name", "");
						String releaseName = releaseObj.optString("name", "New Update");
						String releaseNotes = releaseObj.optString("body", "No release notes available.");
						String htmlUrl = releaseObj.optString("html_url", "");

						// Find APK download URL from assets
						String downloadUrl = htmlUrl; // Fallback to release page
						JSONArray assets = releaseObj.optJSONArray("assets");
						if (assets != null) {
							for (int i = 0; i < assets.length(); i++) {
								JSONObject asset = assets.getJSONObject(i);
								String name = asset.optString("name", "").toLowerCase();
								if (name.endsWith(".apk")) {
									downloadUrl = asset.optString("browser_download_url", htmlUrl);
									break;
								}
							}
						}

						String currentVersion = getCurrentAppVersion(activity);
						String cleanLatestVersion = cleanVersionString(tagName);
						String cleanCurrentVersion = cleanVersionString(currentVersion);

						if (isNewVersionAvailable(cleanCurrentVersion, cleanLatestVersion)) {
							showUpdateDialog(activity, cleanLatestVersion, releaseName, releaseNotes, downloadUrl);
						} else {
							if (showToastIfLatest) {
								Toast.makeText(activity, "You are using the latest version (v" + currentVersion + ")", Toast.LENGTH_SHORT).show();
							}
						}

					} catch (Exception e) {
						if (showToastIfLatest) {
							Toast.makeText(activity, "Error parsing update info", Toast.LENGTH_SHORT).show();
						}
					}
				});
			}
		});
	}

	private static void dismissProgress(ProgressDialog progressDialog) {
		if (progressDialog != null && progressDialog.isShowing()) {
			try {
				progressDialog.dismiss();
			} catch (Exception ignored) {
			}
		}
	}

	public static String getCurrentAppVersion(Context context) {
		try {
			PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pInfo.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			return "1.0";
		}
	}

	public static String cleanVersionString(String version) {
		if (TextUtils.isEmpty(version)) return "0";
		return version.replaceAll("(?i)^v", "").trim();
	}

	public static boolean isNewVersionAvailable(String currentVersion, String latestVersion) {
		if (TextUtils.isEmpty(latestVersion)) return false;
		if (TextUtils.isEmpty(currentVersion)) return true;

		String[] currentParts = currentVersion.split("\\.");
		String[] latestParts = latestVersion.split("\\.");

		int length = Math.max(currentParts.length, latestParts.length);
		for (int i = 0; i < length; i++) {
			int currentPart = i < currentParts.length ? parseVersionPart(currentParts[i]) : 0;
			int latestPart = i < latestParts.length ? parseVersionPart(latestParts[i]) : 0;

			if (latestPart > currentPart) {
				return true;
			} else if (latestPart < currentPart) {
				return false;
			}
		}
		return false;
	}

	private static int parseVersionPart(String part) {
		try {
			return Integer.parseInt(part.replaceAll("[^0-9]", ""));
		} catch (Exception e) {
			return 0;
		}
	}

	private static void showUpdateDialog(final Activity activity, String versionTag, String title, String changelog, final String downloadUrl) {
		if (activity == null || activity.isFinishing()) return;

		BottomSheetDialog dialog = new BottomSheetDialog(activity);
		View sheetView = LayoutInflater.from(activity).inflate(R.layout.dialog_github_update, null);
		dialog.setContentView(sheetView);

		if (dialog.getWindow() != null) {
			dialog.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet)
					.setBackground(new ColorDrawable(Color.TRANSPARENT));
		}

		TextView tvUpdateTitle = sheetView.findViewById(R.id.tvUpdateTitle);
		TextView tvUpdateVersion = sheetView.findViewById(R.id.tvUpdateVersion);
		TextView tvChangelog = sheetView.findViewById(R.id.tvChangelog);
		TextView btnDownload = sheetView.findViewById(R.id.btnDownloadUpdate);
		TextView btnDismiss = sheetView.findViewById(R.id.btnDismissUpdate);

		if (tvUpdateTitle != null && !TextUtils.isEmpty(title)) {
			tvUpdateTitle.setText(title);
		}
		if (tvUpdateVersion != null) {
			tvUpdateVersion.setText("Version v" + versionTag + " is now available");
		}
		if (tvChangelog != null && !TextUtils.isEmpty(changelog)) {
			tvChangelog.setText(changelog);
		}

		if (btnDownload != null) {
			btnDownload.setOnClickListener(v -> {
				dialog.dismiss();
				startDirectDownload(activity, downloadUrl);
			});
		}

		if (btnDismiss != null) {
			btnDismiss.setOnClickListener(v -> dialog.dismiss());
		}

		dialog.show();
	}

	private static void startDirectDownload(final Activity activity, final String downloadUrl) {
		if (activity == null || activity.isFinishing()) return;

		// Check permission for Android 8.0 (API 26) or higher
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			if (!activity.getPackageManager().canRequestPackageInstalls()) {
				Toast.makeText(activity, "Please allow 'Install unknown apps' permission to update.", Toast.LENGTH_LONG).show();
				try {
					Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + activity.getPackageName()));
					activity.startActivity(intent);
				} catch (Exception e) {
					Toast.makeText(activity, "Permission required: Install unknown apps", Toast.LENGTH_SHORT).show();
				}
				return;
			}
		}

		// Modern Glassy BottomSheet Dialog for Download Progress
		BottomSheetDialog downloadDialog = new BottomSheetDialog(activity);
		View progressView = LayoutInflater.from(activity).inflate(R.layout.dialog_download_progress, null);
		downloadDialog.setContentView(progressView);
		downloadDialog.setCancelable(false);

		if (downloadDialog.getWindow() != null) {
			downloadDialog.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet)
					.setBackground(new ColorDrawable(Color.TRANSPARENT));
		}

		ProgressBar progressBar = progressView.findViewById(R.id.progressBarUpdate);
		TextView tvPercent = progressView.findViewById(R.id.tvProgressPercent);
		TextView tvSize = progressView.findViewById(R.id.tvProgressSize);

		downloadDialog.show();

		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url(downloadUrl)
				.addHeader("User-Agent", "MusicVibe-App")
				.build();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(@NonNull Call call, @NonNull IOException e) {
				activity.runOnUiThread(() -> {
					dismissBottomSheet(downloadDialog);
					Toast.makeText(activity, "Download failed: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
				});
			}

			@Override
			public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
				if (!response.isSuccessful() || response.body() == null) {
					activity.runOnUiThread(() -> {
						dismissBottomSheet(downloadDialog);
						Toast.makeText(activity, "Download failed (Server error)", Toast.LENGTH_SHORT).show();
					});
					return;
				}

				File downloadsDir = activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
				if (downloadsDir != null && !downloadsDir.exists()) {
					downloadsDir.mkdirs();
				}
				final File apkFile = new File(downloadsDir, "music_vibe_update.apk");

				try (InputStream inputStream = response.body().byteStream();
					 FileOutputStream outputStream = new FileOutputStream(apkFile)) {

					long totalBytes = response.body().contentLength();
					byte[] buffer = new byte[8192];
					long bytesDownloaded = 0;
					int read;

					while ((read = inputStream.read(buffer)) != -1) {
						outputStream.write(buffer, 0, read);
						bytesDownloaded += read;

						final long finalBytesDownloaded = bytesDownloaded;
						if (totalBytes > 0) {
							final int progress = (int) ((finalBytesDownloaded * 100) / totalBytes);
							final float currentMB = finalBytesDownloaded / (1024f * 1024f);
							final float totalMB = totalBytes / (1024f * 1024f);

							activity.runOnUiThread(() -> {
								if (progressBar != null) progressBar.setProgress(progress);
								if (tvPercent != null) tvPercent.setText(progress + "%");
								if (tvSize != null) {
									tvSize.setText(String.format(Locale.US, "%.1f MB / %.1f MB", currentMB, totalMB));
								}
							});
						} else {
							final float currentMB = finalBytesDownloaded / (1024f * 1024f);
							activity.runOnUiThread(() -> {
								if (tvSize != null) {
									tvSize.setText(String.format(Locale.US, "%.1f MB", currentMB));
								}
							});
						}
					}

					outputStream.flush();

					activity.runOnUiThread(() -> {
						dismissBottomSheet(downloadDialog);
						installApk(activity, apkFile);
					});

				} catch (Exception e) {
					activity.runOnUiThread(() -> {
						dismissBottomSheet(downloadDialog);
						Toast.makeText(activity, "Error saving update file: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
					});
				}
			}
		});
	}

	private static void dismissBottomSheet(BottomSheetDialog dialog) {
		if (dialog != null && dialog.isShowing()) {
			try {
				dialog.dismiss();
			} catch (Exception ignored) {
			}
		}
	}

	public static void installApk(Activity activity, File apkFile) {
		if (activity == null || apkFile == null || !apkFile.exists()) return;

		try {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			Uri apkUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", apkFile);
			intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			activity.startActivity(intent);
		} catch (Exception e) {
			Toast.makeText(activity, "Failed to launch installer: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
		}
	}
}
