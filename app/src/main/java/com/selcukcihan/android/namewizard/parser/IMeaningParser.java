package com.selcukcihan.android.namewizard.parser;

import com.selcukcihan.android.namewizard.Name;

/**
 * Created by SELCUKCI on 9.6.2016.
 */
public interface IMeaningParser {
    String parse(String response);
    String generateUrl();
    Name name();
}
