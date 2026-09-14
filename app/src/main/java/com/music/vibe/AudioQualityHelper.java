package com.music.vibe;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class AudioQualityHelper {

	public static String selectBestAudioUrl(JSONArray downloadArray, Context context) {
		if (downloadArray == null || downloadArray.length() == 0) return "";

		String userSetting = "Auto";
		if (context != null) {
			SharedPreferences sp = context.getSharedPreferences("sp", Context.MODE_PRIVATE);
			userSetting = sp.getString("audio_quality", "Auto");
		}

		String targetQuality = userSetting;
		if ("Auto".equalsIgnoreCase(userSetting) || TextUtils.isEmpty(userSetting)) {
			targetQuality = detectAutoQuality(context);
		}

		String fallbackUrl = "";
		for (int i = 0; i < downloadArray.length(); i++) {
			JSONObject obj = downloadArray.optJSONObject(i);
			if (obj == null) continue;
			String quality = obj.optString("quality", "");
			String url = obj.optString("url", "");

			if (!url.isEmpty()) {
				if (fallbackUrl.isEmpty()) fallbackUrl = url;

				if (quality.equalsIgnoreCase(targetQuality)) {
					return url;
				}
			}
		}

		if (downloadArray.length() > 0) {
			JSONObject lastObj = downloadArray.optJSONObject(downloadArray.length() - 1);
			if (lastObj != null) {
				String lastUrl = lastObj.optString("url", "");
				if (!lastUrl.isEmpty()) return lastUrl;
			}
		}

		return fallbackUrl;
	}

	public static String detectAutoQuality(Context context) {
		if (context == null) return "160kbps";

		try {
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (cm != null) {
				NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
				if (capabilities != null) {
					if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
						capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
						return "320kbps";
					}

					if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
						int downstreamKbps = capabilities.getLinkDownstreamBandwidthKbps();
						if (downstreamKbps > 5000) {
							return "320kbps";
						} else if (downstreamKbps > 1500) {
							return "160kbps";
						} else if (downstreamKbps > 500) {
							return "96kbps";
						} else {
							return "48kbps";
						}
					}
				}
			}
		} catch (Exception e) {
			Log.e("AudioQualityHelper", "Error detecting network quality", e);
		}

		return "160kbps";
	}

	public static String getQualityDisplayLabel(String qualityCode) {
		if (qualityCode == null) return "Auto (Network Speed Adaptive)";

		switch (qualityCode) {
			case "320kbps":
				return "High Quality (320 kbps)";
			case "160kbps":
				return "Standard Quality (160 kbps)";
			case "96kbps":
				return "Medium Quality (96 kbps)";
			case "48kbps":
				return "Data Saver (48 kbps)";
			case "12kbps":
				return "Low Quality (12 kbps)";
			case "Auto":
			default:
				return "Auto (Network Speed Adaptive)";
		}
	}
}
