package com.music.vibe;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.*;
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
import java.util.regex.*;
import jp.wasabeef.picasso.transformations.*;
import org.json.*;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.startapp.sdk.adsbase.adlisteners.*;
import com.startapp.sdk.adsbase.*;
import com.startapp.sdk.ads.banner.*;


public class ZensearchFragmentActivity extends Fragment {
	
	private HashMap<String, Object> map1 = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> All_songs = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> y = new ArrayList<>();
	
	private LinearLayout linear6;
	private LinearLayout linear7;
	private LinearLayout Ln_search;
	private ImageView imageview1;
	private EditText edittext1;
	private Banner linear8;
	private RecyclerView recyclerview1;
	
	private SharedPreferences songZenSearch;
	private RequestNetwork se;
	private RequestNetwork.RequestListener _se_request_listener;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.zensearch_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		FirebaseApp.initializeApp(getContext());
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		linear6 = _view.findViewById(R.id.linear6);
		linear7 = _view.findViewById(R.id.linear7);
		Ln_search = _view.findViewById(R.id.Ln_search);
		imageview1 = _view.findViewById(R.id.imageview1);
		edittext1 = _view.findViewById(R.id.edittext1);
		linear8 = _view.findViewById(R.id.linear8);
		recyclerview1 = _view.findViewById(R.id.recyclerview1);
		songZenSearch = getContext().getSharedPreferences("songZenSearch", Activity.MODE_PRIVATE);
		se = new RequestNetwork((Activity) getContext());
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		edittext1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				HashMap<String, Object> headers = new HashMap<>();
				
				headers.put("Authorization", "Bearer sk-paxsenix-h-IpQ7TD4Z5s8LS9cOLis9tVkz_AfLWh3vlbmFgqECyr1xYZ");
				headers.put("Content-Type", "application/json");
				
				se.setHeaders(headers);
				
				
				
				se.startRequestNetwork(RequestNetworkController.GET, "https://api.paxsenix.org/jiosaavn/search?q=".concat(_charSeq), "t", _se_request_listener);
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		_se_request_listener = new RequestNetwork.RequestListener() {
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
						
						y.add(map4);
						
					}
					
					recyclerview1.setAdapter(new Recyclerview1Adapter(y));
					
				} catch (Exception e) {
					
					
					
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
	}
	
	private void initializeLogic() {
		edittext1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/zenlt.ttf"), 0);
		Ln_search.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)33, (int)1, 0xFFFF5252, 0xFFFFFFFF));
		imageview1.setColorFilter(0xFF757575, PorterDuff.Mode.MULTIPLY);
		recyclerview1.setLayoutManager(new LinearLayoutManager(getContext()));
		StartAppSDK.init(getContext().getApplicationContext(), "204645186", false);
		StartAppAd.disableSplash();
	}
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.search, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final androidx.cardview.widget.CardView mAlbumCard = _view.findViewById(R.id.mAlbumCard);
			final LinearLayout mMusicInfoMiddle = _view.findViewById(R.id.mMusicInfoMiddle);
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
}
