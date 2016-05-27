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

package com.selcukcihan.android.namewizard;

import com.selcukcihan.android.namewizard.wizard.model.AbstractWizardModel;
import com.selcukcihan.android.namewizard.wizard.model.BirthDatePage;
import com.selcukcihan.android.namewizard.wizard.model.PageList;
import com.selcukcihan.android.namewizard.wizard.model.ParentNamesPage;
import com.selcukcihan.android.namewizard.wizard.model.SingleFixedChoicePage;

import android.content.Context;

public class SandwichWizardModel extends AbstractWizardModel {
    public SandwichWizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        return new PageList(
                new SingleFixedChoicePage(this, mContext.getString(R.string.gender)).setChoices(
                        mContext.getString(R.string.male),
                        mContext.getString(R.string.female)).setRequired(true),
                new BirthDatePage(this, mContext.getString(R.string.birth_date)).setRequired(true),
                new ParentNamesPage(this, mContext.getString(R.string.family_names))
        );
    }
}
