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
import androidx.cardview.widget.CardView;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.lifecycle.ViewModelProvider;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.graphics.drawable.GradientDrawable;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;


public class ZennextFragmentActivity extends BottomSheetDialogFragment{
	
	private SharedViewModel sharedViewModel;
	
	private ArrayList<HashMap<String, Object>> Next = new ArrayList<>();
	
	private CardView view;
	private LinearLayout Ln1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private RecyclerView recyclerview1;
	private TextView textview1;
	
	private SharedPreferences songZEN;
	private SharedPreferences songZenNext;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.zennext_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}

	private void initialize(Bundle _savedInstanceState, View _view) {
		view = _view.findViewById(R.id.view);
		Ln1 = _view.findViewById(R.id.Ln1);
		linear2 = _view.findViewById(R.id.linear2);
		linear3 = _view.findViewById(R.id.linear3);
		recyclerview1 = _view.findViewById(R.id.recyclerview1);
		textview1 = _view.findViewById(R.id.textview1);
		Context ctx = getContext();
		if (ctx != null) {
			songZEN = ctx.getSharedPreferences("songZEN", Context.MODE_PRIVATE);
			songZenNext = ctx.getSharedPreferences("songZenNext", Context.MODE_PRIVATE);
		}
	}

	private void initializeLogic() {
		Context ctx = getContext();
		if (ctx != null && textview1 != null) {
			textview1.setTypeface(Typeface.createFromAsset(ctx.getAssets(),"fonts/zenlt.ttf"), Typeface.BOLD);
		}
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
		
		// Set the background of the BottomSheetDialog's window to transparent  
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  
		
		// Also, set the background of the bottom sheet itself to transparent  
		dialog.setOnShowListener(dialogInterface -> {  
			BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;  
			FrameLayout bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);  
			if (bottomSheet != null) {  
				bottomSheet.setBackground(new ColorDrawable(Color.TRANSPARENT));  
			}  
		});  
		
		return dialog;
		
	}{
		
		
		
		
	}
	
	@Override
	
	public void onViewCreated(@NonNull View _view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(_view, savedInstanceState);
		
		sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);  
		
		sharedViewModel.getColor().observe(getViewLifecycleOwner(), color -> {  
			
			if (view != null) {  
				view.setCardBackgroundColor(color);  
			}  
		});
		
	}
	{
		
		
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		try{
			Next = new Gson().fromJson(songZenNext.getString("songZenNext", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			recyclerview1.setAdapter(new Recyclerview1Adapter(Next));
			recyclerview1.setLayoutManager(new LinearLayoutManager(getContext()));
		}catch(Exception e){
			
		}
	}
	public class Recyclerview1Adapter extends Adapter<Recyclerview1Adapter.ViewHolder> {
		
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
			final CardView mAlbumCard = _view.findViewById(R.id.mAlbumCard);
			final LinearLayout mMusicInfoMiddle = _view.findViewById(R.id.mMusicInfoMiddle);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final ImageView ArtZen = _view.findViewById(R.id.ArtZen);
			final TextView TnameZen = _view.findViewById(R.id.TnameZen);
			final TextView TartistZen = _view.findViewById(R.id.TartistZen);
			
			TnameZen.setTextColor(0xFFFFFFFF);
			if (_data.size() > 0) {
				if (_data.get(_position).containsKey("name")) {
					TnameZen.setTypeface(Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/zenlt.ttf"), 0);
					TnameZen.setText(_data.get(_position).get("name").toString());
					TnameZen.setSingleLine(true);
					TnameZen.setEllipsize(TextUtils.TruncateAt.MARQUEE);
					TnameZen.setSelected(true);
				}
				
				if (_data.get(_position).containsKey("artist")) {
					TartistZen.setTypeface(Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/zenlt.ttf"), 0);
					TartistZen.setText(_data.get(_position).get("artist").toString());
					TartistZen.setSingleLine(true);
					TartistZen.setEllipsize(TextUtils.TruncateAt.MARQUEE);
					TartistZen.setSelected(true);
				}
			}
			
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
			// Utiliza Picasso para cargar la imagen en el ImageView
			
			// Click listener en el elemento
			linear1.setOnClickListener(v -> {
				int position = _holder.getBindingAdapterPosition();
				if (position != RecyclerView.NO_POSITION) {
					Context context = v.getContext();
					Intent playIntent = new Intent(context, MusicService.class);
					playIntent.setAction("PLAY_NEW");
					playIntent.putExtra("SONG_LIST", new ArrayList<>(_data)); // Enviar copia de la lista
					playIntent.putExtra("POSITION", position);
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
						context.startForegroundService(playIntent);
					} else {
						context.startService(playIntent);
					}
				}
			});
		}
		
		// Método para obtener la lista actual del Adapter
		public ArrayList<HashMap<String, Object>> getItemList() {
			return _data;
			
		}
		
		@Override
		
public int getItemCount() {
    return (_data != null) ? _data.size() : 0;
}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
}
