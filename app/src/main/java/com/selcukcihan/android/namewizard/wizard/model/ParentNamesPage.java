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

package com.selcukcihan.android.namewizard.wizard.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.selcukcihan.android.namewizard.wizard.ui.CustomerInfoFragment;
import com.selcukcihan.android.namewizard.wizard.ui.ParentNamesFragment;

import java.util.ArrayList;

/**
 * A page asking for a name and an email.
 */
public class ParentNamesPage extends Page {
    public static final String MOTHER_DATA_KEY = "mother";
    public static final String FATHER_DATA_KEY = "father";
    public static final String SURNAME_DATA_KEY = "surname";

    public ParentNamesPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return ParentNamesFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Mother's name", mData.getString(MOTHER_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Father's name", mData.getString(FATHER_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Surname", mData.getString(SURNAME_DATA_KEY), getKey(), -1));
    }
}
