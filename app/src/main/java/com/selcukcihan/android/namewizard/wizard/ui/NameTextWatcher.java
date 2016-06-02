package com.selcukcihan.android.namewizard.wizard.ui;

import android.text.Editable;
import android.text.TextWatcher;

import com.selcukcihan.android.namewizard.wizard.model.Page;
import com.selcukcihan.android.namewizard.wizard.model.ParentNamesPage;

/**
 * Created by SELCUKCI on 26.5.2016.
 */
public class NameTextWatcher implements TextWatcher {
    private ParentNamesPage mPage;
    private String mKey;

    public NameTextWatcher(ParentNamesPage page, String key) {
        super();
        mPage = page;
        mKey = key;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                                  int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        mPage.getData().putString(mKey,
                (editable != null) ? editable.toString() : null);
        mPage.notifyDataChanged();
    }
}
