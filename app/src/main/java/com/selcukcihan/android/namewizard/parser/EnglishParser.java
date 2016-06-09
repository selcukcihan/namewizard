package com.selcukcihan.android.namewizard.parser;

import com.selcukcihan.android.namewizard.Name;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by SELCUKCI on 9.6.2016.
 */
public class EnglishParser extends MeaningParser {
    public EnglishParser(Name name) {
        super(name);
    }
    @Override
    public String parse(String response) {
        Document doc = Jsoup.parse(response);
        Element e = doc.select("ul.list-inline li:contains(Meaning:)").first();
        String str = (e == null ? "" : e.text());
        return str.replaceFirst("Meaning:", "");
    }

    @Override
    public String generateUrl() {
        return "http://www.names.org/n/" + mName.toString() + "/about";
        //return "http://babynames.net/names?query_string=" + mName.toString();
    }
}
