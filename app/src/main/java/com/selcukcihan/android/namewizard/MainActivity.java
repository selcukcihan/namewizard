package com.selcukcihan.android.namewizard;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.selcukcihan.android.namewizard.wizard.model.UserData;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ShortlistFragment.OnCompleteListener {
    private ViewPager mPager;
    private MainPagerAdapter mAdapter;
    private TabPageChangeListener mTabPageChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new MainPagerAdapter(getSupportFragmentManager(), this);
        mPager.setAdapter(mAdapter);

        mTabPageChangeListener = new TabPageChangeListener(0);
        mPager.addOnPageChangeListener(mTabPageChangeListener);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        this.setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_settings:
                return handleSettings();
            case R.id.menu_item_clear_favorites:
                return handleClearFavorites();
            case R.id.menu_item_about:
                return handleAbout();
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean handleAbout() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
        dialog.setContentView(R.layout.about_view);
        dialog.setTitle(R.string.about);
        ((TextView) dialog.findViewById(R.id.selcuk)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) dialog.findViewById(R.id.database)).setMovementMethod(LinkMovementMethod.getInstance());
        dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.mipmap.ic_launcher);
        /*
        alertDialog.setView(R.layout.about_view);

        alertDialog.setMessage("Alert message to be shown");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });*/
        dialog.show();
        return true;
    }

    private boolean handleSettings() {
        Intent intent = new Intent(MainActivity.this, SetupActivity.class);
        startActivityForResult(intent, NameFragment.USER_DATA_REQUEST);
        return true;
    }

    private boolean handleClearFavorites() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setMessage(R.string.dialog_clear_favorites).setTitle(R.string.dialog_title);
        adb.setIcon(android.R.drawable.ic_dialog_alert);

        adb.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
                UserData userData = UserData.newInstance(MainActivity.this);
                if (userData != null) {
                    editor.putString(userData.isMale() ? "male shortlist" : "female shortlist", "");
                } else {
                    editor.putString("male shortlist", "");
                    editor.putString("female shortlist", "");
                }
                editor.commit();
                mTabPageChangeListener.refresh();
            }
        });
        adb.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        adb.show();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onComplete(UserData userData) {
        // Fragment's callback
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mAdapter.notifyDataSetChanged();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mPager);
        //tabLayout.setupWithViewPager(mPager);
    }

    private final class TabPageChangeListener implements ViewPager.OnPageChangeListener {
        private int mPreviousPosition;

        public TabPageChangeListener(int previousPosition) {
            mPreviousPosition = previousPosition;
        }
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            ShortlistFragment fragment = mAdapter.getRegisteredFragment(mPreviousPosition);
            if (fragment != null && position != mPreviousPosition) {
                fragment = mAdapter.getRegisteredFragment(position);
                fragment.refresh();
            }
            mPreviousPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        public void refresh() {
            ShortlistFragment fragment = mAdapter.getRegisteredFragment(mPreviousPosition);
            if (fragment != null) {
                fragment.refresh();
            }
        }
    }
}
