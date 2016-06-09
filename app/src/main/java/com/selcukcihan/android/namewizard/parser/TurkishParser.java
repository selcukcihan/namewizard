package com.selcukcihan.android.namewizard.parser;

import com.selcukcihan.android.namewizard.HttpCommunicator;
import com.selcukcihan.android.namewizard.Name;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by SELCUKCI on 9.6.2016.
 */
public class TurkishParser extends MeaningParser implements IMeaningParser {
    public TurkishParser(Name name) {
        super(name);
    }

    @Override
    public String parse(String response) {
        Document doc = Jsoup.parse(response);

        Element e = doc.select("td:containsOwn(Anlam:)").first();
        e = (e != null ? e.nextElementSibling() : null);
        if (e != null) {
            return e.text();
        }
        return "";
    }

    @Override
    public String generateUrl() {
        return "http://tdk.gov.tr/index.php?option=com_kisiadlari&arama=anlami&uid=" + mName.id() + "&guid=TDK.GTS.574e945ee2b1b4.87498362";
    }
}
