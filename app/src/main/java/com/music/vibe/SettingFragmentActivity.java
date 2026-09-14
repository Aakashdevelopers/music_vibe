package com.music.vibe;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class SettingFragmentActivity extends Fragment {

	private LinearLayout btnAudioQuality;
	private TextView tvQualitySubtitle;
	private LinearLayout btnCheckUpdate;
	private TextView tvVersionSubtitle;
	private SharedPreferences sp;

	private final String[] qualityCodes = {"Auto", "320kbps", "160kbps", "96kbps", "48kbps", "12kbps"};
	private final String[] qualityTitles = {
			"Auto (Recommended)",
			"Very High Quality",
			"High Quality",
			"Standard Quality",
			"Data Saver",
			"Ultra Low"
	};
	private final String[] qualitySubtitles = {
			"Adaptive • Adjusts based on network speed",
			"320 kbps • Studio quality audio & deep bass",
			"160 kbps • Great balance of fidelity and speed",
			"96 kbps • Faster buffering & smooth playback",
			"48 kbps • Conserves mobile data allowance",
			"12 kbps • Minimal bandwidth usage"
	};
	private final int[] qualityColors = {
			0xFFA78BFA, // Auto: Purple
			0xFF34D399, // 320k: Emerald Green
			0xFF60A5FA, // 160k: Blue
			0xFFFBBF24, // 96k: Amber
			0xFFF87171, // 48k: Red
			0xFF9CA3AF  // 12k: Grey
	};

	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.setting_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}

	private void initialize(Bundle _savedInstanceState, View _view) {
		btnAudioQuality = _view.findViewById(R.id.btnAudioQuality);
		tvQualitySubtitle = _view.findViewById(R.id.tvQualitySubtitle);
		btnCheckUpdate = _view.findViewById(R.id.btnCheckUpdate);
		tvVersionSubtitle = _view.findViewById(R.id.tvVersionSubtitle);

		Context ctx = getContext();
		if (ctx != null) {
			sp = ctx.getSharedPreferences("sp", Context.MODE_PRIVATE);
		}

		if (btnAudioQuality != null) {
			btnAudioQuality.setOnClickListener(v -> showQualityDialog());
		}

		if (btnCheckUpdate != null) {
			btnCheckUpdate.setOnClickListener(v -> {
				if (getActivity() != null) {
					GitHubUpdateManager.checkUpdate(getActivity(), true);
				}
			});
		}
	}

	private void initializeLogic() {
		updateQualitySubtitle();
		updateVersionSubtitle();
	}

	private void updateVersionSubtitle() {
		if (tvVersionSubtitle != null && getContext() != null) {
			String currentVer = GitHubUpdateManager.getCurrentAppVersion(getContext());
			tvVersionSubtitle.setText("Version " + currentVer + " • Tap to check GitHub");
		}
	}

	private void updateQualitySubtitle() {
		if (sp != null && tvQualitySubtitle != null) {
			String currentQuality = sp.getString("audio_quality", "Auto");
			tvQualitySubtitle.setText(AudioQualityHelper.getQualityDisplayLabel(currentQuality));
		}
	}

	private void showQualityDialog() {
		Context ctx = getContext();
		if (ctx == null || sp == null) return;

		String currentQuality = sp.getString("audio_quality", "Auto");

		BottomSheetDialog dialog = new BottomSheetDialog(ctx);
		View sheetView = LayoutInflater.from(ctx).inflate(R.layout.dialog_audio_quality, null);
		dialog.setContentView(sheetView);

		if (dialog.getWindow() != null) {
			dialog.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet)
					.setBackground(new ColorDrawable(Color.TRANSPARENT));
		}

		LinearLayout optionsContainer = sheetView.findViewById(R.id.optionsContainer);
		if (optionsContainer != null) {
			optionsContainer.removeAllViews();

			for (int i = 0; i < qualityCodes.length; i++) {
				final String code = qualityCodes[i];
				final String title = qualityTitles[i];
				final String subtitle = qualitySubtitles[i];
				final int color = qualityColors[i];

				View itemView = LayoutInflater.from(ctx).inflate(R.layout.item_audio_quality, optionsContainer, false);

				TextView tvTitle = itemView.findViewById(R.id.tvOptionTitle);
				TextView tvSub = itemView.findViewById(R.id.tvOptionSubtitle);
				ImageView imgBadge = itemView.findViewById(R.id.imgOptionBadge);
				ImageView imgCheck = itemView.findViewById(R.id.imgCheck);

				if (tvTitle != null) tvTitle.setText(title);
				if (tvSub != null) tvSub.setText(subtitle);
				if (imgBadge != null) imgBadge.setColorFilter(color);

				boolean isSelected = code.equalsIgnoreCase(currentQuality);
				if (isSelected) {
					itemView.setBackgroundResource(R.drawable.bg_quality_item_selected);
					if (imgCheck != null) {
						imgCheck.setVisibility(View.VISIBLE);
						imgCheck.setColorFilter(color);
					}
				} else {
					itemView.setBackgroundResource(R.drawable.bg_quality_item_unselected);
					if (imgCheck != null) {
						imgCheck.setVisibility(View.GONE);
					}
				}

				itemView.setOnClickListener(v -> {
					sp.edit().putString("audio_quality", code).apply();
					updateQualitySubtitle();
					dialog.dismiss();
				});

				optionsContainer.addView(itemView);
			}
		}

		dialog.show();
	}
}
