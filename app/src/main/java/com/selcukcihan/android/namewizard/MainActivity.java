package com.selcukcihan.android.namewizard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.selcukcihan.android.namewizard.wizard.model.UserData;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final int USER_DATA_REQUEST = 1;
    public static final String USER_DATA_KEY = "user_data_key";
    private UserData mData;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initializeData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.contains("saved_gender")) {
            boolean male = prefs.getBoolean("saved_gender", false);
            int month = prefs.getInt("saved_month", 1);
            int day = prefs.getInt("saved_day", 1);
            String mother = prefs.getString("saved_mother", "");
            String father = prefs.getString("saved_father", "");
            String surname = prefs.getString("saved_surname", "");

            mData = new UserData(male, mother, father, surname, month, day);

            initializeWidgets();
        } else {
            Intent intent = new Intent(this, SetupActivity.class);
            startActivityForResult(intent, USER_DATA_REQUEST);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("MainActivity", "onResume called");
        initializeData();
    }

    private String[] generateNames() {
        return new String[]{
                "Selçuk",
                "Selim",
                "Sedat",
                "Semih",
                "Osman",
                "Kerem",
                "Erdal",
                "İbrahim",
                "Adem",
                "Onur"
        };
    }

    private void initializeWidgets() {
        mListView = (ListView) findViewById(R.id.list_names);
        String[] planets = new String[] { "Mercury", "Venus", "Earth", "Mars",
                "Jupiter", "Saturn", "Uranus", "Neptune"};

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, R.layout.list_item, generateNames());
        mListView.setAdapter(listAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("MainActivity", "onActivityResult called");
        // If the request went well (OK) and the request was PICK_CONTACT_REQUEST
        if (resultCode == Activity.RESULT_OK && requestCode == USER_DATA_REQUEST) {
            // Perform a query to the contact's content provider for the contact's name
            Bundle bundle = data.getExtras();
            mData = new UserData(bundle);

            initializeWidgets();
        }
    }
}
