package com.selcukcihan.android.namewizard;

import junit.framework.Test;
import junit.framework.TestResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Selcuk on 16.2.2016.
 */
public class HttpCommunicator {

    public String fetch(String url) throws IOException {
        String response = performGetCall(url);
        return parseResponse(response);
    }

    private String parseResponse(String response) {
        Document doc = Jsoup.parse(response);

        Element e = doc.select("td:containsOwn(Anlam:)").first();
        e = (e != null ? e.nextElementSibling() : null);
        if (e != null) {
            return e.text();
        }
        return "";
    }

    private String performGetCall(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            //InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        } finally {
            urlConnection.disconnect();
        }
    }
}
