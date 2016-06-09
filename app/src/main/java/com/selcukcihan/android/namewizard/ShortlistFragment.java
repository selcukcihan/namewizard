package com.selcukcihan.android.namewizard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.selcukcihan.android.namewizard.wizard.model.UserData;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class ShortlistFragment extends Fragment implements HttpPerformingTask.HttpPerformingTaskListener {

    public interface OnCompleteListener {
        void onComplete(UserData userData);
    }

    private OnCompleteListener mListener;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    public static final int USER_DATA_REQUEST = 1;

    protected RecyclerView mList;
    protected TextView mEmptyText;
    protected UserData mUserData;

    public ShortlistFragment() {
        // Required empty public constructor
    }

    public static ShortlistFragment newInstance(Context context) {
        ShortlistFragment fragment = new ShortlistFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name_list, container, false);
        mList = (RecyclerView) view.findViewById(R.id.list);
        mEmptyText = (TextView) view.findViewById(R.id.empty_view);

        mList.setLayoutManager(new LinearLayoutManager(mList.getContext()));
        mList.addItemDecoration(new DividerItemDecoration(getActivity()));

        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    protected void handleEmptyOrNot(List<Name> names) {
        if (names.size() == 0) {
            mList.setVisibility(View.GONE);
            mEmptyText.setVisibility(View.VISIBLE);
        } else {
            mList.setVisibility(View.VISIBLE);
            mEmptyText.setVisibility(View.GONE);
        }
    }

    protected void initializeStuff() {
        Shortlist shortlist = new Shortlist(getContext());
        List<Name> names = shortlist.getAll();
        handleEmptyOrNot(names);
        mList.setAdapter(new NameAdapter(this, names));
        ((SwipeRefreshLayout) getView().findViewById(R.id.swipe_refresh_layout)).setDistanceToTriggerSync(99999);
    }

    protected void refresh() {
        NameAdapter adapter = (NameAdapter)mList.getAdapter();
        Shortlist shortlist = new Shortlist(getContext());
        adapter.loadShortlist(shortlist);

        List<Name> names = new LinkedList<>();
        for (Name n : shortlist.getAll()) {
            names.add(n);
        }
        handleEmptyOrNot(names);
        adapter.refreshItems(names);
    }

    protected void launchActivity() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mUserData = UserData.newInstance(getContext());
        if (mUserData != null) {
            initializeStuff();
            mListener.onComplete(mUserData);
        } else {
            launchActivity();
        }
    }

    @Override
    public void onCompleted(Name name) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle(name.toString());
        alertDialog.setMessage(name.meaning());
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getContext().getString(R.string.dialog_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onFailure(String message) {
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
