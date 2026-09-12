package com.music.vibe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class DownloadFragmentActivity extends Fragment {

	private LinearLayout linear7;
	private LinearLayout linear8;
	private RecyclerView recyclerview1;
	private LinearLayout linear9;

	private TextView tvSongCount;
	private LinearLayout actionButtonsLayout;
	private LinearLayout emptyStateLayout;
	private LinearLayout btnPlayAll;
	private LinearLayout btnShuffle;

	private SharedPreferences songZEN;
	private SharedPreferences sp;
	private SharedPreferences songZenHome;

	private ArrayList<HashMap<String, Object>> maplist = new ArrayList<>();

	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.download_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}

	private void initialize(Bundle _savedInstanceState, View _view) {
		linear7 = _view.findViewById(R.id.linear7);
		linear8 = _view.findViewById(R.id.linear8);
		recyclerview1 = _view.findViewById(R.id.recyclerview1);
		linear9 = _view.findViewById(R.id.linear9);

		tvSongCount = _view.findViewById(R.id.tvSongCount);
		actionButtonsLayout = _view.findViewById(R.id.actionButtonsLayout);
		emptyStateLayout = _view.findViewById(R.id.emptyStateLayout);
		btnPlayAll = _view.findViewById(R.id.btnPlayAll);
		btnShuffle = _view.findViewById(R.id.btnShuffle);

		Context ctx = getContext();
		if (ctx != null) {
			songZEN = ctx.getSharedPreferences("songZEN", Context.MODE_PRIVATE);
			sp = ctx.getSharedPreferences("sp", Context.MODE_PRIVATE);
			songZenHome = ctx.getSharedPreferences("songZenHome", Context.MODE_PRIVATE);
		}
	}

	private void initializeLogic() {
		Context ctx = getContext();
		if (ctx == null || sp == null) return;
		String songJson = sp.getString("song", "");
		if (!TextUtils.isEmpty(songJson)) {
			try {
				maplist = new Gson().fromJson(songJson, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			} catch (Exception e) {
				maplist = new ArrayList<>();
			}
		}
		if (maplist == null) maplist = new ArrayList<>();

		if (tvSongCount != null) {
			int count = maplist.size();
			tvSongCount.setText(count == 1 ? "1 song available offline" : count + " songs available offline");
		}

		if (maplist.isEmpty()) {
			if (emptyStateLayout != null) emptyStateLayout.setVisibility(View.VISIBLE);
			if (actionButtonsLayout != null) actionButtonsLayout.setVisibility(View.GONE);
			if (recyclerview1 != null) recyclerview1.setVisibility(View.GONE);
		} else {
			if (emptyStateLayout != null) emptyStateLayout.setVisibility(View.GONE);
			if (actionButtonsLayout != null) actionButtonsLayout.setVisibility(View.VISIBLE);
			if (recyclerview1 != null) recyclerview1.setVisibility(View.VISIBLE);
		}

		if (btnPlayAll != null) {
			btnPlayAll.setOnClickListener(v -> {
				if (maplist == null || maplist.isEmpty()) return;
				btnPlayAll.setBackgroundResource(R.drawable.bg_chip_glassy_selected);
				if (btnShuffle != null) btnShuffle.setBackgroundResource(R.drawable.bg_chip_glassy_unselected);
				playSongsWithList(maplist, 0);
			});
		}

		if (btnShuffle != null) {
			btnShuffle.setOnClickListener(v -> {
				if (maplist == null || maplist.isEmpty()) return;
				btnShuffle.setBackgroundResource(R.drawable.bg_chip_glassy_selected);
				if (btnPlayAll != null) btnPlayAll.setBackgroundResource(R.drawable.bg_chip_glassy_unselected);
				ArrayList<HashMap<String, Object>> shuffledList = new ArrayList<>(maplist);
				Collections.shuffle(shuffledList);
				playSongsWithList(shuffledList, 0);
			});
		}

		recyclerview1.setAdapter(new Recyclerview1Adapter(maplist));
		recyclerview1.setLayoutManager(new LinearLayoutManager(ctx));
	}

	private void playSongsWithList(ArrayList<HashMap<String, Object>> list, int position) {
		Context context = getContext();
		if (context == null || list == null || list.isEmpty()) return;
		int playPos = (position >= 0 && position < list.size()) ? position : 0;

		Intent playIntent = new Intent(context, MusicService.class);
		playIntent.setAction("PLAY_NEW");
		playIntent.putExtra("SONG_LIST", list);
		playIntent.putExtra("POSITION", playPos);

		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				context.startForegroundService(playIntent);
			} else {
				context.startService(playIntent);
			}
		} catch (Exception e) {
			Log.e("DownloadFragment", "Error starting MusicService", e);
		}
	}

	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {

		ArrayList<HashMap<String, Object>> _data;

		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}

		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View _v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_download_song, parent, false);
			return new ViewHolder(_v);
		}

		@Override
		public void onBindViewHolder(@NonNull ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;

			final View clickTarget = _view.findViewById(R.id.linear1) != null ? _view.findViewById(R.id.linear1) : _view;
			final ImageView ArtZen = _view.findViewById(R.id.ArtZen);
			final TextView TnameZen = _view.findViewById(R.id.TnameZen);
			final TextView TartistZen = _view.findViewById(R.id.TartistZen);

			if (_data != null && _position >= 0 && _position < _data.size()) {
				HashMap<String, Object> itemMap = _data.get(_position);
				if (itemMap != null) {
					if (itemMap.containsKey("name") && itemMap.get("name") != null) {
						String name = itemMap.get("name").toString();
						TnameZen.setText(name);
						TnameZen.setSingleLine(true);
						TattributeZen(TnameZen);
					}

					if (itemMap.containsKey("artist") && itemMap.get("artist") != null) {
						String artist = itemMap.get("artist").toString();
						TartistZen.setText(artist);
						TartistZen.setSingleLine(true);
						TartistZen.setEllipsize(TextUtils.TruncateAt.END);
					}

					if (itemMap.containsKey("photopath") && itemMap.get("photopath") != null) {
						String album_pic = itemMap.get("photopath").toString();
						if (!album_pic.isEmpty()) {
							Picasso.get()
									.load(Uri.parse(album_pic))
									.error(R.drawable.zenloading_error)
									.config(Bitmap.Config.RGB_565)
									.into(ArtZen);
						}
					}

					clickTarget.setOnClickListener(v -> {
						int pos = _holder.getBindingAdapterPosition();
						if (pos != RecyclerView.NO_POSITION) {
							playSongsWithList(_data, pos);
						}
					});
				}
			}
		}

		private void TattributeZen(TextView textView) {
			textView.setEllipsize(TextUtils.TruncateAt.END);
		}

		@Override
		public int getItemCount() {
			return _data != null ? _data.size() : 0;
		}

		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
}
