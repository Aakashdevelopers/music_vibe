package com.music.vibe;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

public class MultiPlaylistManager {

    private static MultiPlaylistManager instance;

    private SharedPreferences sp;
    private Gson gson = new Gson();

    private static final String PREF_NAME = "multi_playlist";
    private static final String KEY_ALL = "all_playlists";

    private MultiPlaylistManager(Context context) {
        sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static MultiPlaylistManager getInstance(Context context) {
        if (instance == null) {
            instance = new MultiPlaylistManager(context);
        }
        return instance;
    }

    // 📥 GET ALL PLAYLISTS
    public HashMap<String, ArrayList<HashMap<String, Object>>> getAll() {

        String json = sp.getString(KEY_ALL, "");

        if (json.equals("")) {
            return new HashMap<>();
        }

        return gson.fromJson(
                json,
                new TypeToken<HashMap<String, ArrayList<HashMap<String, Object>>>>(){}.getType()
        );
    }

    // 💾 SAVE ALL
    private void saveAll(HashMap<String, ArrayList<HashMap<String, Object>>> map) {
        sp.edit().putString(KEY_ALL, gson.toJson(map)).apply();
    }

    // ➕ CREATE PLAYLIST
    public void createPlaylist(String name) {

        HashMap<String, ArrayList<HashMap<String, Object>>> all = getAll();

        if (!all.containsKey(name)) {
            all.put(name, new ArrayList<>());
            saveAll(all);
        }
    }

    // ❌ DELETE PLAYLIST
    public void deletePlaylist(String name) {

        HashMap<String, ArrayList<HashMap<String, Object>>> all = getAll();

        if (all.containsKey(name)) {
            all.remove(name);
            saveAll(all);
        }
    }

    // ✏️ RENAME PLAYLIST
    public void renamePlaylist(String oldName, String newName) {

        HashMap<String, ArrayList<HashMap<String, Object>>> all = getAll();

        if (all.containsKey(oldName)) {

            ArrayList<HashMap<String, Object>> list = all.get(oldName);

            all.remove(oldName);
            all.put(newName, list);

            saveAll(all);
        }
    }

    // ➕ ADD SONG
    public void addSong(String playlistName, HashMap<String, Object> song) {

        HashMap<String, ArrayList<HashMap<String, Object>>> all = getAll();

        if (!all.containsKey(playlistName)) return;

        ArrayList<HashMap<String, Object>> list = all.get(playlistName);

        boolean exists = false;

        for (HashMap<String, Object> s : list) {
            if (s.get("data").toString().equals(song.get("data").toString())) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            list.add(song);
            saveAll(all);
        }
    }

    // ➖ REMOVE SONG
    public void removeSong(String playlistName, String dataPath) {

        HashMap<String, ArrayList<HashMap<String, Object>>> all = getAll();

        if (!all.containsKey(playlistName)) return;

        ArrayList<HashMap<String, Object>> list = all.get(playlistName);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).get("data").toString().equals(dataPath)) {
                list.remove(i);
                break;
            }
        }

        saveAll(all);
    }

    // 📄 GET PLAYLIST
    public ArrayList<HashMap<String, Object>> getPlaylist(String name) {

        HashMap<String, ArrayList<HashMap<String, Object>>> all = getAll();

        if (all.containsKey(name)) {
            return all.get(name);
        }

        return new ArrayList<>();
    }

    // 📃 GET PLAYLIST NAMES
    public ArrayList<String> getPlaylistNames() {

        HashMap<String, ArrayList<HashMap<String, Object>>> all = getAll();

        return new ArrayList<>(all.keySet());
    }
}