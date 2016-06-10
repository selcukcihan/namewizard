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

import android.content.Context;
import android.support.v4.app.Fragment;

import com.selcukcihan.android.namewizard.wizard.ui.BirthDateFragment;
import com.selcukcihan.android.namewizard.wizard.ui.ParentNamesFragment;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A page asking for a name and an email.
 */
public class BirthDatePage extends Page {
    public static final String YEAR_KEY = "year";
    public static final String MONTH_KEY = "month";
    public static final String DAY_KEY = "day";

    public BirthDatePage(Context context, ModelCallbacks callbacks, String title) {
        super(context, callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return BirthDateFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(mData.getInt(YEAR_KEY), mData.getInt(MONTH_KEY), mData.getInt(DAY_KEY));
        dest.add(new ReviewItem("Expected Birth Date", String.format("%1$td %1$tB", calendar), getKey(), -1));
    }
}
