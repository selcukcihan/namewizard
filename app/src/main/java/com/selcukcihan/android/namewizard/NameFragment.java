package com.selcukcihan.android.namewizard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.selcukcihan.android.namewizard.wizard.model.UserData;


public class NameFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final int USER_DATA_REQUEST = 1;

    private ListView mListView;
    private SwipeRefreshLayout mSwipe;
    private NameEngine mEngine;
    private UserData mUserData;

    public NameFragment() {
        // Required empty public constructor
    }

    public static NameFragment newInstance(Context context) {
        NameFragment fragment = new NameFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_name, container, false);

        mListView = (ListView) view.findViewById(R.id.list_names);
        mSwipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipe.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("MainActivity", "onResume called");
        initializeData();
    }

    @Override
    public void onRefresh() {
        refreshData();
    }

    private void refreshData() {
        //ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, R.layout.list_item, generateNames());
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) mListView.getAdapter();
        adapter.clear();
        adapter.addAll(mEngine.fetch());
        adapter.notifyDataSetChanged();
        mSwipe.setRefreshing(false);
    }

    public void initializeWidgets() {
        mEngine = new NameEngine(getContext(), mUserData);
        mListView.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.list_item));

        mSwipe.post(new Runnable() {
            @Override
            public void run() {
                mSwipe.setRefreshing(true);
                refreshData();
            }
        });
    }

    private void initializeData() {
        mUserData = UserData.newInstance(getContext());
        if (mUserData != null) {
            initializeWidgets();
        } else {
            Intent intent = new Intent(getContext(), SetupActivity.class);
            startActivityForResult(intent, USER_DATA_REQUEST);
        }
    }
}
