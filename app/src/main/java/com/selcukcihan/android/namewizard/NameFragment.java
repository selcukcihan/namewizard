package com.selcukcihan.android.namewizard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.selcukcihan.android.namewizard.wizard.model.UserData;


public class NameFragment extends ShortlistFragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipe;

    public NameFragment() {
        // Required empty public constructor
    }

    public static NameFragment newInstance(Context context) {
        NameFragment fragment = new NameFragment();
        return fragment;
    }

    @Override
    public void onRefresh() {
        refreshData();
    }

    @Override
    protected void initializeData() {
        super.initializeData();
        mNames = mEngine;
    }

    private void refreshData() {
        mNames.next();
        mList.getAdapter().notifyDataSetChanged();
        mSwipe.setRefreshing(false);
    }

    @Override
    protected void initializeWidgets() {
        mSwipe = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_refresh_layout);
        mSwipe.setOnRefreshListener(this);

        ImageView icon = (ImageView) this.getActivity().findViewById(R.id.toolbar_icon);
        if (mUserData.isMale()) {
            icon.setImageResource(R.drawable.ic_gender_male_white_36dp);
        } else {
            icon.setImageResource(R.drawable.ic_gender_female_white_36dp);
        }
        //icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_gender_female_white_36dp, getApplicationContext().getTheme()));

        mSwipe.post(new Runnable() {
            @Override
            public void run() {
                mSwipe.setRefreshing(true);
                refreshData();
            }
        });
    }

}
