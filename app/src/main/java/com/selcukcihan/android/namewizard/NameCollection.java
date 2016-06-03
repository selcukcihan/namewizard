package com.selcukcihan.android.namewizard;

import android.content.Context;

import com.selcukcihan.android.namewizard.wizard.model.UserData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by SELCUKCI on 3.6.2016.
 */
public class NameCollection {
    protected final Context mContext;
    protected final UserData mUserData;
    private final List<Name> mNames;

    public NameCollection(Context context, UserData userData) {
        mContext = context;
        mUserData = userData;
        mNames = new LinkedList<>();
    }

    public Name get(int position) {
        return mNames.get(position);
    }

    public int count() {
        return mNames.size();
    }

    public void next() {
        return;
    }

    public void initialize(LinkedList<Name> names) {
        Shortlist shortlist = new Shortlist(mContext);
        for (String id : shortlist.getAll()) {
            for (Name n : names) {
                if (n.id().compareTo(id) == 0) {
                    mNames.add(n);
                    break;
                }
            }
        }
        Collections.sort(mNames);
    }
}
