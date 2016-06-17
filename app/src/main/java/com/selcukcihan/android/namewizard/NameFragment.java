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

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.selcukcihan.android.namewizard.wizard.model.UserData;

import java.util.List;


public class NameFragment extends ShortlistFragment implements SwipyRefreshLayout.OnRefreshListener {

    private NameEngine mEngine;
    private SwipyRefreshLayout mSwipe;

    public NameFragment() {
        // Required empty public constructor
    }

    public static NameFragment newInstance(Context context) {
        NameFragment fragment = new NameFragment();
        return fragment;
    }

    @Override
    protected void initializeStuff() {
        mEngine = new NameEngine(getContext(), mUserData);
        mList.setAdapter(new NameAdapter(this, mEngine.next()));

        mSwipe = (SwipyRefreshLayout) getView().findViewById(R.id.swipe_refresh_layout);
        mSwipe.setOnRefreshListener(this);

        /*
        ImageView icon = (ImageView) this.getActivity().findViewById(R.id.toolbar_icon);
        if (mUserData.isMale()) {
            icon.setImageResource(R.drawable.ic_gender_male_white_36dp);
        } else {
            icon.setImageResource(R.drawable.ic_gender_female_white_36dp);
        }
        //icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_gender_female_white_36dp, getApplicationContext().getTheme()));*/
        /*
        mSwipe.post(new Runnable() {
            @Override
            public void run() {
                mSwipe.setRefreshing(true);
                refreshData(true);
            }
        });*/
        ((SwipyRefreshLayout) getView().findViewById(R.id.swipe_refresh_layout)).setDistanceToTriggerSync(50);
    }

    @Override
    protected void refresh() {
        NameAdapter adapter = (NameAdapter)mList.getAdapter();
        Shortlist shortlist = new Shortlist(getContext());
        adapter.loadShortlist(shortlist);
        List<Name> names = mEngine.current();
        adapter.refreshItems(names);
    }

    private void refreshData(boolean next) {
        List<Name> names = (next ? mEngine.next() : mEngine.prev());
        ((NameAdapter)mList.getAdapter()).refreshItems(names);
        mSwipe.setRefreshing(false);
    }

    @Override
    protected void launchActivity() {
        Intent intent = new Intent(getContext(), SetupActivity.class);
        startActivityForResult(intent, USER_DATA_REQUEST);
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        refreshData(direction == SwipyRefreshLayoutDirection.TOP);
    }
}
