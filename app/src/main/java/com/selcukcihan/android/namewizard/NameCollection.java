package com.selcukcihan.android.namewizard;

import android.content.Context;

import com.selcukcihan.android.namewizard.wizard.model.UserData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by SELCUKCI on 9.6.2016.
 */
public class NameCollection {
    private final Context mContext;
    private final UserData mUserData;
    public final List<Bucket> Buckets;

    public final int TotalFrequency;

    public NameCollection(Context context, UserData userData, String lang) {
        mContext = context;
        mUserData = userData;
        Buckets = new LinkedList<Bucket>();

        if (initializeFrequencies(lang + "_freq.txt")) {
            initializeNames(lang + ".txt");
        }
        int totalFrequency = 0;
        for (Bucket b : Buckets) {
            totalFrequency += b.Weight;
        }
        TotalFrequency = totalFrequency;
    }

    private boolean initializeFrequencies(String assetName) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(mContext.getAssets().open(assetName)));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                Buckets.add(new Bucket(Long.parseLong(tokens[0]), Long.parseLong(tokens[1]), Integer.parseInt(tokens[2])));
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private void initializeNames(String assetName) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(mContext.getAssets().open(assetName)));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                long freq = Long.parseLong(tokens[2]);
                if ((tokens[1].compareTo("1") == 0) == mUserData.isMale()) {
                    for (Bucket b : Buckets) {
                        if (freq >= b.Low && freq <= b.High) {
                            b.Names.add(new Name(tokens[0], mUserData.isMale(), freq, (tokens.length > 3 ? tokens[3] : "")));
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
