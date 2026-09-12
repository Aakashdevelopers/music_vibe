package com.music.vibe;

import com.music.vibe.ZenActivity;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.*;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.media.app.*;
import androidx.palette.graphics.Palette;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnAdapterChangeListener;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import com.facebook.shimmer.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Callback;
import com.theophrast.ui.widget.*;
import com.theophrast.ui.widget.SquareImageView;
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import jp.wasabeef.picasso.transformations.*;
import org.json.*;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.google.android.material.navigation.NavigationView;
import android.media.MediaMetadataRetriever;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.music.vibe.MusicService; // Asegúrate de que la ruta del paquete sea correcta
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.view.ViewTreeObserver;
import com.google.android.material.slider.Slider;
import com.google.android.material.color.MaterialColors;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import androidx.palette.graphics.Palette;
// Corrige el Callback de Picasso
// Asegúrate de tener este import

import android.view.animation.Interpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import android.os.VibrationEffect;
import android.os.Vibrator;
import java.lang.reflect.Type;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.lifecycle.ViewModelProvider;


import android.provider.MediaStore;
import android.database.Cursor;
import android.content.ContentResolver;
import android.net.Uri;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.startapp.sdk.adsbase.adlisteners.*;
import com.startapp.sdk.adsbase.*;
import com.startapp.sdk.ads.banner.*;


public class MainActivity extends AppCompatActivity implements MusicService.ServiceCallback  {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private SharedViewModel sharedViewModel;
	SquigglyProgress progressDrawable;
	SeekBar.OnSeekBarChangeListener touchListener;
	Slider.OnSliderTouchListener touchListener2;
	private String list_json = "";
	private String songs_duration = "";
	private String song_duration = "";
	BottomSheetBehavior<View> bottomSheetBehavior;
	private int previousColor = Color.BLACK;
	private MusicService musicService;
	private boolean isBound = false;
	final int ANIMATION_DURATION = 500;
	private HashMap<String, Object> m = new HashMap<>();
	private String keys = "";
	private String API = "";
	private String table = "";
	private String URL = "";
	private HashMap<String, Object> map = new HashMap<>();
	private HashMap<String, Object> map1 = new HashMap<>();
	private String res = "";
	private String per = "";
	private String ly = "";
	
	private ArrayList<HashMap<String, Object>> All_Song_Data = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> background_list = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> All_Zen_Data_Songs = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> my = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> nw = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> stry = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> maplist = new ArrayList<>();
	
	private CoordinatorLayout CDZEN;
	private LinearLayout Ln_items;
	private LinearLayout FULL_PLAYER_ZENSOUND;
	private LinearLayout linear42;
	private RelativeLayout Relative;
	private LinearLayout linear43;
	private LinearLayout linear45;
	private LinearLayout linear44;
	private CircleImageView profile;
	private TextView textview10;
	private LinearLayout linear46;
	private LinearLayout linear47;
	private ImageView imageview18;
	private ImageView imageview19;
	private ViewPager viewpager1;
	private LinearLayout ln_all_menudown;
	private LinearLayout Ln_mini_nav;
	private LinearLayout linear56;
	private LinearLayout linear10;
	private LinearLayout linear54;
	private LinearLayout linear55;
	private CardView cardview1;
	private LinearLayout linear14;
	private LinearLayout linear15;
	private ImageView prevArt;
	private TextView prevname;
	private TextView prevartista;
	private ImageView prevplaypause;
	private ProgressBar progressbar1;
	private LinearLayout bottom_nav;
	private LinearLayout nav_home_layout;
	private LinearLayout nav_search_layout;
	private LinearLayout nav_download_layout;
	private LinearLayout nav_setting_layout;
	private ImageView nav_home;
	private TextView txt_home;
	private ImageView nav_search;
	private TextView txt_search;
	private ImageView nav_download;
	private TextView txt_download;
	private ImageView nav_setting;
	private TextView txt_setting;
	private LinearLayout PLAYER_TOP;
	private LinearLayout PLAYER_DOWN;
	private LinearLayout linear36;
	private CardView cardview2;
	private LinearLayout linear19;
	private HorizontalScrollView hscroll1;
	private SquareImageView albumArt;
	private TextView songName;
	private LinearLayout linear35;
	private TextView artistName;
	private LinearLayout Btmorre;
	private ImageView imageview13;
	private LinearLayout linear20;
	private LinearLayout fav;
	private LinearLayout repeat;
	private LinearLayout time;
	private TextView textview7;
	private ImageView imageview7;
	private TextView textview9;
	private ImageView imageview9;
	private TextView textview8;
	private ImageView imageview8;
	private LinearLayout linear29;
	private FrameLayout slider;
	private LinearLayout linear31;
	private SeekBar seekBar;
	private LinearLayout linear32;
	private LinearLayout linear33;
	private LinearLayout linear34;
	private TextView currentTime;
	private TextView totalTime;
	private LinearLayout linear25;
	private LinearLayout linear26;
	private LinearLayout linear27;
	private LinearLayout linear28;
	private ImageView prev;
	private LinearLayout linear37;
	private ImageView playPauseBtn;
	private ImageView next;
	
	private FragmentFragmentAdapter fragment;
	private TimerTask timer;
	private SharedPreferences songZEN;
	private AlertDialog.Builder permission;
	private SharedPreferences songZenHome;
	private SharedPreferences songZenSearch;
	private Intent user = new Intent();
	private SharedPreferences songZenNext;
	private DatabaseReference songs = _firebase.getReference("songs");
	private ChildEventListener _songs_child_listener;
	private RequestNetwork rn;
	private RequestNetwork.RequestListener _rn_request_listener;
	private SharedPreferences songZen;
	private RequestNetwork ts;
	private RequestNetwork.RequestListener _ts_request_listener;
	private SharedPreferences sp;
	private SharedPreferences d;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		setupStatusBar();

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
    sharedViewModel.setContext(this);  // 🔹 Importante para que Toast funcione
getAllSongData();
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		CDZEN = findViewById(R.id.CDZEN);
		Ln_items = findViewById(R.id.Ln_items);
		FULL_PLAYER_ZENSOUND = findViewById(R.id.FULL_PLAYER_ZENSOUND);
		linear42 = findViewById(R.id.linear42);
		Relative = findViewById(R.id.Relative);
		linear43 = findViewById(R.id.linear43);
		linear45 = findViewById(R.id.linear45);
		linear44 = findViewById(R.id.linear44);
		profile = findViewById(R.id.profile);
		textview10 = findViewById(R.id.textview10);
		linear46 = findViewById(R.id.linear46);
		linear47 = findViewById(R.id.linear47);
		imageview18 = findViewById(R.id.imageview18);
		imageview19 = findViewById(R.id.imageview19);
		viewpager1 = findViewById(R.id.viewpager1);
		ln_all_menudown = findViewById(R.id.ln_all_menudown);
		Ln_mini_nav = findViewById(R.id.Ln_mini_nav);
		linear56 = findViewById(R.id.linear56);
		linear10 = findViewById(R.id.linear10);
		linear54 = findViewById(R.id.linear54);
		linear55 = findViewById(R.id.linear55);
		cardview1 = findViewById(R.id.cardview1);
		linear14 = findViewById(R.id.linear14);
		linear15 = findViewById(R.id.linear15);
		prevArt = findViewById(R.id.prevArt);
		prevname = findViewById(R.id.prevname);
		prevartista = findViewById(R.id.prevartista);
		prevplaypause = findViewById(R.id.prevplaypause);
		progressbar1 = findViewById(R.id.progressbar1);
		bottom_nav = findViewById(R.id.bottom_nav);
		nav_home_layout = findViewById(R.id.nav_home_layout);
		nav_search_layout = findViewById(R.id.nav_search_layout);
		nav_download_layout = findViewById(R.id.nav_download_layout);
		nav_setting_layout = findViewById(R.id.nav_setting_layout);
		nav_home = findViewById(R.id.nav_home);
		txt_home = findViewById(R.id.txt_home);
		nav_search = findViewById(R.id.nav_search);
		txt_search = findViewById(R.id.txt_search);
		nav_download = findViewById(R.id.nav_download);
		txt_download = findViewById(R.id.txt_download);
		nav_setting = findViewById(R.id.nav_setting);
		txt_setting = findViewById(R.id.txt_setting);
		PLAYER_TOP = findViewById(R.id.PLAYER_TOP);
		PLAYER_DOWN = findViewById(R.id.PLAYER_DOWN);
		linear36 = findViewById(R.id.linear36);
		cardview2 = findViewById(R.id.cardview2);
		linear19 = findViewById(R.id.linear19);
		hscroll1 = findViewById(R.id.hscroll1);
		albumArt = findViewById(R.id.albumArt);
		songName = findViewById(R.id.songName);
		linear35 = findViewById(R.id.linear35);
		artistName = findViewById(R.id.artistName);
		Btmorre = findViewById(R.id.Btmorre);
		imageview13 = findViewById(R.id.imageview13);
		linear20 = findViewById(R.id.linear20);
		fav = findViewById(R.id.fav);
		repeat = findViewById(R.id.repeat);
		time = findViewById(R.id.time);
		textview7 = findViewById(R.id.textview7);
		imageview7 = findViewById(R.id.imageview7);
		textview9 = findViewById(R.id.textview9);
		imageview9 = findViewById(R.id.imageview9);
		textview8 = findViewById(R.id.textview8);
		imageview8 = findViewById(R.id.imageview8);
		linear29 = findViewById(R.id.linear29);
		slider = findViewById(R.id.slider);
		linear31 = findViewById(R.id.linear31);
		seekBar = findViewById(R.id.seekBar);
		linear32 = findViewById(R.id.linear32);
		linear33 = findViewById(R.id.linear33);
		linear34 = findViewById(R.id.linear34);
		currentTime = findViewById(R.id.currentTime);
		totalTime = findViewById(R.id.totalTime);
		linear25 = findViewById(R.id.linear25);
		linear26 = findViewById(R.id.linear26);
		linear27 = findViewById(R.id.linear27);
		linear28 = findViewById(R.id.linear28);
		prev = findViewById(R.id.prev);
		linear37 = findViewById(R.id.linear37);
		playPauseBtn = findViewById(R.id.playPauseBtn);
		next = findViewById(R.id.next);
		fragment = new FragmentFragmentAdapter(getApplicationContext(), getSupportFragmentManager());
		songZEN = getSharedPreferences("songZEN", Activity.MODE_PRIVATE);
		permission = new AlertDialog.Builder(this);
		songZenHome = getSharedPreferences("songZenHome", Activity.MODE_PRIVATE);
		songZenSearch = getSharedPreferences("songZenSearch", Activity.MODE_PRIVATE);
		songZenNext = getSharedPreferences("songZenNext", Activity.MODE_PRIVATE);
		rn = new RequestNetwork(this);
		songZen = getSharedPreferences("songZen", Activity.MODE_PRIVATE);
		ts = new RequestNetwork(this);
		sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
		d = getSharedPreferences("d", Activity.MODE_PRIVATE);
		
		FULL_PLAYER_ZENSOUND.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		linear46.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View _view) {
				viewpager1.setCurrentItem((int)1);
			}
		});
		
		linear47.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivity(user);
			}
		});
		
		viewpager1.addOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrolled(int _position, float _positionOffset, int _positionOffsetPixels) {
				
			}
			
			@Override
			public void onPageSelected(int _position) {
				if (_position == 0) {
					txt_home.setVisibility(View.VISIBLE);
					txt_search.setVisibility(View.GONE);
					txt_download.setVisibility(View.GONE);
					txt_setting.setVisibility(View.GONE);
					
					nav_home_layout.setBackgroundResource(R.drawable.bg_selected_nav);
					nav_search_layout.setBackground(null);
					nav_download_layout.setBackground(null);
					nav_setting_layout.setBackground(null);
					
					nav_home.setColorFilter(0xFFA78BFA);
					nav_search.setColorFilter(0xFF808080);
					nav_download.setColorFilter(0xFF808080);
					nav_setting.setColorFilter(0xFF808080);
					
					
					txt_home.setTextColor(0xFFA78BFA);
				}
				if (_position == 1) {
					txt_home.setVisibility(View.GONE);
					txt_search.setVisibility(View.VISIBLE);
					txt_download.setVisibility(View.GONE);
					txt_setting.setVisibility(View.GONE);
					
					nav_search_layout.setBackgroundResource(R.drawable.bg_selected_nav);
					nav_home_layout.setBackground(null);
					nav_download_layout.setBackground(null);
					nav_setting_layout.setBackground(null);
					
					nav_home.setColorFilter(0xFF808080);
					nav_search.setColorFilter(0xFFA78BFA);
					nav_download.setColorFilter(0xFF808080);
					nav_setting.setColorFilter(0xFF808080);
					
					txt_search.setTextColor(0xFFA78BFA);
				}
				if (_position == 2) {
					txt_home.setVisibility(View.GONE);
					txt_search.setVisibility(View.GONE);
					txt_download.setVisibility(View.VISIBLE);
					txt_setting.setVisibility(View.GONE);
					
					nav_download_layout.setBackgroundResource(R.drawable.bg_selected_nav);
					nav_home_layout.setBackground(null);
					nav_search_layout.setBackground(null);
					nav_setting_layout.setBackground(null);
					
					nav_home.setColorFilter(0xFF808080);
					nav_search.setColorFilter(0xFF808080);
					nav_download.setColorFilter(0xFFA78BFA);
					nav_setting.setColorFilter(0xFF808080);
					
					txt_download.setTextColor(0xFFA78BFA);
				}
				if (_position == 3) {
					
					txt_home.setVisibility(View.GONE);
					txt_search.setVisibility(View.GONE);
					txt_download.setVisibility(View.GONE);
					txt_setting.setVisibility(View.VISIBLE);
					
					nav_setting_layout.setBackgroundResource(R.drawable.bg_selected_nav);
					nav_home_layout.setBackground(null);
					nav_search_layout.setBackground(null);
					nav_download_layout.setBackground(null);
					
					nav_home.setColorFilter(0xFF808080);
					nav_search.setColorFilter(0xFF808080);
					nav_download.setColorFilter(0xFF808080);
					nav_setting.setColorFilter(0xFFA78BFA);
					
					txt_setting.setTextColor(0xFFA78BFA);
				}
			}
			
			@Override
			public void onPageScrollStateChanged(int _scrollState) {
				
			}
		});
		
		nav_home_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View _view) {
				viewpager1.setCurrentItem((int)0);
			}
		});
		
		nav_search_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View _view) {
				viewpager1.setCurrentItem((int)1);
			}
		});
		
		nav_download_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View _view) {
				viewpager1.setCurrentItem((int)2);
			}
		});
		
		nav_setting_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		Btmorre.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (sp.getString("datax", "").contains("https")) {
					String url = sp.getString("datax", "");
					String songName = sp.getString("namex", "");
					
					if (!url.isEmpty()) {
						
						DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
						Uri uri = Uri.parse(url);
						
						DownloadManager.Request request = new DownloadManager.Request(uri);
						
						String fileName = songName.isEmpty() ? "song.mp3" : songName.replaceAll("[\\\\/:*?\"<>|]", "") + ".mp3";
						
						request.setTitle(fileName);
						request.setDescription("Downloading...");
						request.setDestinationInExternalPublicDir(
						Environment.DIRECTORY_DOWNLOADS,
						fileName
						);
						
						request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
						
						// 🔥 IMPORTANT LINE (Download start karega)
						dm.enqueue(request);
						
						Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_SHORT).show();
						
					} else {
						Toast.makeText(getApplicationContext(), "No URL", Toast.LENGTH_SHORT).show();
					}
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), "This file is already downloaded");
				}
			}
		});
		
		time.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View _view) {
				BottomSheetDialogFragment bottomSheet = new ZennextFragmentActivity();
				bottomSheet.show(getSupportFragmentManager(), "achievementBottomSheet");
			}
		});
		
		_songs_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		songs.addChildEventListener(_songs_child_listener);
		
		_rn_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				
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
				
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
	}
	
	private void initializeLogic() {
		_access_files();
		imageview18.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
		imageview19.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
		prevplaypause.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
		prevname.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/zenlt.ttf"), Typeface.NORMAL);
		prevname.setSingleLine(true);
		prevname.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		prevname.setSelected(true);
		prevartista.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/zenlt.ttf"), Typeface.NORMAL);
		prevartista.setSingleLine(true);
		prevartista.setEllipsize(TextUtils.TruncateAt.END);
		
		songName.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/zenlt.ttf"), 1);
		songName.setSingleLine(true);
		songName.setEllipsize(TextUtils.TruncateAt.END);
		
		artistName.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/zenlt.ttf"), 0);
		artistName.setSingleLine(true);
		artistName.setEllipsize(TextUtils.TruncateAt.END);
		
		// Obtener la referencia del ImageView
		
		
		// Establecer el scaleType a CENTER_CROP
		albumArt.setScaleType(ImageView.ScaleType.CENTER_CROP);
		Drawable progressDrawable = progressbar1.getProgressDrawable().mutate();
		progressDrawable.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
		progressbar1.setProgressDrawable(progressDrawable);
		fragment.setTabCount(4);
		viewpager1.setAdapter(fragment);
		try{
			_fullplayerzensound();
		}catch(Exception e){
			
		}
		try{
			_repeatsongs();
		}catch(Exception e){
			
		}
		
		
		profile.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				vibrateDevice(); 
				
				user.setClass(getApplicationContext(), ZenuserActivity.class);
				startActivity(user);
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				
				return true;
			}
		});
	}
	
	private void vibrateDevice() {
		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		if (vibrator != null && vibrator.hasVibrator()) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
			} else {
				vibrator.vibrate(50);
			}
		}
	}{
		
	}
	
	public class FragmentFragmentAdapter extends FragmentStatePagerAdapter {
		// This class is deprecated, you should migrate to ViewPager2:
		// https://developer.android.com/reference/androidx/viewpager2/widget/ViewPager2
		Context context;
		int tabCount;
		
		public FragmentFragmentAdapter(Context context, FragmentManager manager) {
			super(manager);
			this.context = context;
		}
		
		public void setTabCount(int tabCount) {
			this.tabCount = tabCount;
		}
		
		@Override
		public int getCount() {
			return tabCount;
		}
		
		@Override
		public CharSequence getPageTitle(int _position) {
			return "";
		}
		
		
		@Override
		public Fragment getItem(int _position) {
			if (_position == 0) {
				return new ZenhomeFragmentActivity();
			}
			if (_position == 1) {
				return new ZensearchFragmentActivity();
			}
			if (_position == 2) {
				return new DownloadFragmentActivity();
			}
			if (_position == 3) {
				return new SettingFragmentActivity();
			}
			return new Fragment();
		}
		
	}
	
	
	@Override
	protected void onPostCreate(Bundle _savedInstanceState) {
		super.onPostCreate(_savedInstanceState);
		try{
			_service();
		}catch(Exception e){
			
		}
		
		
		Ln_mini_nav.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)40, 0xFF212121)); repeat.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)60, (int)1, 0xFF757575, getResources().getColor(R.color.semi_transparent_white))); fav.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)60, (int)1, 0xFF757575, getResources().getColor(R.color.semi_transparent_white))); time.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)60, (int)1, 0xFF757575, getResources().getColor(R.color.semi_transparent_white))); Btmorre.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)60, (int)1, 0xFF757575, getResources().getColor(R.color.semi_transparent_white))); linear37.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)1, 0xFF757575, getResources().getColor(R.color.semi_transparent_white))); linear46.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)60, (int)1, 0xFFBDBDBD, Color.TRANSPARENT)); linear47.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)1, 0xFFBDBDBD, Color.TRANSPARENT));
		
		
	}
	public void _Animator(final View _view, final String _propertyName, final double _value, final double _duration) {
		ObjectAnimator anim = new ObjectAnimator();
		anim.setTarget(_view);
		anim.setPropertyName(_propertyName);
		anim.setFloatValues((float)_value);
		anim.setDuration((long)_duration);
		anim.start();
	}
	
	
	public void _FadeOut(final View _view, final double _duration) {
		_Animator(_view, "scaleX", 0, 200);
		_Animator(_view, "scaleY", 0, 200);
		timer = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						_Animator(_view, "scaleX", 1, 200);
						_Animator(_view, "scaleY", 1, 200);
					}
				});
			}
		};
		_timer.schedule(timer, (int)(_duration));
	}
	
	
	public void _access_files() {
		_NewCustomDialog("", "", "", "", true);
		// HOME CLICK
		nav_home_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				txt_home.setVisibility(View.VISIBLE);
				txt_search.setVisibility(View.GONE);
				txt_download.setVisibility(View.GONE);
				txt_setting.setVisibility(View.GONE);
				
				nav_home_layout.setBackgroundResource(R.drawable.bg_selected_nav);
				nav_search_layout.setBackground(null);
				nav_download_layout.setBackground(null);
				nav_setting_layout.setBackground(null);
				
				nav_home.setColorFilter(0xFFA78BFA);
				nav_search.setColorFilter(0xFF808080);
				nav_download.setColorFilter(0xFF808080);
				nav_setting.setColorFilter(0xFF808080);
				viewpager1.setCurrentItem((int)0);
				
				txt_home.setTextColor(0xFFA78BFA);
			}
		});
		
		
		// SEARCH CLICK
		nav_search_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				viewpager1.setCurrentItem((int)1);
				txt_home.setVisibility(View.GONE);
				txt_search.setVisibility(View.VISIBLE);
				txt_download.setVisibility(View.GONE);
				txt_setting.setVisibility(View.GONE);
				
				nav_search_layout.setBackgroundResource(R.drawable.bg_selected_nav);
				nav_home_layout.setBackground(null);
				nav_download_layout.setBackground(null);
				nav_setting_layout.setBackground(null);
				
				nav_home.setColorFilter(0xFF808080);
				nav_search.setColorFilter(0xFFA78BFA);
				nav_download.setColorFilter(0xFF808080);
				nav_setting.setColorFilter(0xFF808080);
				
				txt_search.setTextColor(0xFFA78BFA);
			}
		});
		
		
		// DOWNLOAD CLICK
		nav_download_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {viewpager1.setCurrentItem((int)2);
				txt_home.setVisibility(View.GONE);
				txt_search.setVisibility(View.GONE);
				txt_download.setVisibility(View.VISIBLE);
				txt_setting.setVisibility(View.GONE);
				
				nav_download_layout.setBackgroundResource(R.drawable.bg_selected_nav);
				nav_home_layout.setBackground(null);
				nav_search_layout.setBackground(null);
				nav_setting_layout.setBackground(null);
				
				nav_home.setColorFilter(0xFF808080);
				nav_search.setColorFilter(0xFF808080);
				nav_download.setColorFilter(0xFFA78BFA);
				nav_setting.setColorFilter(0xFF808080);
				
				txt_download.setTextColor(0xFFA78BFA);
			}
		});
		
		
		// SETTING CLICK
		nav_setting_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				viewpager1.setCurrentItem((int)3);
				txt_home.setVisibility(View.GONE);
				txt_search.setVisibility(View.GONE);
				txt_download.setVisibility(View.GONE);
				txt_setting.setVisibility(View.VISIBLE);
				
				nav_setting_layout.setBackgroundResource(R.drawable.bg_selected_nav);
				nav_home_layout.setBackground(null);
				nav_search_layout.setBackground(null);
				nav_download_layout.setBackground(null);
				
				nav_home.setColorFilter(0xFF808080);
				nav_search.setColorFilter(0xFF808080);
				nav_download.setColorFilter(0xFF808080);
				nav_setting.setColorFilter(0xFFA78BFA);
				
				txt_setting.setTextColor(0xFFA78BFA);
			}
		});
		songZenHome.edit().putString("songZenHome", new Gson().toJson(my)).commit();
		list_json = new Gson().toJson(my);
		background_list = new Gson().fromJson(list_json, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		// Inicializar SharedPreferences correctamente para cada caso
		SharedPreferences songZEN = getSharedPreferences("songZEN", MODE_PRIVATE);
		SharedPreferences songZenHome = getSharedPreferences("songZenHome", MODE_PRIVATE);
		SharedPreferences songZenSearch = getSharedPreferences("songZenSearch", MODE_PRIVATE);
		SharedPreferences songZenNext = getSharedPreferences("songZenNext", MODE_PRIVATE);
		// Verificar que no son nulos antes de intentar editar
		if (songZEN != null) {
			songZEN.edit().putString("songZEN", new Gson().toJson(my)).apply();
		} else {
			Log.e("MainActivity", "songZEN SharedPreferences es null");
		}
		
		if (songZenHome != null) {
			songZenHome.edit().putString("songZenHome", new Gson().toJson(my)).apply();
		} else {
			Log.e("MainActivity", "songZenHome SharedPreferences es null");
		}
		
		if (songZenSearch != null) {
			songZenSearch.edit().putString("songZenSearch", new Gson().toJson(my)).apply();
		} else {
			Log.e("MainActivity", "songZenSearch SharedPreferences es null");
		}
		
		if (songZenNext != null) {
			songZenNext.edit().putString("songZenNext", new Gson().toJson(my)).apply();
		} else {
			Log.e("MainActivity", "songZenNext SharedPreferences es null");
		}
		
		
		// Guardar los datos
		
		_AllMusic();
	}
	
	
	public void _service() {
		
		
		// Inicializar vistas
		// En tu inicialización de vistas agregar
		prevplaypause = findViewById(R.id.prevplaypause); // Asegúrate que el ID coincida con tu XML
		prev = findViewById(R.id.prev);
		playPauseBtn = findViewById(R.id.playPauseBtn);
		next = findViewById(R.id.next);
		albumArt = findViewById(R.id.albumArt);
		prevArt = findViewById(R.id.prevArt);
		songName = findViewById(R.id.songName);
		artistName = findViewById(R.id.artistName);
		prevname = findViewById(R.id.prevname);
		prevartista = findViewById(R.id.prevartista);
		currentTime = findViewById(R.id.currentTime);
		totalTime = findViewById(R.id.totalTime);
		seekBar = findViewById(R.id.seekBar);
		repeat = findViewById(R.id.repeat);  // Asegúrate de que el ID sea el correcto para tu LinearLayout
		imageview9 = findViewById(R.id.imageview9);
		// Inicializar lista de canciones
		All_Zen_Data_Songs = new ArrayList<>();
		
		// Configurar listeners de botones
		prev.setOnClickListener(v -> sendServiceAction("PREV"));
		next.setOnClickListener(v -> sendServiceAction("NEXT"));
		playPauseBtn.setOnClickListener(v -> {
			String action = isBound && musicService.isPlaying ? "PAUSE" : "PLAY";
			sendServiceAction(action);
		});
		
		// Agregar esto para el segundo botón
		prevplaypause.setOnClickListener(v -> {
			String action = isBound && musicService.isPlaying ? "PAUSE" : "PLAY";
			sendServiceAction(action);
		});
		
		// Configurar SeekBar personalizada  
		setupSeekBar();
		
	}
	// Service Connection
	private ServiceConnection serviceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			MusicService.LocalBinder binder = (MusicService.LocalBinder) service;
			musicService = binder.getService();
			musicService.setServiceCallback(MainActivity.this);
			isBound = true;
			updatePlayerUI();
		}
		
		@Override  
		public void onServiceDisconnected(ComponentName arg0) {  
			isBound = false;  
		}  
	};  
	{  
	}  
	@Override  
	protected void onStart() {  
		super.onStart();  
		// Iniciar y vincular el servicio  
		Intent intent = new Intent(this, MusicService.class);  
		startService(intent);  
		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);  
	}  
	{  
	}  
	@Override  
	protected void onStop() {  
		super.onStop();  
		if (isBound) {  
			unbindService(serviceConnection);  
			isBound = false;  
		}  
	}  
	{  
	}  
	// Deja solo una función
	
	private void sendServiceAction(String action) {
		if (isBound) {
			switch (action) {
				case "PLAY":
				musicService.playMusic();
				break;
				case "PAUSE":
				musicService.pauseMusic();
				break;
				case "NEXT":
				if (!musicService.isRepeatOne) {
					musicService.nextSong();
				} else {
					// Si el modo de repetición está activado, reinicia la misma canción
					musicService.prepareAndPlay();
				}
				break;
				case "PREV":
				if (!musicService.isRepeatOne) {
					musicService.previousSong();
				} else {
					// Si el modo de repetición está activado, reinicia la misma canción
					musicService.prepareAndPlay();
				}
				break;
				case "STOP":
				stopService(new Intent(this, MusicService.class));
				finish();
				break;
				case "TOGGLE_REPEAT":
				musicService.toggleRepeatMode();
				break;
			}
		}
	}
	{
	}
	private void setupSeekBar() {
		seekBar.animate().setDuration(200).setInterpolator(new LinearInterpolator()).start();
		seekBar.setVisibility(View.VISIBLE);
		
		float seekBarProgressWavelength = getResources().getDimensionPixelSize(R.dimen.media_seekbar_progress_wavelength);    
		float seekBarProgressAmplitude = getResources().getDimensionPixelSize(R.dimen.media_seekbar_progress_amplitude);    
		float seekBarProgressPhase = getResources().getDimensionPixelSize(R.dimen.media_seekbar_progress_phase);    
		float seekBarProgressStrokeWidth = getResources().getDimensionPixelSize(R.dimen.media_seekbar_progress_stroke_width);    
		
		progressDrawable = new SquigglyProgress();    
		seekBar.setProgressDrawable(progressDrawable);    
		progressDrawable.setWaveLength(seekBarProgressWavelength);    
		progressDrawable.setLineAmplitude(seekBarProgressAmplitude);    
		progressDrawable.setPhaseSpeed(seekBarProgressPhase);    
		progressDrawable.setStrokeWidth(seekBarProgressStrokeWidth);    
		progressDrawable.setTransitionEnabled(true);    
		progressDrawable.setAnimate(true);    
		progressDrawable.setTint(MaterialColors.getColor(seekBar, com.google.android.material.R.attr.colorPrimary));    
		
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {    
			@Override    
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {    
				if (fromUser && isBound && musicService.isPrepared) {    
					musicService.mediaPlayer.seekTo(progress);  
					progressbar1.setProgress(progress);  
					
				}    
			}    
			
			@Override    
			public void onStartTrackingTouch(SeekBar seekBar) {    
				// No se necesita acción adicional    
			}    
			
			@Override    
			public void onStopTrackingTouch(SeekBar seekBar) {    
				// No se necesita acción adicional    
			}    
		});
		
	}
	{
	}
	@Override
	public void onSongChanged(HashMap<String, Object> song) {
		runOnUiThread(() -> {
			
			
			Object data = song.get("data");
			if (data != null) {
				sp.edit().putString("datax", data.toString()).apply();
			}
			Object datai = song.get("name");
			if (data != null) {
				sp.edit().putString("namex", datai.toString()).apply();
			}
			
			songName.setText(song.get("name").toString());
			artistName.setText(song.get("artist").toString());
			prevname.setText(song.get("name").toString());
			prevartista.setText(song.get("artist").toString());
			loadAlbumArt(song.get("photopath").toString());
			
			int currentSongPosition = musicService.currentPosition;  
			boolean isFavorited = isSongFavorited(currentSongPosition);  
			imageview7.setImageResource(isFavorited ? R.drawable.icon_favorite_round : R.drawable.icon_favorite_border_round);  
		});
		
	}
	{
	}
	
	@Override  
	public void onPlaybackStateChanged(boolean isPlaying) {  
		runOnUiThread(() -> {  
			int icon = isPlaying ? R.drawable.icon_pause_round : R.drawable.icon_play_arrow_round;
			playPauseBtn.setImageResource(icon);
			prevplaypause.setImageResource(icon); // Actualizar el segundo botón
		});  
	}
	{  
	}  
	
	@Override
	
	public void onPositionChanged(int position, int duration) {
		runOnUiThread(() -> {
			seekBar.setMax(duration);
			seekBar.setProgress(position);
			progressbar1.setMax(duration);
			progressbar1.setProgress(position);
			
			currentTime.setText(milliSecondsToTimer(position));  
			totalTime.setText(milliSecondsToTimer(duration));  
		});
		
	}
	{
	}
	// Método updatePlayerUI corregido
	private void updatePlayerUI() {
		if (isBound && musicService.songList != null && musicService.currentPosition < musicService.songList.size()) {
			HashMap<String, Object> currentSong = musicService.songList.get(musicService.currentPosition);
			
			
			songName.setText(currentSong.get("name").toString());    
			artistName.setText(currentSong.get("artist").toString());    
			prevname.setText(currentSong.get("name").toString());    
			prevartista.setText(currentSong.get("artist").toString());    
			
			loadAlbumArt(currentSong.get("photopath").toString());    
			
			playPauseBtn.setImageResource(musicService.isPlaying ? R.drawable.icon_pause_round : R.drawable.icon_play_arrow_round);    
			int icon = musicService.isPlaying ? R.drawable.icon_pause_round : R.drawable.icon_play_arrow_round;
			playPauseBtn.setImageResource(icon);
			prevplaypause.setImageResource(icon);
			// Configurar la duración de la barra de progreso  
			int duration = musicService.getDuration();  
			seekBar.setMax(duration);  
			progressbar1.setMax(duration);  
		}
		
	}
	{
	}
	// Método loadAlbumArt corregido
	private void loadAlbumArt(String url) {
		Target target = new Target() {
			@Override
			public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
				albumArt.setImageBitmap(bitmap);
				extractAndAnimateColor(bitmap);
			}
			
			@Override    
			public void onBitmapFailed(Exception e, Drawable errorDrawable) {    
				albumArt.setImageResource(R.drawable.zenloading_error);  
			}    
			
			@Override    
			public void onPrepareLoad(Drawable placeHolderDrawable) {    
				albumArt.setImageResource(R.drawable.zenloading_error);  
			}    
		};    
		
		albumArt.setTag(target); // Evitar que Picasso lo libere  
		Picasso.get().load(Uri.parse(url)).into(target);  
		
		// Cargar prevArt sin Callback  
		Picasso.get()    
		.load(Uri.parse(url))    
		.error(R.drawable.zenloading_error)    
		.into(prevArt);
		
	}
	{
	}
	private void extractAndAnimateColor(Bitmap bitmap) {
		Palette.from(bitmap).generate(palette -> {
			final int newColor = palette.getDarkMutedColor(palette.getMutedColor(Color.BLACK)); // 🔹 Hacemos la variable final
			int filteredColor = filterColor(newColor);
			
			ValueAnimator colorAnim = ValueAnimator.ofObject(new ArgbEvaluator(), previousColor, filteredColor);  
			colorAnim.setDuration(500);  
			colorAnim.addUpdateListener(animator -> {  
				int animatedColor = (int) animator.getAnimatedValue();  
				
				findViewById(R.id.FULL_PLAYER_ZENSOUND).setBackgroundColor(animatedColor);  
				GradientDrawable drawable = new GradientDrawable();  
				drawable.setColor(animatedColor);  
				drawable.setCornerRadius(40);  
				findViewById(R.id.Ln_mini_nav).setBackground(drawable);  
				
				// 🔹 Mostrar un Toast para depuración  
				
				
				// 🔹 Enviar el color al ViewModel  
				sharedViewModel.setColor(animatedColor);  
			});  
			colorAnim.start();  
			
			previousColor = filteredColor;  
		});
		
	}
	{
	}
	
	private int filterColor(int color) {  
		float[] hsv = new float[3];  
		Color.colorToHSV(color, hsv);  
		
		if (hsv[2] < 0.1f) {  
			hsv[2] = 0.15f;  
		}  
		
		if (hsv[2] > 0.3f) {  
			hsv[2] = 0.2f;  
		}  
		
		return Color.HSVToColor(hsv);  
	}
	
	{
	}
	public static String milliSecondsToTimer(long milliseconds) {
		String finalTimerString = "";
		String secondsString;
		
		int hours = (int) (milliseconds / (1000 * 60 * 60));  
		int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);  
		int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);  
		
		if (hours > 0) {  
			finalTimerString = hours + ":";  
		}  
		secondsString = (seconds < 10) ? "0" + seconds : "" + seconds;  
		return finalTimerString + minutes + ":" + secondsString;  
	}  
	{  
	}
	
	// En onCreate(), configura el botón de repetición
	@Override
	public void onRepeatStateChanged(boolean isRepeatOne) {
		runOnUiThread(() -> {
			// Cambia la imagen del botón de repetición según el estado
			imageview9.setImageResource(isRepeatOne
			? R.drawable.icon_repeat_one_round
			: R.drawable.icon_repeat_round);
		});
	}
	{
	}
	private boolean isSongFavorited(int songPosition) {
		SharedPreferences sharedPreferences = getSharedPreferences("favorites", MODE_PRIVATE);
		String json = sharedPreferences.getString("favorite_songs", "[]");
		Gson gson = new Gson();
		Type type = new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType();
		ArrayList<HashMap<String, Object>> favoriteSongs = gson.fromJson(json, type);
		
		if (favoriteSongs == null) return false;  
		
		HashMap<String, Object> song = musicService.songList.get(songPosition);  
		for (HashMap<String, Object> favoriteSong : favoriteSongs) {  
			if (favoriteSong.get("data").toString().equals(song.get("data").toString())) {  
				return true;  
			}  
		}  
		return false;
		
	}{
		
		
	}
	
	
	public void _fullplayerzensound() {
		// Configuración inicial del BottomSheet
		BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(FULL_PLAYER_ZENSOUND);
		
		bottomSheetBehavior.setPeekHeight(0);
		bottomSheetBehavior.setHideable(true);
		bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
		FULL_PLAYER_ZENSOUND.setVisibility(View.GONE); // Asegurar que esté oculto inicialmente
		
		// Expande al tocar linear10
		linear10.setOnClickListener(v -> {
			if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
				FULL_PLAYER_ZENSOUND.setVisibility(View.VISIBLE); // Hacer visible antes de expandir
				bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
			}
		});
		
		// Manejar estados y transparencia
		bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
			@Override
			public void onStateChanged(@NonNull View bottomSheet, int newState) {
				if (newState == BottomSheetBehavior.STATE_EXPANDED) {
					setSystemBarIconsDark(false);
				} else if (newState == BottomSheetBehavior.STATE_HIDDEN) {
					setSystemBarIconsDark(true);
					FULL_PLAYER_ZENSOUND.setVisibility(View.GONE); // Ocultar completamente
				} else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
					// Forzar estado a HIDDEN si está colapsado (peekHeight = 0)
					bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
				}
			}
			
			@Override
			public void onSlide(@NonNull View bottomSheet, float slideOffset) {
				// Ajustar transparencia durante el desplazamiento
				float alpha = Math.max(0f, Math.min(1f, slideOffset * 2)); // Ajusta según necesidad
				FULL_PLAYER_ZENSOUND.setAlpha(alpha);
				
				// Opcional: Ocultar si se desliza completamente hacia abajo
				if (slideOffset < -0.5f) {
					FULL_PLAYER_ZENSOUND.setVisibility(View.GONE);
				} else {
					FULL_PLAYER_ZENSOUND.setVisibility(View.VISIBLE);
				}
			}
		});
	}
	private void setupStatusBar() {
		Window window = getWindow();
		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		window.setStatusBarColor(Color.BLACK);

		WindowInsetsControllerCompat insetsController = WindowCompat.getInsetsController(window, window.getDecorView());
		if (insetsController != null) {
			insetsController.setAppearanceLightStatusBars(false); // White icons & text (time, battery, etc.)
		}
	}

	// Función para cambiar los iconos del sistema
	private void setSystemBarIconsDark(boolean darkIcons) {
		setupStatusBar();
	}{
	}
	
	
	public void _hours() {
		
	}
	
	
	public void _ui() {
		
	}
	
	
	public void _repeatsongs() {
		repeat.setOnClickListener(v -> {
			// Cambia la imagen cuando se toque el LinearLayout
			sendServiceAction("TOGGLE_REPEAT");
		});
		
		
		
		fav.setOnClickListener(v -> {
			int currentSongPosition = musicService.currentPosition;  // O usa el índice de la canción actual
			boolean isFavorited = isSongFavorited(currentSongPosition);
			
			// Cambiar el ícono
			imageview7.setImageResource(isFavorited ? R.drawable.icon_favorite_border_round : R.drawable.icon_favorite_round);
			
			// Toggle de favoritos
			musicService.toggleFavorite(currentSongPosition);
		});
	}
	
	
	public void _AllData() {
	}
	public void getAllSongData() { 
		
		String[] projection = {
			
			MediaStore.Audio.Media._ID,
			MediaStore.Audio.Media.ALBUM,
			MediaStore.Audio.Media.ALBUM_KEY,
			MediaStore.Audio.Media.ARTIST,
			MediaStore.Audio.Media.DATA,
			MediaStore.Audio.Media.TITLE,
			MediaStore.Audio.Media.DURATION,
			
			MediaStore.Audio.Media.ALBUM_ID,
			
			//android.provider.MediaStore.Audio.Albums.ALBUM_ART
		};
		
		String orderBy = " " + MediaStore.MediaColumns.DISPLAY_NAME;
		
		Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
		
		cursor = getApplicationContext().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, orderBy);
		getAlbumColumnData(cursor);
	} 
	{
	}
	public static Cursor cursor;
	public static int music_column_index;
	
	private void getAlbumColumnData(Cursor cur) {
		try {
			if (cur.moveToFirst()) {
				int _id;
				String name;
				String songuri;
				String artist;
				String album;
				String songs_duration;
				Long albumid;
				Uri imagepath;
				Uri imagepathUri;
				
				do {
					
					name = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
					songuri = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
					
					artist = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
					
					album = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
					
					
					albumid = cur.getLong(cur.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
					
					
					imagepath = Uri.parse("content://media/external/audio/albumart");
					
					imagepathUri = ContentUris.withAppendedId(imagepath,albumid);
					
					{
						
					}
				} while (cur.moveToNext());}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	{
	}
	private String setCorrectDuration(String songs_duration) {
		// TODO Auto-generated method stub
		
		if(Integer.valueOf(songs_duration) != null) {
			int time = Integer.valueOf(songs_duration);
			
			int seconds = time/1000;
			int minutes = seconds/60;
			seconds = seconds % 60;
			
			if(seconds<10) {
				songs_duration = String.valueOf(minutes) + ":0" + String.valueOf(seconds);
				song_duration = songs_duration;
			} else {
				songs_duration = String.valueOf(minutes) + ":" + String.valueOf(seconds);
				song_duration = songs_duration;
			}
			return songs_duration;
		}
		return null;
	}

	{
	}
	
	
	public void _NewCustomDialog(final String _Title, final String _Message, final String _YesButtonText, final String _NoButtonText, final boolean _MultiButton) {
		if (d.contains("d")) {
			
		} else {
			final AlertDialog NewCustomDialog = new AlertDialog.Builder(MainActivity.this).create();
			LayoutInflater NewCustomDialogLI = getLayoutInflater();
			View NewCustomDialogCV = (View) NewCustomDialogLI.inflate(R.layout.social, null);
			NewCustomDialog.setView(NewCustomDialogCV);
			NewCustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			
			
			
			
			
			
			LinearLayout linear3 = (LinearLayout)
			NewCustomDialogCV.findViewById(R.id.linear3);
			LinearLayout instagram_btn = (LinearLayout)
			NewCustomDialogCV.findViewById(R.id.instagram_btn);
			LinearLayout telegram_btn = (LinearLayout)
			NewCustomDialogCV.findViewById(R.id.telegram_btn);
			
			
			ImageView cancel = (ImageView) NewCustomDialogCV.findViewById(R.id.cancel);
			
			linear3.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)20, (int)3, 0xFFFFFFFF, 0xFF000000));
			telegram_btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View _view) {
					user.setAction(Intent.ACTION_VIEW);
					user.setData(Uri.parse("https://t.me/amstudio_19"));
					startActivity(user);
					
				}
			});
			cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View _view) {
					NewCustomDialog.dismiss();
				}
			});
			instagram_btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View _view) {
					user.setAction(Intent.ACTION_VIEW);
					user.setData(Uri.parse("https://www.instagram.com/code_with_aakash_mishra?igsh=d2IwdXB0enR3cDJo"));
					startActivity(user);
					NewCustomDialog.dismiss();
					
				}
			});
			NewCustomDialog.setCancelable(true);
			NewCustomDialog.show();
		}
	}
	
	
	public void _AllMusic() {
		final String[] projection = {
			MediaStore.Audio.Media._ID,
			MediaStore.Audio.Media.DATA,
			MediaStore.Audio.Media.TITLE,
			MediaStore.Audio.Media.ARTIST,
			MediaStore.Audio.Media.ALBUM,
			MediaStore.Audio.Media.SIZE,
			MediaStore.Audio.Media.DURATION
		};
		
		Cursor cursor = getContentResolver().query(
		MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
		projection,
		null,
		null,
		MediaStore.Audio.Media.TITLE + " ASC"
		);
		
		if (cursor != null && cursor.moveToFirst()) {
			do {
				String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
				long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
				long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
				
				// ✅ Only mp3 files
				if (path == null || !path.toLowerCase().endsWith(".mp3")) continue;
				
				// ✅ Skip incomplete / small files
				if (size < 1024 * 1024) continue; // <1MB skip
				
				// ✅ Skip very short (corrupt/incomplete)
				if (duration < 30000) continue; // <30 sec skip
				
				// ✅ File exists check
				File file = new File(path);
				if (!file.exists()) continue;
				
				long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
				String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
				String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
				String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
				
				map = new HashMap<>();
				map.put("id", id);
				map.put("name", title);
				map.put("data", path);
				map.put("photopath", album);
				map.put("artist", artist);
				
				maplist.add(map);
				
			} while (cursor.moveToNext());
		}
		
		// ✅ Save once
		sp.edit().putString("song", new Gson().toJson(maplist)).commit();
		
		if (cursor != null) {
			cursor.close();
		}
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}
