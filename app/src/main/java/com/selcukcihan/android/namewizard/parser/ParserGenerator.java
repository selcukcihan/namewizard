package com.selcukcihan.android.namewizard.parser;

import com.selcukcihan.android.namewizard.MyLocale;
import com.selcukcihan.android.namewizard.Name;

import java.util.Locale;

/**
 * Created by SELCUKCI on 9.6.2016.
 */
public class ParserGenerator {
    public static IMeaningParser generate(Name name) {
        String lang = MyLocale.getLanguage();
        switch (lang) {
            case "en":
                return new EnglishParser(name);
            case "tr":
                return new TurkishParser(name);
            default:
                return null;
        }
    }
}
