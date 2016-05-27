package com.selcukcihan.android.namewizard;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.selcukcihan.android.namewizard.wizard.model.Page;
import com.selcukcihan.android.namewizard.wizard.model.UserData;

import java.util.ArrayList;


public class SetupFragment extends Fragment {
    private UserData mUserData = null;

    public SetupFragment() {
        // Required empty public constructor
    }

    public static SetupFragment newInstance(Context context) {
        UserData data = UserData.newInstance(context);
        Bundle bundle = new Bundle();
        bundle.putParcelable("user_data", data);
        SetupFragment fragment = new SetupFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            mUserData = bundle.getParcelable("user_data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setup, container, false);
        ((EditText) rootView.findViewById(R.id.mother)).setText(mUserData.getMother());
        ((EditText) rootView.findViewById(R.id.father)).setText(mUserData.getFather());
        ((EditText) rootView.findViewById(R.id.surname)).setText(mUserData.getSurname());

        final ArrayList<String> genders = new ArrayList<String>() {{
            add("Male");
            add("Female");
        }};
        final ListView listView = (ListView) rootView.findViewById(android.R.id.list);
        listView.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_single_choice,
                android.R.id.text1,
                genders));
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Pre-select currently selected item.
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < genders.size(); i++) {
                    if (genders.get(i).equals(mUserData.isMale() ? getActivity().getString(R.string.male) : getActivity().getString(R.string.female))) {
                        listView.setItemChecked(i, true);
                        break;
                    }
                }
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
