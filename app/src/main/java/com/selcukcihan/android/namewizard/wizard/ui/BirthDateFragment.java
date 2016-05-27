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
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.selcukcihan.android.namewizard.R;
import com.selcukcihan.android.namewizard.wizard.model.BirthDatePage;

import java.lang.reflect.Field;
import java.util.Calendar;

public class BirthDateFragment extends Fragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private BirthDatePage mPage;
    private DatePicker mBirthDate;

    public static BirthDateFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        BirthDateFragment fragment = new BirthDateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public BirthDateFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (BirthDatePage) mCallbacks.onGetPage(mKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page_birth_date, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        mBirthDate = ((DatePicker) rootView.findViewById(R.id.birth_date));
        mBirthDate.updateDate(mPage.getData().getInt(BirthDatePage.YEAR_KEY), mPage.getData().getInt(BirthDatePage.MONTH_KEY), mPage.getData().getInt(BirthDatePage.DAY_KEY));

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
        Calendar calendar = Calendar.getInstance();
        mBirthDate.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                mPage.getData().putInt(BirthDatePage.YEAR_KEY, year);
                mPage.getData().putInt(BirthDatePage.MONTH_KEY, month);
                mPage.getData().putInt(BirthDatePage.DAY_KEY, dayOfMonth);
                mPage.notifyDataChanged();
            }
        });
        hideYear();
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        /*
        // In a future update to the support library, this should override setUserVisibleHint
        // instead of setMenuVisibility.
        if (mMotherView != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            if (!menuVisible) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        }*/
    }

    public void hideYear(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            int yearSpinnerId = Resources.getSystem().getIdentifier("year", "id", "android");
            if (yearSpinnerId != 0)
            {
                View yearSpinner = mBirthDate.findViewById(yearSpinnerId);
                if (yearSpinner != null)
                {
                    yearSpinner.setVisibility(View.GONE);
                }
            }
        } else { //Older SDK versions
            Field f[] = mBirthDate.getClass().getDeclaredFields();
            for (Field field : f)
            {
                if(field.getName().equals("mYearPicker") || field.getName().equals("mYearSpinner"))
                {
                    field.setAccessible(true);
                    Object yearPicker = null;
                    try {
                        yearPicker = field.get(mBirthDate);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    ((View) yearPicker).setVisibility(View.GONE);
                }
            }
        }
    }
}
