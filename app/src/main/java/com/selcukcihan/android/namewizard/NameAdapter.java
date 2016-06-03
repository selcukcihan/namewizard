package com.selcukcihan.android.namewizard;

import android.graphics.PorterDuff;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by SELCUKCI on 1.6.2016.
 */
public class NameAdapter extends RecyclerView.Adapter<NameAdapter.ViewHolder> {

    private final ShortlistFragment mNameFragment;
    private final NameCollection names;

    public NameAdapter(ShortlistFragment nameFragment, NameCollection names) {
        mNameFragment = nameFragment;
        this.names = names;
        this.names.next();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_name, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTextView.setText(names.get(position).toString());
        final Shortlist shortlist = new Shortlist(mNameFragment.getContext());
        if (shortlist.has(names.get(position))) {
            holder.mFavorite.setImageResource(R.drawable.ic_favorite_black_36dp);
        }
        holder.mFavorite.getDrawable().setColorFilter(ContextCompat.getColor(mNameFragment.getContext(), R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        holder.mFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shortlist.has(names.get(position))) {
                    shortlist.remove(names.get(position));
                    holder.mFavorite.setImageResource(R.drawable.ic_favorite_border_black_36dp);
                    holder.mFavorite.getDrawable().setColorFilter(ContextCompat.getColor(mNameFragment.getContext(), R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                } else {
                    shortlist.add(names.get(position));
                    holder.mFavorite.setImageResource(R.drawable.ic_favorite_black_36dp);
                    holder.mFavorite.getDrawable().setColorFilter(ContextCompat.getColor(mNameFragment.getContext(), R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                }
            }
        });
        holder.mMeaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpPerformingTask task = new HttpPerformingTask(names.get(position));
                task.attach(mNameFragment);
                task.execute();
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.count();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTextView;
        public final ImageButton mFavorite;
        public final ImageButton mMeaning;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTextView = (TextView) view.findViewById(R.id.name);
            mFavorite = (ImageButton) view.findViewById(R.id.favorite);
            mMeaning = (ImageButton) view.findViewById(R.id.meaning);
        }
    }
}
