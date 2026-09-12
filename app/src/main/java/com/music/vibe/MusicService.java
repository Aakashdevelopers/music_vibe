package com.music.vibe;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener {
    public static MediaPlayer mediaPlayer;
    public static MediaSessionCompat mediaSession;
    public static boolean isPlaying = false;
    public static boolean isPrepared = false;
    public static int currentPosition = 0;
    public static boolean isRepeatOne = false;

    public ArrayList<HashMap<String, Object>> songList;

    private final IBinder binder = new LocalBinder();
    private ServiceCallback serviceCallback;
    private static final String CHANNEL_ID = "music_channel";

    public interface ServiceCallback {
        void onSongChanged(HashMap<String, Object> song);
        void onPlaybackStateChanged(boolean isPlaying);
        void onPositionChanged(int position, int duration);
        void onRepeatStateChanged(boolean isRepeat);
    }

    public class LocalBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    public void setServiceCallback(ServiceCallback callback) {
        this.serviceCallback = callback;
        if (callback != null) {
            if (songList != null && !songList.isEmpty()) {
                callback.onSongChanged(songList.get(currentPosition));
            }
            callback.onPlaybackStateChanged(isPlaying);
            if (mediaPlayer != null && isPrepared) {
                callback.onPositionChanged(mediaPlayer.getCurrentPosition(), mediaPlayer.getDuration());
            }
            callback.onRepeatStateChanged(isRepeatOne);
        }
    }

    private Handler updateHandler = new Handler();
    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null && isPlaying && isPrepared) {
                updatePlaybackState();
                showNotification();
                notifyPositionUpdate();
                updateHandler.postDelayed(this, 1000);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaSession = new MediaSessionCompat(this, "MusicService");
        mediaPlayer.setOnCompletionListener(this);

        createNotificationChannel();

        mediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                playMusic();
            }

            @Override
            public void onPause() {
                pauseMusic();
            }

            @Override
            public void onSkipToNext() {
                nextSong();
            }

            @Override
            public void onSkipToPrevious() {
                previousSong();
            }

            @Override
            public void onSeekTo(long pos) {
                if (mediaPlayer != null && isPrepared) {
                    mediaPlayer.seekTo((int) pos);
                    updatePlaybackState();
                    notifyPositionUpdate();
                }
            }
        });

        mediaPlayer.setOnErrorListener((mp, what, extra) -> {
            handlePlaybackError();
            return true;
        });
    }

    private void handlePlaybackError() {
        isPrepared = false;
        isPlaying = false;
        if (serviceCallback != null) {
            serviceCallback.onPlaybackStateChanged(false);
        }
        nextSong(); // Intentar reproducir la siguiente canción
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();
            switch (action) {
                case "PLAY_NEW":
                    handlePlayNew(intent);
                    break;
                case "SEEK":
                    handleSeek(intent);
                    break;
                case "PLAY":
                    playMusic();
                    break;
                case "PAUSE":
                    pauseMusic();
                    break;
                case "NEXT":
                    nextSong();
                    break;
                case "PREV":
                    previousSong();
                    break;
                case "STOP":
                    stopSelf();
                    break;
                case "TOGGLE_REPEAT":
                    toggleRepeatMode();
                    break;
            }
        }
        return START_STICKY;
    }

    private void handlePlayNew(Intent intent) {
        if (intent.hasExtra("SONG_LIST")) {
            songList = (ArrayList<HashMap<String, Object>>) intent.getSerializableExtra("SONG_LIST");
        }
        if (intent.hasExtra("POSITION")) {
            currentPosition = intent.getIntExtra("POSITION", 0);
        }
        if (songList != null && !songList.isEmpty()) {
            currentPosition = Math.max(0, Math.min(currentPosition, songList.size() - 1));
            prepareAndPlay();
        }
    }

    private void handleSeek(Intent intent) {
        if (mediaPlayer != null && isPrepared) {
            int seekToPosition = intent.getIntExtra("SEEK_POSITION", 0);
            mediaPlayer.seekTo(seekToPosition);
            updatePlaybackState();
            notifyPositionUpdate();
        }
    }

    public void playMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying() && isPrepared) {
            try {
                mediaPlayer.start();
                isPlaying = true;
                startUpdates();
                updatePlaybackState();
                showNotification();
                notifyPositionUpdate(); // Llamar para actualizar SeekBar y ProgressBar inmediatamente

                if (serviceCallback != null) {
                    serviceCallback.onPlaybackStateChanged(true);
                }
            } catch (IllegalStateException e) {
                handlePlaybackError();
            }
        }
    }

    public void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            try {
                mediaPlayer.pause();
                isPlaying = false;
                stopUpdates();
                updatePlaybackState();
                showNotification();

                if (serviceCallback != null) {
                    serviceCallback.onPlaybackStateChanged(false);
                }
            } catch (IllegalStateException e) {
                handlePlaybackError();
            }
        }
    }

    public void nextSong() {

    if (isRepeatOne) {
        prepareAndPlay();
        return;
    }

    if (songList == null || songList.isEmpty()) return;

    isPrepared = false;

    int oldPosition = currentPosition;

    currentPosition = (currentPosition + 1) % songList.size();

    // 🔥 MAIN LIST END
    if (currentPosition == 0 && oldPosition == songList.size() - 1) {

        try {
            SharedPreferences sp = getSharedPreferences("music_data", MODE_PRIVATE);
            String json = sp.getString("songs_backup", "");

            if (!json.equals("")) {

                ArrayList<HashMap<String, Object>> backupList =
                        new Gson().fromJson(
                                json,
                                new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType()
                        );

                if (backupList != null && !backupList.isEmpty()) {

                    // 🔥 SWITCH TO BACKUP
                    songList = new ArrayList<>(backupList);

                    // 🔀 RANDOM POSITION (IMPORTANT 🔥)
                    currentPosition = new java.util.Random().nextInt(songList.size());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    else {
        // 🔀 BACKUP MODE → RANDOM NEXT
        if (songList != null && !songList.isEmpty()) {

            currentPosition = new java.util.Random().nextInt(songList.size());
        }
    }

    prepareAndPlay();
}

    public void previousSong() {
        if (isRepeatOne) {
            // Si el modo de repetición está activado, reinicia la misma canción
            prepareAndPlay();
        } else if (songList != null && !songList.isEmpty()) {
            // Si no está activado, pasa a la canción anterior
            isPrepared = false;
            currentPosition = (currentPosition - 1 + songList.size()) % songList.size();
            prepareAndPlay();
        }
    }

    public void prepareAndPlay() {
        try {
            if (songList == null || songList.isEmpty()) {
                return;
            }

            currentPosition = Math.max(0, Math.min(currentPosition, songList.size() - 1));

            HashMap<String, Object> currentSong = songList.get(currentPosition);
            saveRecentSong(currentSong);

            if (mediaPlayer != null) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    mediaPlayer.reset();
                    mediaPlayer.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                mediaPlayer.setLooping(isRepeatOne);
                handlePlaybackError();
                return true;
            });

            String dataSource = currentSong.get("data").toString();
            mediaPlayer.setDataSource(dataSource);
            isPrepared = false;

            mediaPlayer.setOnPreparedListener(mp -> {
                isPrepared = true;
                updateMetadata();
                showNotification();
                playMusic();
                if (serviceCallback != null) {
                    serviceCallback.onSongChanged(currentSong);
                }
            });

            mediaPlayer.prepareAsync();

        } catch (Exception e) {
            e.printStackTrace();
            handlePlaybackError();
        }
    }

    private void updatePlaybackState() {
        if (mediaPlayer == null) return;

        long actions = PlaybackStateCompat.ACTION_PLAY_PAUSE |
                PlaybackStateCompat.ACTION_SKIP_TO_NEXT |
                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                PlaybackStateCompat.ACTION_SEEK_TO;

        int state = isPlaying ? PlaybackStateCompat.STATE_PLAYING : PlaybackStateCompat.STATE_PAUSED;

        try {
            PlaybackStateCompat playbackState = new PlaybackStateCompat.Builder()
                    .setActions(actions)
                    .setState(state, mediaPlayer.getCurrentPosition(), 1f)
                    .build();
            mediaSession.setPlaybackState(playbackState);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void notifyPositionUpdate() {
        if (serviceCallback != null && mediaPlayer != null && isPrepared) {
            try {
                serviceCallback.onPositionChanged(
                        mediaPlayer.getCurrentPosition(),
                        mediaPlayer.getDuration()
                );
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateMetadata() {
        if (mediaPlayer != null && songList != null && currentPosition < songList.size()) {
            HashMap<String, Object> currentSong = songList.get(currentPosition);
            MediaMetadataCompat.Builder metadataBuilder = new MediaMetadataCompat.Builder();
            metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_TITLE,
                    currentSong.get("name").toString());
            metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST,
                    currentSong.get("artist").toString());
            try {
                metadataBuilder.putLong(MediaMetadataCompat.METADATA_KEY_DURATION,
                        mediaPlayer.getDuration());
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            mediaSession.setMetadata(metadataBuilder.build());
        }
    }

    private void showNotification() {
        if (songList == null || currentPosition < 0 || currentPosition >= songList.size()) {
            return;
        }

        HashMap<String, Object> currentSong = songList.get(currentPosition);
        String songName = currentSong.get("name").toString();
        String artist = currentSong.get("artist").toString();
        Bitmap albumArt = getAlbumArt(currentSong.get("photopath").toString());

        Intent playPauseIntent = new Intent(this, MusicService.class);
        playPauseIntent.setAction(isPlaying ? "PAUSE" : "PLAY");
        PendingIntent playPausePending = PendingIntent.getService(this, 0,
                playPauseIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Action playPauseAction = new NotificationCompat.Action(
                isPlaying ? R.drawable.ic_pause : R.drawable.ic_play,
                isPlaying ? "Pausar" : "Reproducir",
                playPausePending
        );

        NotificationCompat.Action nextAction = new NotificationCompat.Action(
                R.drawable.ic_next,
                "Siguiente",
                PendingIntent.getService(this, 1,
                        new Intent(this, MusicService.class).setAction("NEXT"),
                        PendingIntent.FLAG_IMMUTABLE)
        );

        NotificationCompat.Action prevAction = new NotificationCompat.Action(
                R.drawable.ic_previous,
                "Anterior",
                PendingIntent.getService(this, 2,
                        new Intent(this, MusicService.class).setAction("PREV"),
                        PendingIntent.FLAG_IMMUTABLE)
        );

        NotificationCompat.Action stopAction = new NotificationCompat.Action(
                R.drawable.ic_stop,
                "Detener",
                PendingIntent.getService(this, 3,
                        new Intent(this, MusicService.class).setAction("STOP"),
                        PendingIntent.FLAG_IMMUTABLE)
        );

        try {
            int progress = mediaPlayer != null && isPrepared ?
                    mediaPlayer.getCurrentPosition() : 0;
            int duration = mediaPlayer != null && isPrepared ?
                    mediaPlayer.getDuration() : 0;

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.zenmusic)
                    .setContentTitle(songName)
                    .setContentText(artist)
                    .setLargeIcon(albumArt)
                    .setProgress(duration, progress, false)
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                            .setMediaSession(mediaSession.getSessionToken())
                            .setShowActionsInCompactView(0, 1, 2))
                    .addAction(prevAction)
                    .addAction(playPauseAction)
                    .addAction(nextAction)
                    .addAction(stopAction)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .build();

            startForeground(1, notification);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap getAlbumArt(String path) {
    try {

        if (path.startsWith("http")) {

            java.net.URL url = new java.net.URL(path);
            java.net.HttpURLConnection connection =
                    (java.net.HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            connection.connect();

            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);

            return Bitmap.createScaledBitmap(bitmap, 512, 512, true);
        }

        else if (path.startsWith("content://")) {

            Uri uri = Uri.parse(path);
            InputStream inputStream = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            return bitmap;
        }

        else {

            Bitmap bitmap = BitmapFactory.decodeFile(path);
            return bitmap;
        }

    } catch (Exception e) {
        e.printStackTrace();
        return BitmapFactory.decodeResource(getResources(), R.drawable.zenloading_error);
    }
}
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Reproducción de música",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription("Controles para la reproducción de música");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public int getDuration() {
        if (mediaPlayer != null && isPrepared) {
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    public void toggleRepeatMode() {
        isRepeatOne = !isRepeatOne;
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(isRepeatOne);
        }
        if (serviceCallback != null) {
            serviceCallback.onRepeatStateChanged(isRepeatOne);
        }
    }

    private void saveRecentSong(HashMap<String, Object> song) {
        SharedPreferences sharedPreferences = getSharedPreferences("recent_songs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = sharedPreferences.getString("song_list", "[]");

        Type type = new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType();
        ArrayList<HashMap<String, Object>> recentSongs = gson.fromJson(json, type);

        if (recentSongs == null) {
            recentSongs = new ArrayList<>();
        }

        // Verificar si la canción ya existe y eliminarla
        String newSongPath = song.get("data").toString();
        for (int i = 0; i < recentSongs.size(); i++) {
            if (recentSongs.get(i).get("data").toString().equals(newSongPath)) {
                recentSongs.remove(i);
                break;
            }
        }

        // Agregar la nueva canción al inicio
        recentSongs.add(0, song);

        // Limitar a 15 canciones
        if (recentSongs.size() > 15) {
            recentSongs.remove(recentSongs.size() - 1);
        }

        // Guardar la lista actualizada en SharedPreferences
        editor.putString("song_list", gson.toJson(recentSongs));
        editor.apply();
    }

    public void toggleFavorite(int songPosition) {
        SharedPreferences sharedPreferences = getSharedPreferences("favorites", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = sharedPreferences.getString("favorite_songs", "[]");
        Type type = new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType();
        ArrayList<HashMap<String, Object>> favoriteSongs = gson.fromJson(json, type);

        if (favoriteSongs == null) {
            favoriteSongs = new ArrayList<>();
        }

        HashMap<String, Object> song = songList.get(songPosition);
        boolean alreadyFavorited = false;

        for (int i = 0; i < favoriteSongs.size(); i++) {
            if (favoriteSongs.get(i).get("data").toString().equals(song.get("data").toString())) {
                favoriteSongs.remove(i);
                alreadyFavorited = true;
                break;
            }
        }

        if (!alreadyFavorited) {
            favoriteSongs.add(song);
        }

        editor.putString("favorite_songs", gson.toJson(favoriteSongs));
        editor.apply();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        isPrepared = false;
        isPlaying = false;

        if (isRepeatOne) {
            // Si el modo de repetición está activado, reinicia la misma canción
            prepareAndPlay();
        } else {
            // Si no está activado, pasa a la siguiente canción
            nextSong();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopUpdates();
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mediaSession != null) {
            mediaSession.release();
            mediaSession = null;
        }
        stopForeground(true);
        serviceCallback = null;
    }

    private void startUpdates() {
        stopUpdates(); // Asegurarse de que no hay actualizaciones pendientes
        updateHandler.post(updateRunnable);
    }

    private void stopUpdates() {
        updateHandler.removeCallbacks(updateRunnable);
    }
}