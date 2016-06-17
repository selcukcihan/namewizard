package com.selcukcihan.android.namewizard;

import android.content.Context;

import com.selcukcihan.android.namewizard.wizard.model.UserData;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by SELCUKCI on 27.5.2016.
 */
public class NameEngine {
    private final Context mContext;
    private final UserData mUserData;
    private final NameCollection mNames;
    private final MyRandom mRandom;
    private int mBucketPointer = 0;
    private List<Name> mCurrentlyFetched;
    private final int[] mBucketIndices;

    private List<List<Name>> mFetched;
    private int mPageIndex = -1;

    public NameEngine(Context context, UserData userData) {
        mContext = context;
        mUserData = userData;
        mNames = new NameCollection(context, userData, MyLocale.getLanguage()); // Locale.getDefault().getLanguage() + ".txt"
        mRandom = new MyRandom(seed());
        mBucketIndices = new int[mNames.TotalFrequency];
        mFetched = new LinkedList<>();
        computeBucketIndices();
    }

    private void computeBucketIndices() {
        for (int i = 0; i < mBucketIndices.length; i++) {
            mBucketIndices[i] = -1;
        }
        for (int i = mNames.Buckets.size() - 1; i >= 0; i--) {
            int k = 0;
            for (int j = 0; j < mNames.Buckets.get(i).Weight; j++) {
                boolean skipped = true;
                while(k < mBucketIndices.length){
                    if (mBucketIndices[k] == -1) {
                        if (skipped) {
                            mBucketIndices[k] = i;
                            skipped = false;
                        } else {
                            skipped = true;
                        }
                    }
                    k++;
                }
            }
        }
    }

    private int seed() {
        MessageDigest md = null;
        int hash = 0;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] bytesOfMessage = (mUserData.getFather() + " " + mUserData.getMother() + " " + mUserData.getSurname() + " " + System.currentTimeMillis()).getBytes("UTF-8");
            byte[] thedigest = md.digest(bytesOfMessage);
            ByteBuffer wrapped = ByteBuffer.wrap(thedigest); // big-endian by default
            hash = wrapped.getShort();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return (mUserData.getMonth() * 31 + mUserData.getDay() + (mUserData.isMale() ? 1000 : 500) + hash * 10000);
    }

    private Name getNameFrom(int bucketIndex, boolean forward) {
        Bucket bucket = mNames.Buckets.get(bucketIndex);
        if (bucket.Names.size() > 0) {
            int index = (forward ? mRandom.next(bucket.Names.size()) : mRandom.prev());
            if (index == -1) {
                return null;
            }
            if (index >= bucket.Names.size()) {
                return null;
            }
            Name name = bucket.Names.get(index);
            if (Arrays.asList(new String[]{mUserData.getSurname(), mUserData.getFather(), mUserData.getMother()}).contains(name.toString())) {
                return getNameFrom(bucketIndex, forward);
            } else {
                return name;
            }
        } else {
            return null;
        }
    }

    public List<Name> next() {
        if (mPageIndex + 1 == mFetched.size()){
            List<Name> names = new LinkedList<>();
            for (int i = 0; i < 6; i++) {
                Name name = getNameFrom(mBucketIndices[mBucketPointer], true);
                if (name != null && !name.in(names)) {
                    names.add(name);
                }
                mBucketPointer++;
                if (mBucketPointer >= mNames.TotalFrequency) {
                    mBucketPointer = 0;
                }
            }
            Collections.sort(names);
            mCurrentlyFetched = names;
            mFetched.add(mCurrentlyFetched);
            mPageIndex++;
            return names;
        } else {
            mPageIndex++;
            mCurrentlyFetched = mFetched.get(mPageIndex);
            return mCurrentlyFetched;
        }
    }

    public List<Name> prev() {
        if (mPageIndex <= 0) {
            mPageIndex = 0;
            return mCurrentlyFetched;
        } else {
            mPageIndex--;
            mCurrentlyFetched = mFetched.get(mPageIndex);
            return mCurrentlyFetched;
        }
    }

    public List<Name> current() {
        return mCurrentlyFetched;
    }
}
