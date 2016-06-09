package com.selcukcihan.android.namewizard;

import java.util.Locale;

/**
 * Created by SELCUKCI on 9.6.2016.
 */
public class MyLocale {
    public static String getLanguage() {
        return Locale.getDefault().getLanguage();
        //return "en";
    }
}
