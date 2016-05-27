package com.selcukcihan.android.namewizard.wizard.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;

import com.selcukcihan.android.namewizard.R;

/**
 * Created by SELCUKCI on 26.5.2016.
 */
public class UserData implements Parcelable {
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

    public UserData(Parcel in){
        mMother = in.readString();
        mFather = in.readString();
        mSurname = in.readString();
        mMonth = in.readInt();
        mDay = in.readInt();
        mMale = (in.readInt() > 0 ? true : false);
    }

    public void persist(SharedPreferences.Editor editor) {
        editor.putString("saved_mother", getMother());
        editor.putString("saved_father", getFather());
        editor.putString("saved_surname", getSurname());
        editor.putInt("saved_month", getMonth());
        editor.putInt("saved_day", getDay());
        editor.putBoolean("saved_gender", isMale());
        editor.commit();
    }

    public static UserData newInstance(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (prefs.contains("saved_gender")) {
            boolean male = prefs.getBoolean("saved_gender", false);
            int month = prefs.getInt("saved_month", 1);
            int day = prefs.getInt("saved_day", 1);
            String mother = prefs.getString("saved_mother", "");
            String father = prefs.getString("saved_father", "");
            String surname = prefs.getString("saved_surname", "");
            return new UserData(male, mother, father, surname, month, day);
        } else {
            return null;
        }
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getMother());
        dest.writeString(getFather());
        dest.writeString(getSurname());
        dest.writeInt(getMonth());
        dest.writeInt(getDay());
        dest.writeInt(isMale() ? 1 : 0);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };
}
