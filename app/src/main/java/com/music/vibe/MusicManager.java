package com.music.vibe;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;

public class MusicManager {

    private static MusicManager instance;

    public ArrayList<HashMap<String, Object>> songList = new ArrayList<>();
    public ArrayList<HashMap<String, Object>> backupList = new ArrayList<>();

    private HashSet<String> ids = new HashSet<>();

    private SharedPreferences sp;
    private Gson gson = new Gson();

    private MusicManager(Context context) {
        if (context != null) {
            sp = context.getSharedPreferences("music_data", Context.MODE_PRIVATE);
        }
    }

    public static synchronized MusicManager getInstance(Context context) {
        if (instance == null) {
            instance = new MusicManager(context != null ? context.getApplicationContext() : null);
        }
        return instance;
    }

    // ➕ ADD SINGLE SONG
    public void addSong(HashMap<String, Object> map) {
        if (map == null) return;

        Object idObj = map.get("id");
        Object nameObj = map.get("name");
        Object artistObj = map.get("artist");

        String id = idObj != null ? idObj.toString() :
                (nameObj != null ? nameObj.toString() : "") + (artistObj != null ? artistObj.toString() : "");

        if (!id.isEmpty() && !ids.contains(id)) {
            ids.add(id);
            songList.add(map);
        }
    }

    // ➕ ADD MULTIPLE SONGS
    public void addSongs(ArrayList<HashMap<String, Object>> list) {
        if (list == null) return;
        for (HashMap<String, Object> map : list) {
            addSong(map);
        }
    }

    // 🔄 SET NEW LIST
    public void setList(ArrayList<HashMap<String, Object>> list) {
        songList.clear();
        ids.clear();
        addSongs(list);
    }

    // 🔀 SHUFFLE SONGS
    public void shuffleSongs() {
        Collections.shuffle(songList);
    }

    // 🔀 SET SHUFFLED COPY (BEST)
    public void setShuffledList(ArrayList<HashMap<String, Object>> list) {
        if (list == null) return;
        ArrayList<HashMap<String, Object>> temp = new ArrayList<>(list);
        Collections.shuffle(temp);
        setList(temp);
    }

    // 📦 MERGE LISTS
    @SafeVarargs
    public final void mergeLists(ArrayList<HashMap<String, Object>>... lists) {
        if (lists == null) return;
        for (ArrayList<HashMap<String, Object>> list : lists) {
            addSongs(list);
        }
    }

    // 💾 SAVE SONGS (SHUFFLED ORDER SAVE)
    public void saveSongs() {
        if (sp == null) return;
        String json = gson.toJson(songList);
        sp.edit().putString("songs_backup", json).apply();
    }

    // 📥 LOAD SONGS
    public void loadSongs() {
        if (sp == null) return;
        String json = sp.getString("songs_backup", "");

        if (json != null && !json.isEmpty()) {
            ArrayList<HashMap<String, Object>> savedList =
                    gson.fromJson(json,
                            new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());

            if (savedList != null) {
                setList(savedList);
            }
        }
    }

    // 📥 SET BACKUP LIST
    public void setBackup(ArrayList<HashMap<String, Object>> list) {
        backupList.clear();
        if (list != null) {
            backupList.addAll(list);
        }
    }

    // 🔁 GET SONG LIST
    public ArrayList<HashMap<String, Object>> getSongs() {
        return songList;
    }

    // 🧹 CLEAR ALL
    public void clearAll() {
        songList.clear();
        backupList.clear();
        ids.clear();
        if (sp != null) {
            sp.edit().clear().apply();
        }
    }
}
