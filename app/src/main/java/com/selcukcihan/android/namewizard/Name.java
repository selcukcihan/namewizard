package com.selcukcihan.android.namewizard;

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
        return this.toString().compareTo(other.toString());
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
}
