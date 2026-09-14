package com.music.vibe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ZensearchFragmentActivity extends Fragment {

	private ArrayList<HashMap<String, Object>> y = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> shimmerListMap = new ArrayList<>();

	private LinearLayout linear6;
	private LinearLayout linear7;
	private LinearLayout Ln_search;
	private ImageView imageview1;
	private EditText edittext1;
	private ImageView clear_btn;
	private LinearLayout linear8;
	private RecyclerView recyclerview1;
	private RecyclerView searchShimmer;
	private LinearLayout emptySearchState;

	private SharedPreferences songZenSearch;
	private RequestNetwork se;
	private RequestNetwork.RequestListener _se_request_listener;

	private final Handler searchHandler = new Handler(Looper.getMainLooper());
	private Runnable searchRunnable;
	private String lastSearchQuery = "";

	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.zensearch_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}

	private void initialize(Bundle _savedInstanceState, View _view) {
		linear6 = _view.findViewById(R.id.linear6);
		linear7 = _view.findViewById(R.id.linear7);
		Ln_search = _view.findViewById(R.id.Ln_search);
		imageview1 = _view.findViewById(R.id.imageview1);
		edittext1 = _view.findViewById(R.id.edittext1);
		clear_btn = _view.findViewById(R.id.clear_btn);
		linear8 = _view.findViewById(R.id.linear8);
		searchShimmer = _view.findViewById(R.id.searchShimmer);
		recyclerview1 = _view.findViewById(R.id.recyclerview1);
		emptySearchState = _view.findViewById(R.id.emptySearchState);

		if (clear_btn != null) {
			clear_btn.setOnClickListener(v -> {
				if (edittext1 != null) {
					edittext1.setText("");
				}
			});
		}

		Context ctx = getContext();
		if (ctx != null) {
			songZenSearch = ctx.getSharedPreferences("songZenSearch", Context.MODE_PRIVATE);
		}
		se = new RequestNetwork(getActivity());

		_se_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				if (!isAdded() || getContext() == null) return;
				try {
					JSONObject main = new JSONObject(_param2);
					JSONArray results = main.getJSONArray("results");
					y.clear();

					for (int i = 0; i < results.length(); i++) {
						HashMap<String, Object> map1 = new HashMap<>();
						JSONObject item = results.getJSONObject(i);
						String title = item.optString("name", "Unknown");

						String artist = "Unknown";
						JSONObject artistsObj = item.optJSONObject("artists");
						if (artistsObj != null) {
							JSONArray primary = artistsObj.optJSONArray("primary");
							if (primary != null && primary.length() > 0) {
								JSONObject artistObj = primary.getJSONObject(0);
								artist = artistObj.optString("name", "Unknown");
							}
						}

						String imageurl = "";
						JSONArray imageArray = item.optJSONArray("image");
						if (imageArray != null && imageArray.length() > 2) {
							JSONObject imageObj = imageArray.getJSONObject(2);
							imageurl = imageObj.optString("url", "");
						}

						String songurl = "";
						JSONArray downloadArray = item.optJSONArray("downloadUrl");
						if (downloadArray != null) {
							songurl = AudioQualityHelper.selectBestAudioUrl(downloadArray, getContext());
						}

						if (songurl.equals("")) continue;

						map1.put("name", title);
						map1.put("artist", artist);
						map1.put("photopath", imageurl);
						map1.put("data", songurl);

						y.add(map1);
					}
				} catch (Exception e) {
					Log.e("Zensearch", "Error parsing search response", e);
				}

				Context context = getContext();
				if (context == null) return;

				if (searchShimmer != null) searchShimmer.setVisibility(View.GONE);

				if (y.isEmpty()) {
					if (emptySearchState != null) emptySearchState.setVisibility(View.VISIBLE);
					if (recyclerview1 != null) recyclerview1.setVisibility(View.GONE);
				} else {
					if (emptySearchState != null) emptySearchState.setVisibility(View.GONE);
					if (recyclerview1 != null) recyclerview1.setVisibility(View.VISIBLE);
				}

				if (recyclerview1 != null) {
					recyclerview1.setAdapter(new Recyclerview1Adapter(y));
					recyclerview1.setLayoutManager(new LinearLayoutManager(context));
				}

				MusicManager manager = MusicManager.getInstance(context);
				manager.addSongs(y);
			}

			@Override
			public void onErrorResponse(String _param1, String _param2) {
				if (!isAdded() || getContext() == null) return;
				if (searchShimmer != null) searchShimmer.setVisibility(View.GONE);
			}
		};

		if (edittext1 != null) {
			edittext1.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
					final String query = _param1.toString().trim();

					if (searchRunnable != null) {
						searchHandler.removeCallbacks(searchRunnable);
					}

					if (!TextUtils.isEmpty(query)) {
						if (clear_btn != null) clear_btn.setVisibility(View.VISIBLE);

						searchRunnable = () -> performSearch(query);
						searchHandler.postDelayed(searchRunnable, 300);
					} else {
						lastSearchQuery = "";
						if (clear_btn != null) clear_btn.setVisibility(View.GONE);
						if (searchShimmer != null) searchShimmer.setVisibility(View.GONE);
						if (emptySearchState != null) emptySearchState.setVisibility(View.VISIBLE);
						if (recyclerview1 != null) recyclerview1.setVisibility(View.GONE);
						y.clear();
						if (recyclerview1 != null && recyclerview1.getAdapter() != null) {
							recyclerview1.getAdapter().notifyDataSetChanged();
						}
					}
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

				@Override
				public void afterTextChanged(Editable s) {}
			});

			edittext1.setOnEditorActionListener((v, actionId, event) -> {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					String query = edittext1.getText().toString().trim();
					if (!TextUtils.isEmpty(query)) {
						if (searchRunnable != null) {
							searchHandler.removeCallbacks(searchRunnable);
						}
						performSearch(query);
						hideKeyboard();
					}
					return true;
				}
				return false;
			});
		}
	}

	private void performSearch(String query) {
		if (query.equals(lastSearchQuery)) return;
		lastSearchQuery = query;

		if (emptySearchState != null) emptySearchState.setVisibility(View.GONE);
		if (recyclerview1 != null) recyclerview1.setVisibility(View.GONE);
		if (searchShimmer != null) searchShimmer.setVisibility(View.VISIBLE);

		HashMap<String, Object> headers = new HashMap<>();
		headers.put("Authorization", "Bearer sk-paxsenix-h-IpQ7TD4Z5s8LS9cOLis9tVkz_AfLWh3vlbmFgqECyr1xYZ");
		headers.put("Content-Type", "application/json");

		se.setHeaders(headers);
		se.startRequestNetwork(RequestNetworkController.GET, "https://api.paxsenix.org/jiosaavn/search?q=" + Uri.encode(query), "", _se_request_listener);
	}

	private void hideKeyboard() {
		if (getActivity() != null && getActivity().getCurrentFocus() != null) {
			InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			if (imm != null) {
				imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
			}
		}
	}

	private void initializeLogic() {
		Context ctx = getContext();
		if (ctx != null) {
			shimmerListMap.clear();
			for (int i = 0; i < 8; i++) {
				shimmerListMap.add(new HashMap<>());
			}

			if (searchShimmer != null) {
				searchShimmer.setAdapter(new SearchShimmerAdapter(shimmerListMap));
				searchShimmer.setLayoutManager(new LinearLayoutManager(ctx));
				searchShimmer.setVisibility(View.GONE);
			}

			if (edittext1 != null) {
				try {
					edittext1.setTypeface(Typeface.createFromAsset(ctx.getAssets(), "fonts/zenlt.ttf"), Typeface.NORMAL);
				} catch (Exception ignored) {}
			}

			if (Ln_search != null) {
				GradientDrawable gd = new GradientDrawable();
				gd.setCornerRadius(24);
				gd.setStroke(1, 0xFF303045);
				gd.setColor(0xFF1E1E2C);
				Ln_search.setBackground(gd);
			}
		}

		if (imageview1 != null) {
			imageview1.setColorFilter(0xFFA0A0B0, PorterDuff.Mode.SRC_IN);
		}

		if (recyclerview1 != null && ctx != null) {
			recyclerview1.setLayoutManager(new LinearLayoutManager(ctx));
		}
	}

	@Override
	public void onDestroyView() {
		if (searchRunnable != null) {
			searchHandler.removeCallbacks(searchRunnable);
		}
		super.onDestroyView();
	}

	public class SearchShimmerAdapter extends RecyclerView.Adapter<SearchShimmerAdapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;

		public SearchShimmerAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}

		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View _v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shimmer_search, parent, false);
			return new ViewHolder(_v);
		}

		@Override
		public void onBindViewHolder(@NonNull ViewHolder _holder, final int _position) {
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

	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {

		ArrayList<HashMap<String, Object>> _data;

		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}

		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View _v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search, parent, false);
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
						TnameZen.setEllipsize(TextUtils.TruncateAt.END);
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
						if (pos != RecyclerView.NO_POSITION && _data != null && pos < _data.size()) {
							Context context = v.getContext();
							Intent playIntent = new Intent(context, MusicService.class);
							playIntent.setAction("PLAY_NEW");
							playIntent.putExtra("SONG_LIST", _data);
							playIntent.putExtra("POSITION", pos);

							try {
								if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
									context.startForegroundService(playIntent);
								} else {
									context.startService(playIntent);
								}
							} catch (Exception e) {
								Log.e("ZensearchFragment", "Error starting MusicService", e);
							}
						}
					});
				}
			}
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
