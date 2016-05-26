package com.selcukcihan.android.namewizard.wizard.model;

import android.os.Bundle;

/**
 * Created by SELCUKCI on 26.5.2016.
 */
public class UserData {
    private String mMother;
    private String mFather;
    private String mSurname;
    private int mMonth;
    private int mDay;
    private boolean mMale;

    public UserData(boolean male, String mother, String father, String surname, int month, int day) {
        mMother = mother;
        mFather = father;
        mSurname = surname;
        mMonth = month;
        mDay = day;
        mMale = male;
    }

    public UserData(Bundle data) {
        mMother = data.getString(ParentNamesPage.MOTHER_DATA_KEY);
        mFather = data.getString(ParentNamesPage.FATHER_DATA_KEY);
        mSurname = data.getString(ParentNamesPage.SURNAME_DATA_KEY);
        mMonth = data.getInt(BirthDatePage.MONTH_KEY);
        mDay = data.getInt(BirthDatePage.DAY_KEY);
        mMale = data.getBoolean(Page.SIMPLE_DATA_KEY);
    }

    public String getMother() {
        return mMother;
    }

    public String getFather() {
        return mFather;
    }

    public String getSurname() {
        return mSurname;
    }

    public int getMonth() {
        return mMonth;
    }

    public int getDay() {
        return mDay;
    }

    public boolean isMale() {
        return mMale;
    }
}
