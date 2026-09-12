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
import com.facebook.shimmer.*;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Callback;
import com.theophrast.ui.widget.*;
import java.io.*;
import java.text.*;
import java.util.*;
import jp.wasabeef.picasso.transformations.*;
import org.json.*;

public class DownloadFragmentActivity extends Fragment {
	
	private String album_pic = "";
	
	private ArrayList<HashMap<String, Object>> All_Zen_Data_Songs = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> maplist = new ArrayList<>();
	
	private LinearLayout linear7;
	private LinearLayout linear8;
	private RecyclerView recyclerview1;
	private LinearLayout linear9;
	
	private SharedPreferences songZEN;
	private SharedPreferences sp;
	private SharedPreferences songZenHome;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.download_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		FirebaseApp.initializeApp(getContext());
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		linear7 = _view.findViewById(R.id.linear7);
		linear8 = _view.findViewById(R.id.linear8);
		recyclerview1 = _view.findViewById(R.id.recyclerview1);
		linear9 = _view.findViewById(R.id.linear9);
		songZEN = getContext().getSharedPreferences("songZEN", Activity.MODE_PRIVATE);
		sp = getContext().getSharedPreferences("sp", Activity.MODE_PRIVATE);
		songZenHome = getContext().getSharedPreferences("songZenHome", Activity.MODE_PRIVATE);
	}
	
	private void initializeLogic() {
		maplist = new Gson().fromJson(sp.getString("song", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		recyclerview1.setAdapter(new Recyclerview1Adapter(maplist));
		recyclerview1.setLayoutManager(new LinearLayoutManager(getContext()));
	}
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.zenlistmusicquickpicks, null);
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
			final androidx.cardview.widget.CardView cardview1 = _view.findViewById(R.id.cardview1);
			final ImageView ArtZen = _view.findViewById(R.id.ArtZen);
			final TextView TnameZen = _view.findViewById(R.id.TnameZen);
			final TextView TartistZen = _view.findViewById(R.id.TartistZen);
			
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
								ArrayList<HashMap<String, Object>> fullList = maplist;
								
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
