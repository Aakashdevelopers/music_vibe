package com.music.vibe;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.*;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.media.app.*;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.facebook.shimmer.*;
import com.google.firebase.FirebaseApp;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Callback;
import com.theophrast.ui.widget.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import jp.wasabeef.picasso.transformations.*;
import org.json.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.SharedPreferences;
import java.lang.reflect.Type;
import androidx.core.widget.NestedScrollView;
import com.startapp.sdk.adsbase.adlisteners.*;
import com.startapp.sdk.adsbase.*;
import com.startapp.sdk.ads.banner.*;


public class ZenhomeFragmentActivity extends Fragment {
	
	private Timer _timer = new Timer();
	
	private String API = "";
	private String table = "";
	private String URL = "";
	private String list_json = "";
	private HashMap<String, Object> map = new HashMap<>();
	private HashMap<String, Object> map1 = new HashMap<>();
	private HashMap<String, Object> map2 = new HashMap<>();
	private HashMap<String, Object> map3 = new HashMap<>();
	private HashMap<String, Object> map4 = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> QuickPicks = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> last = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> my = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> background_list = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> stori = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> y = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> ins = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> ShimmerListMap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> nso = new ArrayList<>();
	
	private NestedScrollView mainScrollBar;
	private LinearLayout body;
	private LinearLayout QuickPicksMusicsLayout;
	private LinearLayout RecommendedAlbumsLayout;
	private LinearLayout MusicVideosLayout;
	private LinearLayout TopArtistsLayout;
	private LinearLayout QuickPicksMusicsLayoutTop;
	private RecyclerView QuickPicksMusicsLayoutShimmer;
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
	private Banner linear7;
	private TextView textview1;
	
	private SharedPreferences songZen;
	private SharedPreferences songZenHome;
	private SharedPreferences prefs;
	private SharedPreferences sharedPreferences;
	private TimerTask t;
	private SharedPreferences str;
	private RequestNetwork storys;
	private RequestNetwork.RequestListener _storys_request_listener;
	private RequestNetwork ts;
	private RequestNetwork.RequestListener _ts_request_listener;
	private SharedPreferences songZenNext;
	private SharedPreferences songZenSearch;
	private RequestNetwork instaviral;
	private RequestNetwork.RequestListener _instaviral_request_listener;
	private RequestNetwork ns;
	private RequestNetwork.RequestListener _ns_request_listener;
	private SharedPreferences sp;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.zenhome_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		FirebaseApp.initializeApp(getContext());
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
		QuickPicksMusicsLayoutShimmer = _view.findViewById(R.id.QuickPicksMusicsLayoutShimmer);
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
		songZen = getContext().getSharedPreferences("songZen", Activity.MODE_PRIVATE);
		songZenHome = getContext().getSharedPreferences("songZenHome", Activity.MODE_PRIVATE);
		prefs = getContext().getSharedPreferences("prefs", Activity.MODE_PRIVATE);
		sharedPreferences = getContext().getSharedPreferences("recent_songs", Activity.MODE_PRIVATE);
		str = getContext().getSharedPreferences("story", Activity.MODE_PRIVATE);
		storys = new RequestNetwork((Activity) getContext());
		ts = new RequestNetwork((Activity) getContext());
		songZenNext = getContext().getSharedPreferences("songZenNext", Activity.MODE_PRIVATE);
		songZenSearch = getContext().getSharedPreferences("songZenSearch", Activity.MODE_PRIVATE);
		instaviral = new RequestNetwork((Activity) getContext());
		ns = new RequestNetwork((Activity) getContext());
		sp = getContext().getSharedPreferences("sp", Activity.MODE_PRIVATE);
		
		_storys_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				try {
					
					JSONObject main = new JSONObject(_response);
					
					JSONArray results = main.getJSONArray("results");
					
					stori.clear();
					
					for (int i = 0; i < results.length(); i++) {
						
						HashMap<String, Object> map1 = new HashMap<>();
						
						JSONObject item = results.getJSONObject(i);
						
						String title = item.optString("name", "Unknown");
						
						
						// ARTIST
						String artist = "Unknown";
						
						JSONObject artistsObj = item.optJSONObject("artists");
						
						if (artistsObj != null) {
							
							JSONArray primary = artistsObj.optJSONArray("primary");
							
							if (primary != null && primary.length() > 0) {
								
								JSONObject artistObj = primary.getJSONObject(0);
								
								artist = artistObj.optString("name", "Unknown");
								
							}
							
						}
						
						
						// IMAGE
						String imageurl = "";
						
						JSONArray imageArray = item.optJSONArray("image");
						
						if (imageArray != null && imageArray.length() > 2) {
							
							JSONObject imageObj = imageArray.getJSONObject(2);
							
							imageurl = imageObj.optString("url", "");
							
						}
						
						
						// AUDIO URL
						String songurl = "";
						
						JSONArray downloadArray = item.optJSONArray("downloadUrl");
						
						if (downloadArray != null && downloadArray.length() > 4) {
							
							JSONObject audioObj = downloadArray.getJSONObject(4);
							
							songurl = audioObj.optString("url", "");
							
						}
						
						
						// SKIP EMPTY SONGS
						if (songurl.equals("")) continue;
						
						
						map1.put("name", title);
						map1.put("artist", artist);
						map1.put("photopath", imageurl);
						map1.put("data", songurl);
						
						stori.add(map1);
						
					}
					
					
				} catch (Exception e) {
					
				}
				recyclerview1.setAdapter(new Recyclerview1Adapter(stori));
				recyclerview1.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
				QuickPicksMusicsLayoutShimmer.setVisibility(View.GONE);
				recyclerview1.setVisibility(View.VISIBLE);
				MusicManager manager = MusicManager.getInstance(requireContext());
				
				manager.addSongs(stori);   // ✅ correct
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
		
		_ts_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				try {
					
					JSONObject main = new JSONObject(_response);
					
					JSONArray results = main.getJSONArray("results");
					
					y.clear();
					
					for (int i = 0; i < results.length(); i++) {
						
						HashMap<String, Object> map2 = new HashMap<>();
						
						JSONObject item = results.getJSONObject(i);
						
						String title = item.optString("name", "Unknown");
						
						
						// ARTIST
						String artist = "Unknown";
						
						JSONObject artistsObj = item.optJSONObject("artists");
						
						if (artistsObj != null) {
							
							JSONArray primary = artistsObj.optJSONArray("primary");
							
							if (primary != null && primary.length() > 0) {
								
								JSONObject artistObj = primary.getJSONObject(0);
								
								artist = artistObj.optString("name", "Unknown");
								
							}
							
						}
						
						
						// IMAGE
						String imageurl = "";
						
						JSONArray imageArray = item.optJSONArray("image");
						
						if (imageArray != null && imageArray.length() > 2) {
							
							JSONObject imageObj = imageArray.getJSONObject(2);
							
							imageurl = imageObj.optString("url", "");
							
						}
						
						
						// AUDIO URL
						String songurl = "";
						
						JSONArray downloadArray = item.optJSONArray("downloadUrl");
						
						if (downloadArray != null && downloadArray.length() > 4) {
							
							JSONObject audioObj = downloadArray.getJSONObject(4);
							
							songurl = audioObj.optString("url", "");
							
						}
						
						
						// SKIP EMPTY SONGS
						if (songurl.equals("")) continue;
						
						
						map2.put("name", title);
						map2.put("artist", artist);
						map2.put("photopath", imageurl);
						map2.put("data", songurl);
						
						y.add(map2);
						
					}
					
					
					
				} catch (Exception e) {
					
					
					
				}
				Collections.shuffle(y);
				recyclerview2.setAdapter(new Recyclerview2Adapter(y));
				recyclerview2.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
				RecommendedAlbumsLayoutShimmer.setVisibility(View.GONE);
				recyclerview2.setVisibility(View.VISIBLE);
				MusicManager manager = MusicManager.getInstance(requireContext());
				
				manager.addSongs(y);   // ✅ correct
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
		
		_instaviral_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				try {
					
					JSONObject main = new JSONObject(_response);
					
					JSONArray results = main.getJSONArray("results");
					
					ins.clear();
					
					for (int i = 0; i < results.length(); i++) {
						
						HashMap<String, Object> map3 = new HashMap<>();
						
						JSONObject item = results.getJSONObject(i);
						
						String title = item.optString("name", "Unknown");
						
						
						// ARTIST
						String artist = "Unknown";
						
						JSONObject artistsObj = item.optJSONObject("artists");
						
						if (artistsObj != null) {
							
							JSONArray primary = artistsObj.optJSONArray("primary");
							
							if (primary != null && primary.length() > 0) {
								
								JSONObject artistObj = primary.getJSONObject(0);
								
								artist = artistObj.optString("name", "Unknown");
								
							}
							
						}
						
						
						// IMAGE
						String imageurl = "";
						
						JSONArray imageArray = item.optJSONArray("image");
						
						if (imageArray != null && imageArray.length() > 2) {
							
							JSONObject imageObj = imageArray.getJSONObject(2);
							
							imageurl = imageObj.optString("url", "");
							
						}
						
						
						// AUDIO URL
						String songurl = "";
						
						JSONArray downloadArray = item.optJSONArray("downloadUrl");
						
						if (downloadArray != null && downloadArray.length() > 4) {
							
							JSONObject audioObj = downloadArray.getJSONObject(4);
							
							songurl = audioObj.optString("url", "");
							
						}
						
						
						// SKIP EMPTY SONGS
						if (songurl.equals("")) continue;
						
						
						map3.put("name", title);
						map3.put("artist", artist);
						map3.put("photopath", imageurl);
						map3.put("data", songurl);
						
						ins.add(map3);
						
					}
					
					
				} catch (Exception e) {
					
					
				}
				Collections.shuffle(ins);
				recyclerview3.setAdapter(new Recyclerview3Adapter(ins));
				recyclerview3.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
				MusicVideosLayoutShimmer.setVisibility(View.GONE);
				recyclerview3.setVisibility(View.VISIBLE);
				MusicManager manager = MusicManager.getInstance(requireContext());
				
				manager.addSongs(ins);   // ✅ correct
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
		
		_ns_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				try {
					
					JSONObject main = new JSONObject(_response);
					
					JSONArray results = main.getJSONArray("results");
					
					nso.clear();
					
					for (int i = 0; i < results.length(); i++) {
						
						HashMap<String, Object> map4 = new HashMap<>();
						
						JSONObject item = results.getJSONObject(i);
						
						String title = item.optString("name", "Unknown");
						
						
						// ARTIST
						String artist = "Unknown";
						
						JSONObject artistsObj = item.optJSONObject("artists");
						
						if (artistsObj != null) {
							
							JSONArray primary = artistsObj.optJSONArray("primary");
							
							if (primary != null && primary.length() > 0) {
								
								JSONObject artistObj = primary.getJSONObject(0);
								
								artist = artistObj.optString("name", "Unknown");
								
							}
							
						}
						
						
						// IMAGE
						String imageurl = "";
						
						JSONArray imageArray = item.optJSONArray("image");
						
						if (imageArray != null && imageArray.length() > 2) {
							
							JSONObject imageObj = imageArray.getJSONObject(2);
							
							imageurl = imageObj.optString("url", "");
							
						}
						
						
						// AUDIO URL
						String songurl = "";
						
						JSONArray downloadArray = item.optJSONArray("downloadUrl");
						
						if (downloadArray != null && downloadArray.length() > 4) {
							
							JSONObject audioObj = downloadArray.getJSONObject(4);
							
							songurl = audioObj.optString("url", "");
							
						}
						
						
						// SKIP EMPTY SONGS
						if (songurl.equals("")) continue;
						
						
						map4.put("name", title);
						map4.put("artist", artist);
						map4.put("photopath", imageurl);
						map4.put("data", songurl);
						
						nso.add(map4);
						
					}
					
					
				} catch (Exception e) {
					
					
					
				}
				recyclerview5.setAdapter(new Recyclerview5Adapter(nso));
				recyclerview5.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
				recyclerview4.setVisibility(View.GONE);
				recyclerview5.setVisibility(View.VISIBLE);
				MusicManager manager = MusicManager.getInstance(requireContext());
				
				manager.addSongs(stori);   // ✅ correct
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
	}
	
	private void initializeLogic() {
		storys.startRequestNetwork(RequestNetworkController.GET, "https://flip-saavn.vercel.app/search?query=bollywood+song", "r", _storys_request_listener);
		_locate_vrb();
		for(int _repeat50 = 0; _repeat50 < (int)(10); _repeat50++) {
			{
				HashMap<String, Object> _item = new HashMap<>();
				_item.put("", "");
				ShimmerListMap.add(_item);
			}
		}
		QuickPicksMusicsLayoutShimmer.setAdapter(new QuickPicksMusicsLayoutShimmerAdapter(ShimmerListMap));
		QuickPicksMusicsLayoutShimmer.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
		RecommendedAlbumsLayoutShimmer.setAdapter(new RecommendedAlbumsLayoutShimmerAdapter(ShimmerListMap));
		RecommendedAlbumsLayoutShimmer.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
		MusicVideosLayoutShimmer.setAdapter(new MusicVideosLayoutShimmerAdapter(ShimmerListMap));
		MusicVideosLayoutShimmer.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
		recyclerview4.setAdapter(new Recyclerview4Adapter(ShimmerListMap));
		recyclerview4.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
		QuickPicksMusicsLayoutShimmer.setVisibility(View.VISIBLE);
		RecommendedAlbumsLayoutShimmer.setVisibility(View.VISIBLE);
		MusicVideosLayoutShimmer.setVisibility(View.VISIBLE);
		recyclerview1.setVisibility(View.GONE);
		recyclerview2.setVisibility(View.GONE);
		recyclerview3.setVisibility(View.GONE);
		recyclerview4.setVisibility(View.GONE);
		StartAppSDK.init(getContext().getApplicationContext(), "204645186", false);
		StartAppAd.disableSplash();
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
		
		
		
		
		HashMap<String, Object> headers2 = new HashMap<>();
		
		headers2.put("Authorization", "Bearer sk-paxsenix-h-IpQ7TD4Z5s8LS9cOLis9tVkz_AfLWh3vlbmFgqECyr1xYZ");
		headers2.put("Content-Type", "application/json");
		
		storys.setHeaders(headers2);
		
		storys.startRequestNetwork(
		RequestNetworkController.GET,
		"https://api.paxsenix.org/jiosaavn/search?q=latest+hindi+songs",
		"",
		_storys_request_listener
		);
		HashMap<String, Object> headers3 = new HashMap<>();
		
		headers3.put("Authorization", "Bearer sk-paxsenix-h-IpQ7TD4Z5s8LS9cOLis9tVkz_AfLWh3vlbmFgqECyr1xYZ");
		headers3.put("Content-Type", "application/json");
		
		ts.setHeaders(headers3);
		
		ts.startRequestNetwork(
		RequestNetworkController.GET,
		"https://api.paxsenix.org/jiosaavn/search?q=bhojpuri+song",
		"",
		_ts_request_listener
		);
		
		
		
		
		HashMap<String, Object> headers4 = new HashMap<>();
		
		headers4.put("Authorization", "Bearer sk-paxsenix-h-IpQ7TD4Z5s8LS9cOLis9tVkz_AfLWh3vlbmFgqECyr1xYZ");
		headers4.put("Content-Type", "application/json");
		
		instaviral.setHeaders(headers4);
		
		instaviral.startRequestNetwork(
		RequestNetworkController.GET,
		"https://api.paxsenix.org/jiosaavn/search?q=Haryanvi+song",
		"",
		_instaviral_request_listener
		);
		
		
		
		
	}
	
	public class QuickPicksMusicsLayoutShimmerAdapter extends RecyclerView.Adapter<QuickPicksMusicsLayoutShimmerAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public QuickPicksMusicsLayoutShimmerAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.shimmer, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final LinearLayout mMusicInfoMiddle = _view.findViewById(R.id.mMusicInfoMiddle);
			final androidx.cardview.widget.CardView mAlbumCard = _view.findViewById(R.id.mAlbumCard);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final ImageView ArtZen = _view.findViewById(R.id.ArtZen);
			final com.facebook.shimmer.ShimmerFrameLayout linear6 = _view.findViewById(R.id.linear6);
			final com.facebook.shimmer.ShimmerFrameLayout linear7 = _view.findViewById(R.id.linear7);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
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
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.zenlistmusic, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final androidx.cardview.widget.CardView mAlbumCard = _view.findViewById(R.id.mAlbumCard);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final ImageView ArtZen = _view.findViewById(R.id.ArtZen);
			final TextView TnameZen = _view.findViewById(R.id.TnameZen);
			final TextView TartistZen = _view.findViewById(R.id.TartistZen);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			if (_data != null && _position >= 0 && _position < _data.size()) {
				// Manejar el nombre de la canción
				if (_data.get(_position).containsKey("name")) {
					String name = _data.get(_position).get("name").toString();
					TnameZen.setTypeface(Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/zenlt.ttf"), 0);
					TnameZen.setText(name);
					TnameZen.setSingleLine(true);
					TnameZen.setEllipsize(TextUtils.TruncateAt.END);
				}
				
				// Manejar el artista
				if (_data.get(_position).containsKey("artist")) {
					String artist = _data.get(_position).get("artist").toString();
					TartistZen.setTypeface(Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/zenlt.ttf"), 0);
					TartistZen.setText(artist);
					TartistZen.setSingleLine(true);
					TartistZen.setEllipsize(TextUtils.TruncateAt.END);
				}
				
				// Manejar la imagen del álbum
				if (_data.get(_position).containsKey("photopath")) {
					String album_pic = _data.get(_position).get("photopath").toString();
					Uri imageUri = Uri.parse(album_pic);
					
					// Mejorar el manejo de memoria con Picasso
					Picasso.get()
					.load(imageUri)
					.error(R.drawable.zenloading_error)
					.config(Bitmap.Config.RGB_565) // Optimizar uso de memoria
					.into(ArtZen, new Callback() {
						@Override
						public void onSuccess() {
							// La imagen se cargó exitosamente
						}
						@Override
						public void onError(Exception e) {
							// Manejar el error si es necesario
						}
					});
					
				}
				
				// Mejorar el click listener
				linear1.setOnClickListener(new View.OnClickListener() {    
					@Override    
					public void onClick(View v) {    
						int position = _holder.getBindingAdapterPosition();    
						if (position != RecyclerView.NO_POSITION) {    
							Context context = v.getContext();
							
							// Obtener la canción seleccionada de manera segura
							HashMap<String, Object> clickedSong = _data.get(position);
							if (clickedSong == null) return;
							
							// Obtener la lista completa de manera más eficiente
							SharedPreferences prefs = getContext().getSharedPreferences("songZEN", Context.MODE_PRIVATE);
							String json = prefs.getString("songZEN", "");  
							
							if (TextUtils.isEmpty(json)) {
								SketchwareUtil.showMessage(context, "Error: Lista de canciones no disponible");
								return;
							}
							
							try {
								ArrayList<HashMap<String, Object>> fullList = stori;
								
								if (fullList == null || fullList.isEmpty()) {
									SketchwareUtil.showMessage(context, "Error: Lista de canciones vacía");
									return;
								}
								
								// Buscar la posición de manera más eficiente
								int fullPosition = findSongPosition(fullList, clickedSong);
								
								if (fullPosition != -1) {
									Intent playIntent = new Intent(context, MusicService.class);    
									playIntent.setAction("PLAY_NEW");    
									playIntent.putExtra("SONG_LIST", fullList);    
									playIntent.putExtra("POSITION", fullPosition);    
									
									// Iniciar el servicio de manera segura
									try {
										if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {  
											context.startForegroundService(playIntent);  
										} else {  
											context.startService(playIntent);  
										}
									} catch (Exception e) {
										SketchwareUtil.showMessage(context, "Error iniciando el servicio de música");
										e.printStackTrace();
									}
								} else {
									SketchwareUtil.showMessage(context, "Canción no encontrada en la lista completa");
								}
							} catch (Exception e) {
								SketchwareUtil.showMessage(context, "Error procesando la lista de canciones");
								e.printStackTrace();
							}
						}    
					}    
				});
			}
		}
		// Método auxiliar para encontrar la posición de la canción
		private int findSongPosition(ArrayList<HashMap<String, Object>> fullList, HashMap<String, Object> song) {
			String songData = (String) song.get("data");
			if (songData == null) return -1;
			
			for (int i = 0; i < fullList.size(); i++) {
				String currentData = (String) fullList.get(i).get("data");
				if (songData.equals(currentData)) {
					return i;
				}
			}
			return -1;
		}{
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
	
	public class RecommendedAlbumsLayoutShimmerAdapter extends RecyclerView.Adapter<RecommendedAlbumsLayoutShimmerAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public RecommendedAlbumsLayoutShimmerAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.shimmer, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final LinearLayout mMusicInfoMiddle = _view.findViewById(R.id.mMusicInfoMiddle);
			final androidx.cardview.widget.CardView mAlbumCard = _view.findViewById(R.id.mAlbumCard);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final ImageView ArtZen = _view.findViewById(R.id.ArtZen);
			final com.facebook.shimmer.ShimmerFrameLayout linear6 = _view.findViewById(R.id.linear6);
			final com.facebook.shimmer.ShimmerFrameLayout linear7 = _view.findViewById(R.id.linear7);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
	
	public class Recyclerview2Adapter extends RecyclerView.Adapter<Recyclerview2Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview2Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.zenlistmusic, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final androidx.cardview.widget.CardView mAlbumCard = _view.findViewById(R.id.mAlbumCard);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final ImageView ArtZen = _view.findViewById(R.id.ArtZen);
			final TextView TnameZen = _view.findViewById(R.id.TnameZen);
			final TextView TartistZen = _view.findViewById(R.id.TartistZen);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			if (_data != null && _position >= 0 && _position < _data.size()) {
				// Manejar el nombre de la canción
				if (_data.get(_position).containsKey("name")) {
					String name = _data.get(_position).get("name").toString();
					TnameZen.setTypeface(Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/zenlt.ttf"), 0);
					TnameZen.setText(name);
					TnameZen.setSingleLine(true);
					TnameZen.setEllipsize(TextUtils.TruncateAt.END);
				}
				
				// Manejar el artista
				if (_data.get(_position).containsKey("artist")) {
					String artist = _data.get(_position).get("artist").toString();
					TartistZen.setTypeface(Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/zenlt.ttf"), 0);
					TartistZen.setText(artist);
					TartistZen.setSingleLine(true);
					TartistZen.setEllipsize(TextUtils.TruncateAt.END);
				}
				
				// Manejar la imagen del álbum
				if (_data.get(_position).containsKey("photopath")) {
					String album_pic = _data.get(_position).get("photopath").toString();
					android.net.Uri imageUri = android.net.Uri.parse(album_pic);
					
					// Mejorar el manejo de memoria con Picasso
					Picasso.get()
					.load(imageUri)
					.error(R.drawable.zenloading_error)
					.config(Bitmap.Config.RGB_565) // Optimizar uso de memoria
					.into(ArtZen, new Callback() {
						@Override
						public void onSuccess() {
							// La imagen se cargó exitosamente
						}
						@Override
						public void onError(Exception e) {
							// Manejar el error si es necesario
						}
					});
					
				}
				
				// Mejorar el click listener
				linear1.setOnClickListener(new View.OnClickListener() {    
					@Override    
					public void onClick(View v) {    
						int position = _holder.getBindingAdapterPosition();    
						if (position != RecyclerView.NO_POSITION) {    
							Context context = v.getContext();
							
							// Obtener la canción seleccionada de manera segura
							HashMap<String, Object> clickedSong = _data.get(position);
							if (clickedSong == null) return;
							
							// Obtener la lista completa de manera más eficiente
							SharedPreferences prefs = getContext().getSharedPreferences("songZEN", Context.MODE_PRIVATE);
							String json = prefs.getString("songZEN", "");  
							
							if (TextUtils.isEmpty(json)) {
								SketchwareUtil.showMessage(context, "Error: Lista de canciones no disponible");
								return;
							}
							
							try {
								ArrayList<HashMap<String, Object>> fullList = y;
								
								if (fullList == null || fullList.isEmpty()) {
									SketchwareUtil.showMessage(context, "Error: Lista de canciones vacía");
									return;
								}
								
								// Buscar la posición de manera más eficiente
								int fullPosition = findSongPosition(fullList, clickedSong);
								
								if (fullPosition != -1) {
									Intent playIntent = new Intent(context, MusicService.class);    
									playIntent.setAction("PLAY_NEW");    
									playIntent.putExtra("SONG_LIST", fullList);    
									playIntent.putExtra("POSITION", fullPosition);    
									
									// Iniciar el servicio de manera segura
									try {
										if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {  
											context.startForegroundService(playIntent);  
										} else {  
											context.startService(playIntent);  
										}
									} catch (Exception e) {
										SketchwareUtil.showMessage(context, "Error iniciando el servicio de música");
										e.printStackTrace();
									}
								} else {
									SketchwareUtil.showMessage(context, "Canción no encontrada en la lista completa");
								}
							} catch (Exception e) {
								SketchwareUtil.showMessage(context, "Error procesando la lista de canciones");
								e.printStackTrace();
							}
						}    
					}    
				});
			}
		}
		// Método auxiliar para encontrar la posición de la canción
		private int findSongPosition(ArrayList<HashMap<String, Object>> fullList, HashMap<String, Object> song) {
			String songData = (String) song.get("data");
			if (songData == null) return -1;
			
			for (int i = 0; i < fullList.size(); i++) {
				String currentData = (String) fullList.get(i).get("data");
				if (songData.equals(currentData)) {
					return i;
				}
			}
			return -1;
		}{
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
	
	public class MusicVideosLayoutShimmerAdapter extends RecyclerView.Adapter<MusicVideosLayoutShimmerAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public MusicVideosLayoutShimmerAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.shimmer, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final LinearLayout mMusicInfoMiddle = _view.findViewById(R.id.mMusicInfoMiddle);
			final androidx.cardview.widget.CardView mAlbumCard = _view.findViewById(R.id.mAlbumCard);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final ImageView ArtZen = _view.findViewById(R.id.ArtZen);
			final com.facebook.shimmer.ShimmerFrameLayout linear6 = _view.findViewById(R.id.linear6);
			final com.facebook.shimmer.ShimmerFrameLayout linear7 = _view.findViewById(R.id.linear7);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
	
	public class Recyclerview3Adapter extends RecyclerView.Adapter<Recyclerview3Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview3Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.zenlistmusic, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final androidx.cardview.widget.CardView mAlbumCard = _view.findViewById(R.id.mAlbumCard);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final ImageView ArtZen = _view.findViewById(R.id.ArtZen);
			final TextView TnameZen = _view.findViewById(R.id.TnameZen);
			final TextView TartistZen = _view.findViewById(R.id.TartistZen);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			if (_data != null && _position >= 0 && _position < _data.size()) {
				// Manejar el nombre de la canción
				if (_data.get(_position).containsKey("name")) {
					String name = _data.get(_position).get("name").toString();
					TnameZen.setTypeface(Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/zenlt.ttf"), 0);
					TnameZen.setText(name);
					TnameZen.setSingleLine(true);
					TnameZen.setEllipsize(TextUtils.TruncateAt.END);
				}
				
				// Manejar el artista
				if (_data.get(_position).containsKey("artist")) {
					String artist = _data.get(_position).get("artist").toString();
					TartistZen.setTypeface(Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/zenlt.ttf"), 0);
					TartistZen.setText(artist);
					TartistZen.setSingleLine(true);
					TartistZen.setEllipsize(TextUtils.TruncateAt.END);
				}
				
				// Manejar la imagen del álbum
				if (_data.get(_position).containsKey("photopath")) {
					String album_pic = _data.get(_position).get("photopath").toString();
					android.net.Uri imageUri = android.net.Uri.parse(album_pic);
					
					// Mejorar el manejo de memoria con Picasso
					Picasso.get()
					.load(imageUri)
					.error(R.drawable.zenloading_error)
					.config(Bitmap.Config.RGB_565) // Optimizar uso de memoria
					.into(ArtZen, new Callback() {
						@Override
						public void onSuccess() {
							// La imagen se cargó exitosamente
						}
						@Override
						public void onError(Exception e) {
							// Manejar el error si es necesario
						}
					});
					
				}
				
				// Mejorar el click listener
				linear1.setOnClickListener(new View.OnClickListener() {    
					@Override    
					public void onClick(View v) {    
						int position = _holder.getBindingAdapterPosition();    
						if (position != RecyclerView.NO_POSITION) {    
							Context context = v.getContext();
							
							// Obtener la canción seleccionada de manera segura
							HashMap<String, Object> clickedSong = _data.get(position);
							if (clickedSong == null) return;
							
							// Obtener la lista completa de manera más eficiente
							SharedPreferences prefs = getContext().getSharedPreferences("songZEN", Context.MODE_PRIVATE);
							String json = prefs.getString("songZEN", "");  
							
							if (TextUtils.isEmpty(json)) {
								SketchwareUtil.showMessage(context, "Error: Lista de canciones no disponible");
								return;
							}
							
							try {
								ArrayList<HashMap<String, Object>> fullList = ins;
								
								if (fullList == null || fullList.isEmpty()) {
									SketchwareUtil.showMessage(context, "Error: Lista de canciones vacía");
									return;
								}
								
								// Buscar la posición de manera más eficiente
								int fullPosition = findSongPosition(fullList, clickedSong);
								
								if (fullPosition != -1) {
									Intent playIntent = new Intent(context, MusicService.class);    
									playIntent.setAction("PLAY_NEW");    
									playIntent.putExtra("SONG_LIST", fullList);    
									playIntent.putExtra("POSITION", fullPosition);    
									
									// Iniciar el servicio de manera segura
									try {
										if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {  
											context.startForegroundService(playIntent);  
										} else {  
											context.startService(playIntent);  
										}
									} catch (Exception e) {
										SketchwareUtil.showMessage(context, "Error iniciando el servicio de música");
										e.printStackTrace();
									}
								} else {
									SketchwareUtil.showMessage(context, "Canción no encontrada en la lista completa");
								}
							} catch (Exception e) {
								SketchwareUtil.showMessage(context, "Error procesando la lista de canciones");
								e.printStackTrace();
							}
						}    
					}    
				});
			}
		}
		// Método auxiliar para encontrar la posición de la canción
		private int findSongPosition(ArrayList<HashMap<String, Object>> fullList, HashMap<String, Object> song) {
			String songData = (String) song.get("data");
			if (songData == null) return -1;
			
			for (int i = 0; i < fullList.size(); i++) {
				String currentData = (String) fullList.get(i).get("data");
				if (songData.equals(currentData)) {
					return i;
				}
			}
			return -1;
		}{
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
	
	public class Recyclerview4Adapter extends RecyclerView.Adapter<Recyclerview4Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview4Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.shimmer, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final LinearLayout mMusicInfoMiddle = _view.findViewById(R.id.mMusicInfoMiddle);
			final androidx.cardview.widget.CardView mAlbumCard = _view.findViewById(R.id.mAlbumCard);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final ImageView ArtZen = _view.findViewById(R.id.ArtZen);
			final com.facebook.shimmer.ShimmerFrameLayout linear6 = _view.findViewById(R.id.linear6);
			final com.facebook.shimmer.ShimmerFrameLayout linear7 = _view.findViewById(R.id.linear7);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
	
	public class Recyclerview5Adapter extends RecyclerView.Adapter<Recyclerview5Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview5Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.zenlistmusic, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final androidx.cardview.widget.CardView mAlbumCard = _view.findViewById(R.id.mAlbumCard);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final ImageView ArtZen = _view.findViewById(R.id.ArtZen);
			final TextView TnameZen = _view.findViewById(R.id.TnameZen);
			final TextView TartistZen = _view.findViewById(R.id.TartistZen);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			if (_data != null && _position >= 0 && _position < _data.size()) {
				// Manejar el nombre de la canción
				if (_data.get(_position).containsKey("name")) {
					String name = _data.get(_position).get("name").toString();
					TnameZen.setTypeface(Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/zenlt.ttf"), 0);
					TnameZen.setText(name);
					TnameZen.setSingleLine(true);
					TnameZen.setEllipsize(TextUtils.TruncateAt.END);
				}
				
				// Manejar el artista
				if (_data.get(_position).containsKey("artist")) {
					String artist = _data.get(_position).get("artist").toString();
					TartistZen.setTypeface(Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/zenlt.ttf"), 0);
					TartistZen.setText(artist);
					TartistZen.setSingleLine(true);
					TartistZen.setEllipsize(TextUtils.TruncateAt.END);
				}
				
				// Manejar la imagen del álbum
				if (_data.get(_position).containsKey("photopath")) {
					String album_pic = _data.get(_position).get("photopath").toString();
					android.net.Uri imageUri = android.net.Uri.parse(album_pic);
					
					// Mejorar el manejo de memoria con Picasso
					Picasso.get()
					.load(imageUri)
					.error(R.drawable.zenloading_error)
					.config(Bitmap.Config.RGB_565) // Optimizar uso de memoria
					.into(ArtZen, new Callback() {
						@Override
						public void onSuccess() {
							// La imagen se cargó exitosamente
						}
						@Override
						public void onError(Exception e) {
							// Manejar el error si es necesario
						}
					});
					
				}
				
				// Mejorar el click listener
				linear1.setOnClickListener(new View.OnClickListener() {    
					@Override    
					public void onClick(View v) {    
						int position = _holder.getBindingAdapterPosition();    
						if (position != RecyclerView.NO_POSITION) {    
							Context context = v.getContext();
							
							// Obtener la canción seleccionada de manera segura
							HashMap<String, Object> clickedSong = _data.get(position);
							if (clickedSong == null) return;
							
							// Obtener la lista completa de manera más eficiente
							SharedPreferences prefs = getContext().getSharedPreferences("songZEN", Context.MODE_PRIVATE);
							String json = prefs.getString("songZEN", "");  
							
							if (TextUtils.isEmpty(json)) {
								SketchwareUtil.showMessage(context, "Error: Lista de canciones no disponible");
								return;
							}
							
							try {
								ArrayList<HashMap<String, Object>> fullList = nso;
								
								if (fullList == null || fullList.isEmpty()) {
									SketchwareUtil.showMessage(context, "Error: Lista de canciones vacía");
									return;
								}
								
								// Buscar la posición de manera más eficiente
								int fullPosition = findSongPosition(fullList, clickedSong);
								
								if (fullPosition != -1) {
									Intent playIntent = new Intent(context, MusicService.class);    
									playIntent.setAction("PLAY_NEW");    
									playIntent.putExtra("SONG_LIST", fullList);    
									playIntent.putExtra("POSITION", fullPosition);    
									
									// Iniciar el servicio de manera segura
									try {
										if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {  
											context.startForegroundService(playIntent);  
										} else {  
											context.startService(playIntent);  
										}
									} catch (Exception e) {
										SketchwareUtil.showMessage(context, "Error iniciando el servicio de música");
										e.printStackTrace();
									}
								} else {
									SketchwareUtil.showMessage(context, "Canción no encontrada en la lista completa");
								}
							} catch (Exception e) {
								SketchwareUtil.showMessage(context, "Error procesando la lista de canciones");
								e.printStackTrace();
							}
						}    
					}    
				});
			}
		}
		// Método auxiliar para encontrar la posición de la canción
		private int findSongPosition(ArrayList<HashMap<String, Object>> fullList, HashMap<String, Object> song) {
			String songData = (String) song.get("data");
			if (songData == null) return -1;
			
			for (int i = 0; i < fullList.size(); i++) {
				String currentData = (String) fullList.get(i).get("data");
				if (songData.equals(currentData)) {
					return i;
				}
			}
			return -1;
		}{
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
}
