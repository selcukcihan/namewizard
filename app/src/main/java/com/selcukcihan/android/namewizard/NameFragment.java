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


public class NameFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, HttpPerformingTask.HttpPerformingTaskListener {
    public static final int USER_DATA_REQUEST = 1;

    private ListView mListView;
    private SwipeRefreshLayout mSwipe;
    private NameEngine mEngine;
    private UserData mUserData;
    private RecyclerView mList;

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
        View view = inflater.inflate(R.layout.fragment_name_list, container, false);
        mList = (RecyclerView) view.findViewById(R.id.list);

        mList.setLayoutManager(new LinearLayoutManager(mList.getContext()));
        mList.addItemDecoration(new DividerItemDecoration(getActivity()));

        //mListView = (ListView) view.findViewById(R.id.list_names);
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
        initializeData();
    }

    @Override
    public void onRefresh() {
        refreshData();
    }

    private void refreshData() {
        mEngine.next();
        mList.getAdapter().notifyDataSetChanged();
        mSwipe.setRefreshing(false);
    }

    public void initializeWidgets() {
        mEngine = new NameEngine(getContext(), mUserData);
        mList.setAdapter(new NameAdapter(this, mEngine));

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

    private void initializeData() {
        mUserData = UserData.newInstance(getContext());
        if (mUserData != null) {
            initializeWidgets();
        } else {
            Intent intent = new Intent(getContext(), SetupActivity.class);
            startActivityForResult(intent, USER_DATA_REQUEST);
        }
    }

    @Override
    public void onCompleted(String meaning) {
        Toast toast = Toast.makeText(getContext(), meaning, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onFailure(String message) {
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
