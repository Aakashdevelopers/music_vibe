package com.music.vibe;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
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
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.media.app.*;
import androidx.palette.graphics.Palette;
import com.facebook.shimmer.*;
import com.google.firebase.FirebaseApp;
import com.theophrast.ui.widget.*;
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import jp.wasabeef.picasso.transformations.*;
import org.json.*;
import android.graphics.drawable.GradientDrawable;
import android.animation.ValueAnimator;
import android.animation.ArgbEvaluator;
import android.view.animation.LinearInterpolator;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;



public class ZenPermissionsActivity extends AppCompatActivity {
	
	private static final int STORAGE_PERMISSION_CODE = 100;
	private static final int REQUEST_MEDIA_PERMISSION_CODE = 200;
	
	private LinearLayout linear6;
	private LinearLayout linear1;
	private LinearLayout linear7;
	private ImageView back;
	private TextView textview4;
	private LinearLayout Ln_Main;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private CircleImageView circleimageview1;
	private TextView textview1;
	private TextView textview2;
	private LinearLayout Ln_Bt_St;
	private TextView textview3;
	
	private Intent zgranted = new Intent();
	private SharedPreferences zg;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.zen_permissions);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear6 = findViewById(R.id.linear6);
		linear1 = findViewById(R.id.linear1);
		linear7 = findViewById(R.id.linear7);
		back = findViewById(R.id.back);
		textview4 = findViewById(R.id.textview4);
		Ln_Main = findViewById(R.id.Ln_Main);
		linear3 = findViewById(R.id.linear3);
		linear4 = findViewById(R.id.linear4);
		linear5 = findViewById(R.id.linear5);
		circleimageview1 = findViewById(R.id.circleimageview1);
		textview1 = findViewById(R.id.textview1);
		textview2 = findViewById(R.id.textview2);
		Ln_Bt_St = findViewById(R.id.Ln_Bt_St);
		textview3 = findViewById(R.id.textview3);
		zg = getSharedPreferences("zg", Activity.MODE_PRIVATE);
	}
	
	private void initializeLogic() {
		if (_succes()) {
			return;
		}
		back.setColorFilter(0xFF000000, PorterDuff.Mode.MULTIPLY);
		
		
		Ln_Bt_St = findViewById(R.id.Ln_Bt_St);
		
		final GradientDrawable gradient = new GradientDrawable(
		GradientDrawable.Orientation.LEFT_RIGHT,
		new int[]{0xFF6D5FE6, 0xFF4E74F3}
		);
		gradient.setCornerRadius(100);
		Ln_Bt_St.setBackground(gradient);
		
		ValueAnimator animator = ValueAnimator.ofObject(
		new ArgbEvaluator(), 0xFF6D5FE6, 0xFF4E74F3, 0xFF6D5FE6
		);
		animator.setDuration(1000);
		animator.setInterpolator(new LinearInterpolator());
		animator.setRepeatCount(ValueAnimator.INFINITE);
		animator.setRepeatMode(ValueAnimator.REVERSE);
		
		animator.addUpdateListener(a -> {
			gradient.setColors(new int[]{(int) a.getAnimatedValue(), 0xFF4E74F3});
			Ln_Bt_St.setBackground(gradient);
		});
		
		animator.start();
		
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/zenlt.ttf"), 0);
		textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/zenlt.ttf"), 0);
		textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/zenlt.ttf"), 0);
		textview4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/zenlt.ttf"), 0);
		
		
		Ln_Bt_St = findViewById(R.id.Ln_Bt_St);
		
		Ln_Bt_St.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				_checkStoragePermission();
			}
		});
		
		
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == REQUEST_MEDIA_PERMISSION_CODE) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				
				// Usamos un Handler para programar la tarea en el hilo principal
				
				Intent zgranted   = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(zgranted);
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				finish(); // Finaliza la actividad actual después de iniciar la nueva
				
			} else {
				Toast.makeText(this, "Permisos de medios denegados", Toast.LENGTH_SHORT).show();
				// Opcional: Redirigir al usuario a la configuración de la aplicación
				_openAppSettings();
			}
		}
	}
	{
	}
	
	public void _checkStoragePermission() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			// Android 13 y superior: Solicitar permisos específicos para medios
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED ||
			ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED ||
			ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions(this, new String[]{
					Manifest.permission.READ_MEDIA_IMAGES,
					Manifest.permission.READ_MEDIA_VIDEO,
					Manifest.permission.READ_MEDIA_AUDIO
				}, REQUEST_MEDIA_PERMISSION_CODE);
			} else {
				Intent zgranted = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(zgranted);
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				finish(); // Finaliza la actividad actual después de iniciar la nueva
			}
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			// Android 6.0 (Marshmallow) a Android 12: Solicitar READ_EXTERNAL_STORAGE
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions(this, new String[]{
					Manifest.permission.READ_EXTERNAL_STORAGE
				}, REQUEST_MEDIA_PERMISSION_CODE);
			} else {
				Intent zgranted = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(zgranted);
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				finish(); // Finaliza la actividad actual después de iniciar la nueva
			}
		} else {
			Intent zgranted = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(zgranted);
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			finish(); // Finaliza la actividad actual después de iniciar la nueva
		}
	}
	
	
	public void _openAppSettings() {
		
		Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		Uri uri = Uri.fromParts("package", getPackageName(), null);
		intent.setData(uri);
		startActivity(intent);
		
	}
	
	
	public boolean _succes() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			// Android 13 y superior: Verificar si los permisos específicos para medios ya han sido concedidos
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED &&
			ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED &&
			ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED) {
				Intent zgranted = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(zgranted);
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				finish(); // Finaliza la actividad actual después de iniciar la nueva
				return true;
			}
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			// Android 6.0 (Marshmallow) a Android 12: Verificar si READ_EXTERNAL_STORAGE ya ha sido concedido
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
				Intent zgranted = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(zgranted);
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				finish(); // Finaliza la actividad actual después de iniciar la nueva
				return true;
			}
		} else {
			// No es necesario verificar permisos en versiones inferiores
			Intent zgranted = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(zgranted);
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			finish(); // Finaliza la actividad actual después de iniciar la nueva
			return true;
		}
		return false;
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
