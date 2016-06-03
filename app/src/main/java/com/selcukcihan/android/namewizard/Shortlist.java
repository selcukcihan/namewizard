package com.selcukcihan.android.namewizard;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by SELCUKCI on 3.6.2016.
 */
public class Shortlist {
    private final Context mContext;
    private List<String> mNames;
    public Shortlist(Context context) {
        mContext = context;
        init();
    }

    public List<String> getAll() {
        return mNames;
    }

    public boolean has(Name name) {
        Log.e("Shortlist", "has " + name.id() + " - " + name.toString() + " - " + (mNames.contains(name.id()) ? "found" : "not found"));
        return mNames.contains(name.id());
    }

    public void add(Name name) {
        Log.e("Shortlist", "add: " + name.id());
        mNames.add(name.id());
        persist();
    }

    public void remove(Name name) {
        Log.e("Shortlist", "del: " + name.id());
        mNames.remove(name);
        persist();
    }

    private void init() {
        mNames = new LinkedList<>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        Log.e("Shortlist", "Shortlist init");
        if (prefs.contains("shortlist")) {
            String str = prefs.getString("shortlist", "");
            String [] ids = str.split(" ");
            for (int i = 0; i < ids.length; i++) {
                mNames.add(ids[i]);
            }
        }
        Log.e("Shortlist", "initialized: " + serialize());
    }

    private void persist() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
        String str = serialize();
        Log.e("Shortlist", "persist: " + str);
        editor.putString("shortlist", str);
        editor.commit();
    }

    private String serialize() {
        StringBuilder str = new StringBuilder();
        for (String id : mNames) {
            str.append(id);
            str.append(" ");
        }
        return str.toString();
    }


}
