package com.selcukcihan.android.namewizard;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.selcukcihan.android.namewizard.wizard.model.UserData;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by SELCUKCI on 3.6.2016.
 */
public class Shortlist {
    private final Context mContext;
    private final UserData mUserData;
    private List<Name> mNames;
    private static final String SEPARATOR = "#";
    public Shortlist(Context context) {
        mContext = context;
        mUserData = UserData.newInstance(mContext);
        init();
    }

    public List<Name> getAll() {
        Collections.sort(mNames);
        return mNames;
    }

    public boolean has(Name name) {
        return name.in(mNames);
    }

    public void add(Name name) {
        mNames.add(name);
        persist();
    }

    public void remove(Name name) {
        for (Name n : mNames) {
            if (n.male() == name.male() && n.compareTo(name) == 0) {
                mNames.remove(n);
                break;
            }
        }
        persist();
    }

    private void init() {
        mNames = new LinkedList<>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        if (mUserData != null) {
            String prefString = mUserData.isMale() ? "male shortlist" : "female shortlist";
            String str = prefs.contains(prefString) ? prefs.getString(prefString, "") : "";
            if (!str.isEmpty()) {
                String[] serialized = str.split(SEPARATOR);
                for (int i = 0; i < serialized.length; i++) {
                    mNames.add(new Name(serialized[i]));
                }
            }
        }
    }

    private void persist() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
        String str = serialize();
        if (mUserData != null) {
            editor.putString(mUserData.isMale() ? "male shortlist" : "female shortlist", str);
            editor.commit();
        }
    }

    private String serialize() {
        StringBuilder str = new StringBuilder();
        for (Name n : mNames) {
            str.append(n.serialize()).append(SEPARATOR);
        }
        return str.toString();
    }

}
