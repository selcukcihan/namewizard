package com.selcukcihan.android.namewizard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.selcukcihan.android.namewizard.wizard.model.UserData;


public class ShortlistFragment extends Fragment implements HttpPerformingTask.HttpPerformingTaskListener {
    public static final int USER_DATA_REQUEST = 1;

    protected RecyclerView mList;
    protected NameCollection mNames;
    protected NameEngine mEngine;
    protected UserData mUserData;

    public ShortlistFragment() {
        // Required empty public constructor
    }

    public static ShortlistFragment newInstance(Context context) {
        ShortlistFragment fragment = new ShortlistFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name_list, container, false);
        mList = (RecyclerView) view.findViewById(R.id.list);

        mList.setLayoutManager(new LinearLayoutManager(mList.getContext()));
        mList.addItemDecoration(new DividerItemDecoration(getActivity()));

        return view;
    }

    protected void initializeWidgets() {

    }

    protected void initializeData() {
        mEngine = new NameEngine(getContext(), mUserData);
        mNames = new NameCollection(getContext(), mUserData);
        mNames.initialize(mEngine.getAll());
    }

    @Override
    public void onResume() {
        super.onResume();
        mUserData = UserData.newInstance(getContext());
        if (mUserData != null) {
            initializeData();
            mList.setAdapter(new NameAdapter(this, mNames));
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
