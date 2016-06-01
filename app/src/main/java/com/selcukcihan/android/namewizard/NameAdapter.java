package com.selcukcihan.android.namewizard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by SELCUKCI on 1.6.2016.
 */
public class NameAdapter extends RecyclerView.Adapter<NameAdapter.ViewHolder> {

    private final NameFragment mNameFragment;
    private final NameEngine mEngine;

    public NameAdapter(NameFragment nameFragment, NameEngine engine) {
        mNameFragment = nameFragment;
        mEngine = engine;
        mEngine.next();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_name, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTextView.setText(mEngine.get(position).toString());
        holder.mMeaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpPerformingTask task = new HttpPerformingTask(mEngine.get(position));
                task.attach(mNameFragment);
                task.execute(mEngine.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEngine.count();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTextView;
        public final ImageButton mMeaning;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTextView = (TextView) view.findViewById(R.id.name);
            mMeaning = (ImageButton) view.findViewById(R.id.meaning);
        }
    }
}
