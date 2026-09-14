package com.music.vibe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
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
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.FirebaseApp;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ZenhomeFragmentActivity extends Fragment {

	private ArrayList<HashMap<String, Object>> stori = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> chipSongs = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> y = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> ins = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> nso = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> ShimmerListMap = new ArrayList<>();

	private NestedScrollView mainScrollBar;
	private LinearLayout body;
	private LinearLayout QuickPicksMusicsLayout;
	private LinearLayout RecommendedAlbumsLayout;
	private LinearLayout MusicVideosLayout;
	private LinearLayout TopArtistsLayout;
	private LinearLayout QuickPicksMusicsLayoutTop;
	private RecyclerView QuickPicksMusicsLayoutShimmer;
	private RecyclerView QuickPicksShimmer;
	private RecyclerView recyclerviewQuickPicks;
	private RecyclerView recyclerview1;
	private TextView QuickPicksMusicsLayoutTopTitle;
	private LinearLayout RecommendedAlbumsLayoutTop;
	private RecyclerView RecommendedAlbumsLayoutShimmer;
	private RecyclerView recyclerview2;
	private TextView RecommendedAlbumsLayoutTopTitle;
	private LinearLayout MusicVideosLayoutTop;
	private RecyclerView MusicVideosLayoutShimmer;
	private RecyclerView recyclerview3;
	private TextView MusicVideosLayoutTopTitle;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private RecyclerView recyclerview4;
	private RecyclerView recyclerview5;
	private LinearLayout linear7;
	private TextView textview1;

	private TextView chipTrending;
	private TextView chipEnergise;
	private TextView chipFeelGood;
	private TextView chipRelax;
	private TextView chipPodcasts;
	private TextView chipHindi;
	private TextView chipBhojpuri;
	private TextView chipHaryanvi;
	private List<TextView> chipList = new ArrayList<>();

	private RequestNetwork storys;
	private RequestNetwork.RequestListener _storys_request_listener;
	private RequestNetwork chipRequest;
	private RequestNetwork.RequestListener _chip_request_listener;
	private RequestNetwork ts;
	private RequestNetwork.RequestListener _ts_request_listener;
	private RequestNetwork instaviral;
	private RequestNetwork.RequestListener _instaviral_request_listener;
	private RequestNetwork ns;
	private RequestNetwork.RequestListener _ns_request_listener;

	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.zenhome_fragment, _container, false);

			initialize(_savedInstanceState, _view);
			initializeLogic();
			return _view;
	}

	private void initialize(Bundle _savedInstanceState, View _view) {
		mainScrollBar = _view.findViewById(R.id.mainScrollBar);
		body = _view.findViewById(R.id.body);
		QuickPicksMusicsLayout = _view.findViewById(R.id.QuickPicksMusicsLayout);
		RecommendedAlbumsLayout = _view.findViewById(R.id.RecommendedAlbumsLayout);
		MusicVideosLayout = _view.findViewById(R.id.MusicVideosLayout);
		TopArtistsLayout = _view.findViewById(R.id.TopArtistsLayout);
		QuickPicksMusicsLayoutTop = _view.findViewById(R.id.QuickPicksMusicsLayoutTop);
		QuickPicksMusicsLayoutShimmer = _view.findViewById(R.id.HindiSongsShimmer);
		QuickPicksShimmer = _view.findViewById(R.id.QuickPicksShimmer);
		recyclerviewQuickPicks = _view.findViewById(R.id.recyclerviewQuickPicks);
		recyclerview1 = _view.findViewById(R.id.recyclerview1);
		QuickPicksMusicsLayoutTopTitle = _view.findViewById(R.id.QuickPicksMusicsLayoutTopTitle);
		RecommendedAlbumsLayoutTop = _view.findViewById(R.id.RecommendedAlbumsLayoutTop);
		RecommendedAlbumsLayoutShimmer = _view.findViewById(R.id.RecommendedAlbumsLayoutShimmer);
		recyclerview2 = _view.findViewById(R.id.recyclerview2);
		RecommendedAlbumsLayoutTopTitle = _view.findViewById(R.id.RecommendedAlbumsLayoutTopTitle);
		MusicVideosLayoutTop = _view.findViewById(R.id.MusicVideosLayoutTop);
		MusicVideosLayoutShimmer = _view.findViewById(R.id.MusicVideosLayoutShimmer);
		recyclerview3 = _view.findViewById(R.id.recyclerview3);
		MusicVideosLayoutTopTitle = _view.findViewById(R.id.MusicVideosLayoutTopTitle);
		linear4 = _view.findViewById(R.id.linear4);
		linear5 = _view.findViewById(R.id.linear5);
		linear6 = _view.findViewById(R.id.linear6);
		recyclerview4 = _view.findViewById(R.id.recyclerview4);
		recyclerview5 = _view.findViewById(R.id.recyclerview5);
		linear7 = _view.findViewById(R.id.linear7);
		textview1 = _view.findViewById(R.id.textview1);

		chipTrending = _view.findViewById(R.id.chip_trending);
		chipEnergise = _view.findViewById(R.id.chip_energise);
		chipFeelGood = _view.findViewById(R.id.chip_feel_good);
		chipRelax = _view.findViewById(R.id.chip_relax);
		chipPodcasts = _view.findViewById(R.id.chip_podcasts);
		chipHindi = _view.findViewById(R.id.chip_hindi);
		chipBhojpuri = _view.findViewById(R.id.chip_bhojpuri);
		chipHaryanvi = _view.findViewById(R.id.chip_haryanvi);

		chipList.clear();
		if (chipTrending != null) chipList.add(chipTrending);
		if (chipEnergise != null) chipList.add(chipEnergise);
		if (chipFeelGood != null) chipList.add(chipFeelGood);
		if (chipRelax != null) chipList.add(chipRelax);
		if (chipPodcasts != null) chipList.add(chipPodcasts);
		if (chipHindi != null) chipList.add(chipHindi);
		if (chipBhojpuri != null) chipList.add(chipBhojpuri);
		if (chipHaryanvi != null) chipList.add(chipHaryanvi);

		setupChipClickListeners();

		storys = new RequestNetwork(getActivity());
		chipRequest = new RequestNetwork(getActivity());
		ts = new RequestNetwork(getActivity());
		instaviral = new RequestNetwork(getActivity());
		ns = new RequestNetwork(getActivity());


		_storys_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				if (!isAdded() || getContext() == null) return;
				try {
					JSONObject main = new JSONObject(_param2);
					JSONArray results = main.getJSONArray("results");
					stori.clear();

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

						stori.add(map1);
					}
				} catch (Exception e) {
					Log.e("Zenhome", "Error parsing storys response", e);
				}

				Context ctx = getContext();
				if (ctx == null) return;
				recyclerview1.setAdapter(new Recyclerview1Adapter(stori));
				recyclerview1.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false));
				QuickPicksMusicsLayoutShimmer.setVisibility(View.GONE);
				recyclerview1.setVisibility(View.VISIBLE);

				if (QuickPicksShimmer != null) QuickPicksShimmer.setVisibility(View.GONE);
				if (recyclerviewQuickPicks != null) {
					recyclerviewQuickPicks.setAdapter(new QuickPicksAdapter(stori));
					recyclerviewQuickPicks.setLayoutManager(new GridLayoutManager(ctx, 2));
					recyclerviewQuickPicks.setNestedScrollingEnabled(false);
					recyclerviewQuickPicks.setVisibility(View.VISIBLE);
				}

				MusicManager manager = MusicManager.getInstance(ctx);
				manager.addSongs(stori);
			}

			@Override
			public void onErrorResponse(String _param1, String _param2) {
				if (!isAdded() || getContext() == null) return;
				QuickPicksMusicsLayoutShimmer.setVisibility(View.GONE);
			}
		};

		_chip_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				if (!isAdded() || getContext() == null) return;
				try {
					JSONObject main = new JSONObject(_param2);
					JSONArray results = main.getJSONArray("results");
					chipSongs.clear();

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

						chipSongs.add(map1);
					}
				} catch (Exception e) {
					Log.e("Zenhome", "Error parsing chip response", e);
				}

				Context ctx = getContext();
				if (ctx == null) return;

				if (QuickPicksShimmer != null) QuickPicksShimmer.setVisibility(View.GONE);
				if (recyclerviewQuickPicks != null) {
					recyclerviewQuickPicks.setAdapter(new QuickPicksAdapter(chipSongs));
					recyclerviewQuickPicks.setLayoutManager(new GridLayoutManager(ctx, 2));
					recyclerviewQuickPicks.setNestedScrollingEnabled(false);
					recyclerviewQuickPicks.setVisibility(View.VISIBLE);
				}

				MusicManager manager = MusicManager.getInstance(ctx);
				manager.addSongs(chipSongs);
			}

			@Override
			public void onErrorResponse(String _param1, String _param2) {
				if (!isAdded() || getContext() == null) return;
				if (QuickPicksShimmer != null) QuickPicksShimmer.setVisibility(View.GONE);
			}
		};

		_ts_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				if (!isAdded() || getContext() == null) return;
				try {
					JSONObject main = new JSONObject(_param2);
					JSONArray results = main.getJSONArray("results");
					y.clear();

					for (int i = 0; i < results.length(); i++) {
						HashMap<String, Object> map2 = new HashMap<>();
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

						map2.put("name", title);
						map2.put("artist", artist);
						map2.put("photopath", imageurl);
						map2.put("data", songurl);

						y.add(map2);
					}
				} catch (Exception e) {
					Log.e("Zenhome", "Error parsing ts response", e);
				}

				Context ctx = getContext();
				if (ctx == null) return;
				Collections.shuffle(y);
				recyclerview2.setAdapter(new Recyclerview2Adapter(y));
				recyclerview2.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false));
				RecommendedAlbumsLayoutShimmer.setVisibility(View.GONE);
				recyclerview2.setVisibility(View.VISIBLE);

				MusicManager manager = MusicManager.getInstance(ctx);
				manager.addSongs(y);
			}

			@Override
			public void onErrorResponse(String _param1, String _param2) {
				if (!isAdded() || getContext() == null) return;
				RecommendedAlbumsLayoutShimmer.setVisibility(View.GONE);
			}
		};

		_instaviral_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				if (!isAdded() || getContext() == null) return;
				try {
					JSONObject main = new JSONObject(_param2);
					JSONArray results = main.getJSONArray("results");
					ins.clear();

					for (int i = 0; i < results.length(); i++) {
						HashMap<String, Object> map3 = new HashMap<>();
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

						map3.put("name", title);
						map3.put("artist", artist);
						map3.put("photopath", imageurl);
						map3.put("data", songurl);

						ins.add(map3);
					}
				} catch (Exception e) {
					Log.e("Zenhome", "Error parsing instaviral response", e);
				}

				Context ctx = getContext();
				if (ctx == null) return;
				Collections.shuffle(ins);
				recyclerview3.setAdapter(new Recyclerview3Adapter(ins));
				recyclerview3.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false));
				MusicVideosLayoutShimmer.setVisibility(View.GONE);
				recyclerview3.setVisibility(View.VISIBLE);

				MusicManager manager = MusicManager.getInstance(ctx);
				manager.addSongs(ins);
			}

			@Override
			public void onErrorResponse(String _param1, String _param2) {
				if (!isAdded() || getContext() == null) return;
				MusicVideosLayoutShimmer.setVisibility(View.GONE);
			}
		};

		_ns_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				if (!isAdded() || getContext() == null) return;
				try {
					JSONObject main = new JSONObject(_param2);
					JSONArray results = main.getJSONArray("results");
					nso.clear();

					for (int i = 0; i < results.length(); i++) {
						HashMap<String, Object> map4 = new HashMap<>();
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

						map4.put("name", title);
						map4.put("artist", artist);
						map4.put("photopath", imageurl);
						map4.put("data", songurl);

						nso.add(map4);
					}
				} catch (Exception e) {
					Log.e("Zenhome", "Error parsing ns response", e);
				}

				Context ctx = getContext();
				if (ctx == null) return;
				recyclerview5.setAdapter(new Recyclerview5Adapter(nso));
				recyclerview5.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false));
				recyclerview4.setVisibility(View.GONE);
				recyclerview5.setVisibility(View.VISIBLE);

				MusicManager manager = MusicManager.getInstance(ctx);
				manager.addSongs(nso);
			}

			@Override
			public void onErrorResponse(String _param1, String _param2) {
				if (!isAdded() || getContext() == null) return;
				recyclerview4.setVisibility(View.GONE);
			}
		};
	}

	private void setupChipClickListeners() {
		if (chipTrending != null) chipTrending.setOnClickListener(v -> selectChip(chipTrending, "bollywood song"));
		if (chipEnergise != null) chipEnergise.setOnClickListener(v -> selectChip(chipEnergise, "energise workout songs"));
		if (chipFeelGood != null) chipFeelGood.setOnClickListener(v -> selectChip(chipFeelGood, "feel good songs"));
		if (chipRelax != null) chipRelax.setOnClickListener(v -> selectChip(chipRelax, "relax lofi music"));
		if (chipPodcasts != null) chipPodcasts.setOnClickListener(v -> selectChip(chipPodcasts, "podcasts devotional"));
		if (chipHindi != null) chipHindi.setOnClickListener(v -> selectChip(chipHindi, "latest hindi songs"));
		if (chipBhojpuri != null) chipBhojpuri.setOnClickListener(v -> selectChip(chipBhojpuri, "bhojpuri hits"));
		if (chipHaryanvi != null) chipHaryanvi.setOnClickListener(v -> selectChip(chipHaryanvi, "haryanvi songs"));
	}

	private void selectChip(TextView selectedChip, String query) {
		for (TextView chip : chipList) {
			if (chip == selectedChip) {
				chip.setBackgroundResource(R.drawable.bg_chip_glassy_selected);
				chip.setTextColor(0xFFFFFFFF);
				chip.setTypeface(null, Typeface.BOLD);
			} else {
				chip.setBackgroundResource(R.drawable.bg_chip_glassy_unselected);
				chip.setTextColor(0xFFE0E0F0);
				chip.setTypeface(null, Typeface.NORMAL);
			}
		}

		if (QuickPicksShimmer != null && recyclerviewQuickPicks != null) {
			QuickPicksShimmer.setVisibility(View.VISIBLE);
			recyclerviewQuickPicks.setVisibility(View.GONE);
		}

		HashMap<String, Object> headers = new HashMap<>();
		headers.put("Authorization", "Bearer sk-paxsenix-h-IpQ7TD4Z5s8LS9cOLis9tVkz_AfLWh3vlbmFgqECyr1xYZ");
		headers.put("Content-Type", "application/json");

		chipRequest.setHeaders(headers);
		chipRequest.startRequestNetwork(RequestNetworkController.GET, "https://api.paxsenix.org/jiosaavn/search?q=" + Uri.encode(query), "chip", _chip_request_listener);
	}

	private void initializeLogic() {
		Context ctx = getContext();
		if (ctx == null) return;

		for (int _repeat50 = 0; _repeat50 < 10; _repeat50++) {
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("", "");
			ShimmerListMap.add(_item);
		}

		if (QuickPicksShimmer != null) {
			QuickPicksShimmer.setAdapter(new QuickPicksShimmerAdapter(ShimmerListMap));
			QuickPicksShimmer.setLayoutManager(new GridLayoutManager(ctx, 2));
			QuickPicksShimmer.setNestedScrollingEnabled(false);
			QuickPicksShimmer.setVisibility(View.VISIBLE);
		}

		if (QuickPicksMusicsLayoutShimmer != null) {
			QuickPicksMusicsLayoutShimmer.setAdapter(new QuickPicksMusicsLayoutShimmerAdapter(ShimmerListMap));
			QuickPicksMusicsLayoutShimmer.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false));
			QuickPicksMusicsLayoutShimmer.setVisibility(View.VISIBLE);
		}

		if (RecommendedAlbumsLayoutShimmer != null) {
			RecommendedAlbumsLayoutShimmer.setAdapter(new RecommendedAlbumsLayoutShimmerAdapter(ShimmerListMap));
			RecommendedAlbumsLayoutShimmer.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false));
			RecommendedAlbumsLayoutShimmer.setVisibility(View.VISIBLE);
		}

		if (MusicVideosLayoutShimmer != null) {
			MusicVideosLayoutShimmer.setAdapter(new MusicVideosLayoutShimmerAdapter(ShimmerListMap));
			MusicVideosLayoutShimmer.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false));
			MusicVideosLayoutShimmer.setVisibility(View.VISIBLE);
		}

		if (recyclerview4 != null) {
			recyclerview4.setAdapter(new Recyclerview4Adapter(ShimmerListMap));
			recyclerview4.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false));
			recyclerview4.setVisibility(View.VISIBLE);
		}

		if (recyclerview1 != null) recyclerview1.setVisibility(View.GONE);
		if (recyclerview2 != null) recyclerview2.setVisibility(View.GONE);
		if (recyclerview3 != null) recyclerview3.setVisibility(View.GONE);
		if (recyclerview5 != null) recyclerview5.setVisibility(View.GONE);

		loadAllSections();
	}

	private void loadAllSections() {
		HashMap<String, Object> headers = new HashMap<>();
		headers.put("Authorization", "Bearer sk-paxsenix-h-IpQ7TD4Z5s8LS9cOLis9tVkz_AfLWh3vlbmFgqECyr1xYZ");
		headers.put("Content-Type", "application/json");

		// Section 1: Quick Picks & Top Hindi Hits
		storys.setHeaders(headers);
		storys.startRequestNetwork(RequestNetworkController.GET, "https://api.paxsenix.org/jiosaavn/search?q=latest+hindi+songs", "r", _storys_request_listener);

		// Section 2: Bhojpuri Hits
		ts.setHeaders(headers);
		ts.startRequestNetwork(RequestNetworkController.GET, "https://api.paxsenix.org/jiosaavn/search?q=bhojpuri+hits", "ts", _ts_request_listener);

		// Section 3: Haryanvi Beats
		instaviral.setHeaders(headers);
		instaviral.startRequestNetwork(RequestNetworkController.GET, "https://api.paxsenix.org/jiosaavn/search?q=haryanvi+beats", "insta", _instaviral_request_listener);

		// Section 4: Top India Charts
		ns.setHeaders(headers);
		ns.startRequestNetwork(RequestNetworkController.GET, "https://api.paxsenix.org/jiosaavn/search?q=top+india+songs", "ns", _ns_request_listener);
	}

	public void _locate_vrb() {
		HashMap<String, Object> headers1 = new HashMap<>();
		headers1.put("Authorization", "Bearer sk-paxsenix-h-IpQ7TD4Z5s8LS9cOLis9tVkz_AfLWh3vlbmFgqECyr1xYZ");
		headers1.put("Content-Type", "application/json");

		ns.setHeaders(headers1);
		ns.startRequestNetwork(
				RequestNetworkController.GET,
				"https://api.paxsenix.org/jiosaavn/search?q=top+song",
				"",
				_ns_request_listener
		);
	}

	private String getPlayCountFormatted(int pos) {
		String[] counts = {"30m plays", "37k plays", "988k plays", "6.5m plays", "1.2m plays", "450k plays", "2.8m plays", "820k plays", "14m plays", "520k plays"};
		return counts[Math.abs(pos) % counts.length];
	}

	public class QuickPicksAdapter extends Adapter<QuickPicksAdapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;

		public QuickPicksAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}

		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View _v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quick_pick, parent, false);
			return new ViewHolder(_v);
		}

		@Override
		public void onBindViewHolder(@NonNull ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			final View linear1 = _view.findViewById(R.id.linear1);
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
						String plays = getPlayCountFormatted(_position);
						TartistZen.setText(artist + " • " + plays);
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

					View clickTarget = linear1 != null ? linear1 : _view;
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
								Log.e("ZenhomeFragment", "Error starting MusicService", e);
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

	public class QuickPicksShimmerAdapter extends Adapter<QuickPicksShimmerAdapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;

		public QuickPicksShimmerAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}

		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View _v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shimmer_quick_pick, parent, false);
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

	public class QuickPicksMusicsLayoutShimmerAdapter extends Adapter<QuickPicksMusicsLayoutShimmerAdapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;

		public QuickPicksMusicsLayoutShimmerAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}

		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View _v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shimmer, parent, false);
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

	public class Recyclerview1Adapter extends Adapter<Recyclerview1Adapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;

		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}

		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View _v = LayoutInflater.from(parent.getContext()).inflate(R.layout.zenlistmusic, parent, false);
			return new ViewHolder(_v);
		}

		@Override
		public void onBindViewHolder(@NonNull ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
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

					linear1.setOnClickListener(v -> {
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
								Log.e("ZenhomeFragment", "Error starting MusicService", e);
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

	public class RecommendedAlbumsLayoutShimmerAdapter extends Adapter<RecommendedAlbumsLayoutShimmerAdapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;

		public RecommendedAlbumsLayoutShimmerAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}

		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View _v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shimmer, parent, false);
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

	public class Recyclerview2Adapter extends Adapter<Recyclerview2Adapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;

		public Recyclerview2Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}

		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View _v = LayoutInflater.from(parent.getContext()).inflate(R.layout.zenlistmusic, parent, false);
			return new ViewHolder(_v);
		}

		@Override
		public void onBindViewHolder(@NonNull ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
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

					linear1.setOnClickListener(v -> {
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
								Log.e("ZenhomeFragment", "Error starting MusicService", e);
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

	public class MusicVideosLayoutShimmerAdapter extends Adapter<MusicVideosLayoutShimmerAdapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;

		public MusicVideosLayoutShimmerAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}

		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View _v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shimmer, parent, false);
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

	public class Recyclerview3Adapter extends Adapter<Recyclerview3Adapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;

		public Recyclerview3Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}

		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View _v = LayoutInflater.from(parent.getContext()).inflate(R.layout.zenlistmusic, parent, false);
			return new ViewHolder(_v);
		}

		@Override
		public void onBindViewHolder(@NonNull ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
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

					linear1.setOnClickListener(v -> {
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
								Log.e("ZenhomeFragment", "Error starting MusicService", e);
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

	public class Recyclerview4Adapter extends Adapter<Recyclerview4Adapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;

		public Recyclerview4Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}

		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View _v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shimmer, parent, false);
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

	public class Recyclerview5Adapter extends Adapter<Recyclerview5Adapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;

		public Recyclerview5Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}

		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View _v = LayoutInflater.from(parent.getContext()).inflate(R.layout.zenlistmusic, parent, false);
			return new ViewHolder(_v);
		}

		@Override
		public void onBindViewHolder(@NonNull ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
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

					linear1.setOnClickListener(v -> {
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
								Log.e("ZenhomeFragment", "Error starting MusicService", e);
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
