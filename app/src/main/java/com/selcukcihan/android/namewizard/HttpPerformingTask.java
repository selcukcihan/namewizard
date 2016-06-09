package com.selcukcihan.android.namewizard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.selcukcihan.android.namewizard.parser.IMeaningParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by SELCUKCI on 16.2.2016.
 */
public class HttpPerformingTask extends AsyncTask<Void, Void, String> {

    private ProgressDialog mDialog;
    private final IMeaningParser mParser;

    public HttpPerformingTask(IMeaningParser parser) {
        super();
        mParser = parser;
    }

    public interface HttpPerformingTaskListener {
        void onCompleted(Name name);
        void onFailure(String message);
    }

    private ShortlistFragment mListener;

    public void attach(ShortlistFragment listener) {
        mListener = listener;
        mDialog = new ProgressDialog(listener.getContext());
    }

    private Exception mException;
    protected String doInBackground(Void... params) {
        try {
            if (mParser.name() == null) {
                return "";
            }
            if (mParser.name().meaning().isEmpty()) {
                HttpCommunicator communicator = new HttpCommunicator(mParser);
                return communicator.fetch(mParser.generateUrl());
            } else {
                return mParser.name().meaning();
            }
        } catch (Exception ex) {
            mException = ex;
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        if (mParser.name() != null) {
            mDialog.setMessage(String.format(mDialog.getContext().getResources().getString(R.string.waiting), mParser.name().toString()));
            mDialog.show();
        }
    }

    public Exception getException() { return mException; }

    @Override
    protected void onPostExecute(String meaning) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (meaning != null && !meaning.isEmpty()) {
            mParser.name().setMeaning(meaning);
            mListener.onCompleted(mParser.name());
        } else {
            if (mException != null) {
                mListener.onFailure(mException.getLocalizedMessage());
            } else if (mParser.name() != null) {
                mListener.onFailure("name not found");
            }
        }
    }
}
