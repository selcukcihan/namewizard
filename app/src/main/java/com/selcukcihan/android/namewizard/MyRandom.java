package com.selcukcihan.android.namewizard;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by SELCUKCI on 9.6.2016.
 */
public class MyRandom {
    private final Random mRandom;
    private final List<Integer> mGenerated;
    private int mIndex;
    public MyRandom(int seed) {
        mRandom = new Random(seed);
        mGenerated = new LinkedList<>();
        mIndex = -1;
    }

    public int next(int max) {
        mIndex++;
        if (mIndex < mGenerated.size()) {
            return mGenerated.get(mIndex);
        } else {
            int n = mRandom.nextInt(max);
            mGenerated.add(n);
            return n;
        }
    }

    public int prev() {
        mIndex--;
        if (mGenerated.size() > 1) {
            if (mIndex < 0) {
                mIndex = mGenerated.size() - 1;
            }
            return mGenerated.get(mIndex);
        } else {
            mIndex++;
            return -1;
        }
    }
}
