package com.selcukcihan.android.namewizard;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by SELCUKCI on 9.6.2016.
 */
public class Bucket {
    public final long Low;
    public final long High;
    public final int Weight;
    public final List<Name> Names;

    public Bucket(long low, long high, int weight) {
        Low = low;
        High = high;
        Weight = weight;
        Names = new LinkedList<Name>();
    }
}
