package com.selcukcihan.android.namewizard;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.selcukcihan.android.namewizard.wizard.model.UserData;

/**
 * Created by SELCUKCI on 25.5.2016.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {
    private final Context mContext;

    private SparseArray<ShortlistFragment> mRegisteredFragments = new SparseArray<ShortlistFragment>();

    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return NameFragment.newInstance(mContext);
            case 1:
                return ShortlistFragment.newInstance(mContext);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            String title = mContext.getString(R.string.suggestions);
            SpannableString sb = new SpannableString(title + "  ");

            UserData userData = UserData.newInstance(mContext);
            if (userData != null) {
                Drawable image = ContextCompat.getDrawable(mContext, userData.isMale() ? R.drawable.ic_gender_male_white_24dp : R.drawable.ic_gender_female_white_24dp);
                image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
                ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
                sb.setSpan(imageSpan, title.length() + 1, title.length() + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return sb;
        } else {
            String title = mContext.getString(R.string.shortlist);
            Drawable image = ContextCompat.getDrawable(mContext, R.drawable.ic_favorite_black_24dp);
            image.setColorFilter(ContextCompat.getColor(mContext, R.color.colorLight), PorterDuff.Mode.SRC_ATOP);
            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
            SpannableString sb = new SpannableString(title + "   ");
            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
            sb.setSpan(imageSpan, title.length() + 2, title.length() + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ShortlistFragment fragment = (ShortlistFragment) super.instantiateItem(container, position);
        mRegisteredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mRegisteredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public ShortlistFragment getRegisteredFragment(int position) {
        return mRegisteredFragments.get(position);
    }
}
