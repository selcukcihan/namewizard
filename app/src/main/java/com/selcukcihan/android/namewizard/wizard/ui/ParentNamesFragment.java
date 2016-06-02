/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.selcukcihan.android.namewizard.wizard.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.selcukcihan.android.namewizard.R;
import com.selcukcihan.android.namewizard.wizard.model.BirthDatePage;
import com.selcukcihan.android.namewizard.wizard.model.ParentNamesPage;
import com.selcukcihan.android.namewizard.wizard.model.SingleFixedChoicePage;
import com.selcukcihan.android.namewizard.wizard.model.UserData;

public class ParentNamesFragment extends Fragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private ParentNamesPage mPage;
    private TextView mMotherView;
    private TextView mFatherView;
    private TextView mSurnameView;

    public static ParentNamesFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        ParentNamesFragment fragment = new ParentNamesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ParentNamesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (ParentNamesPage) mCallbacks.onGetPage(mKey);

        UserData data = UserData.newInstance(this.getContext());
        if (data != null) {
            mPage.getData().putString(ParentNamesPage.MOTHER_DATA_KEY, data.getMother());
            mPage.getData().putString(ParentNamesPage.FATHER_DATA_KEY, data.getFather());
            mPage.getData().putString(ParentNamesPage.SURNAME_DATA_KEY, data.getSurname());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page_parent_names, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        mMotherView = ((TextView) rootView.findViewById(R.id.mother));
        mFatherView = ((TextView) rootView.findViewById(R.id.father));
        mSurnameView = ((TextView) rootView.findViewById(R.id.surname));

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }

        mCallbacks = (PageFragmentCallbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mPage.getData().containsKey(ParentNamesPage.MOTHER_DATA_KEY)) {
            mMotherView.setText(mPage.getData().getString(ParentNamesPage.MOTHER_DATA_KEY));
            mFatherView.setText(mPage.getData().getString(ParentNamesPage.FATHER_DATA_KEY));
            mSurnameView.setText(mPage.getData().getString(ParentNamesPage.SURNAME_DATA_KEY));
        }

        mMotherView.addTextChangedListener(new NameTextWatcher(mPage, ParentNamesPage.MOTHER_DATA_KEY));
        mFatherView.addTextChangedListener(new NameTextWatcher(mPage, ParentNamesPage.FATHER_DATA_KEY));
        mSurnameView.addTextChangedListener(new NameTextWatcher(mPage, ParentNamesPage.SURNAME_DATA_KEY));
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        // In a future update to the support library, this should override setUserVisibleHint
        // instead of setMenuVisibility.
        if (mMotherView != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            if (!menuVisible) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        }
    }
}
