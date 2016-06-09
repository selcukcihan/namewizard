package com.selcukcihan.android.namewizard;

import java.text.Collator;
import java.util.List;

/**
 * Created by SELCUKCI on 1.6.2016.
 */
public class Name implements Comparable<Name> {
    private final String mName;
    private final boolean mMale;
    private final String mId;
    private final long mFreq;
    private String mMeaning;

    private static final Object lock = new Object();

    public Name(String name, boolean male, long freq, String id) {
        mName = name;
        mMale = male;
        mFreq = freq;
        mId = id;
        mMeaning = "";
    }

    public Name(String serialized) {
        String []values = serialized.split(" ");
        mName = values[0];
        mMale = (values[1].compareTo("1") == 0);
        mFreq = Long.parseLong(values[2]);
        if (values.length > 3) {
            mId = values[3];
        } else {
            mId = "";
        }
        mMeaning = "";
    }

    public String serialize() {
        return mName + " " + (mMale ? "1" : "0") + " " + mFreq + " " + mId;
    }

    @Override
    public String toString() {
        return mName;
    }

    public String id() {
        return mId;
    }

    public boolean male() {
        return mMale;
    }

    public long freq() {
        return mFreq;
    }

    public int compareTo(Name other) {
        Collator coll = Collator.getInstance();
        coll.setStrength(Collator.PRIMARY);
        return coll.compare(this.toString(), other.toString());
    }

    public String meaning() {
        String m;
        synchronized (lock) {
            m = mMeaning;
        }
        return m;
    }

    public void setMeaning(String nameMeaning) {
        synchronized (lock) {
            mMeaning = nameMeaning;
        }
    }

    public boolean in(List<Name> names) {
        for (Name n : names) {
            if (n.male() == this.male() && n.mName.compareTo(this.mName) == 0) {
                return true;
            }
        }
        return false;
    }
}
