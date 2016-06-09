package com.selcukcihan.android.namewizard.parser;

import com.selcukcihan.android.namewizard.Name;

/**
 * Created by SELCUKCI on 9.6.2016.
 */
public abstract class MeaningParser implements IMeaningParser {
    protected final Name mName;

    public MeaningParser(Name name) {
        mName = name;
    }

    @Override
    public Name name() {
        return mName;
    }
}
