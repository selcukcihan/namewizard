package com.selcukcihan.android.namewizard;

import android.content.Context;
import android.os.Message;

import com.selcukcihan.android.namewizard.wizard.model.UserData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by SELCUKCI on 27.5.2016.
 */
public class NameEngine {
    private final Context mContext;
    private final UserData mUserData;
    private final List<List<Name>> mNames;
    private final int[] mFrequencies = {8, 4, 2, 1};
    private final Random mRandom;
    private int mCount = 0;
    private List<Name> mCurrentlyFetched;

    public NameEngine(Context context, UserData userData) {
        mContext = context;
        mUserData = userData;
        mNames = new ArrayList<>(4);
        mRandom = new Random(seed());
        for (int i = 0; i < mFrequencies.length; i++) {
            mNames.add(new LinkedList<Name>());
        }
        initializeData("names.txt");
    }

    private int seed() {
        MessageDigest md = null;
        int hash = 0;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] bytesOfMessage = (mUserData.getFather() + " " + mUserData.getMother() + " " + mUserData.getSurname()).getBytes("UTF-8");
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

    private Name getNameFrom(int bucketIndex) {
        List<Name> bucket = mNames.get(bucketIndex);
        int index = mRandom.nextInt(bucket.size());
        Name name = bucket.get(index);
        if (Arrays.asList(new String[] {mUserData.getSurname(), mUserData.getFather(), mUserData.getMother()}).contains(name.toString())) {
            return getNameFrom(bucketIndex);
        } else {
            //bucket.remove(index);
            return name;
        }
    }

    public void next() {
        mCurrentlyFetched = fetch();
    }

    public int count() {
        return mCurrentlyFetched.size();
    }

    public Name get(int position) {
        if (position < mCurrentlyFetched.size()) {
            return mCurrentlyFetched.get(position);
        }
        return null;
    }

    public List<Name> fetch() {
        List<Name> names = new LinkedList<>();
        for (int i = 0; i < 6; i++) {
            mCount++;
            for (int j = 0; j < mFrequencies.length; j++) {
                if (mCount % mFrequencies[j] == 0) {
                    names.add(getNameFrom(j));
                    break;
                }
            }
        }
        Collections.sort(names);
        return names;
    }

    private void initializeData(String assetName) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(mContext.getAssets().open(assetName)));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                long freq = Long.parseLong(tokens[2]);
                int index = (int)(Math.round(Math.log10(freq / 10000)));
                if (index < 0) {
                    index = 0;
                } else if (index >= mNames.size()) {
                    index = mNames.size() - 1;
                }
                if (Boolean.parseBoolean(tokens[1]) == mUserData.isMale()) {
                    mNames.get(index).add(new Name(tokens[0], mUserData.isMale(), freq, tokens[3]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
