package com.example.tupa_mobile.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tupa_mobile.Connections.Connection;
import com.example.tupa_mobile.OpenWeather.OpenDaily;
import com.example.tupa_mobile.OpenWeather.OpenDailyAdapter;
import com.example.tupa_mobile.R;
import com.example.tupa_mobile.SettingsPage.Settings;
import com.example.tupa_mobile.SettingsPage.SettingsAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    private TextView txtResult;
    private RecyclerView userSettingsRecycler, appSettingsRecycler, infoSettingsRecycler;
    private ArrayList<Settings> settingsList;
    private SettingsAdapter adapter;
    private ArrayList<OpenDaily> dailies;
    private OpenDailyAdapter openAdapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        userSettingsRecycler = view.findViewById(R.id.userSettingsRecycler);
        userSettingsRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));

        appSettingsRecycler = view.findViewById(R.id.appSettingsRecycler);
        appSettingsRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));

        infoSettingsRecycler = view.findViewById(R.id.infoSettingsRecycler);
        infoSettingsRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));

        settingsList = new ArrayList<>();
        adapter = new SettingsAdapter(view.getContext(), settingsList);

        userSettingsRecycler.setAdapter(adapter);
        appSettingsRecycler.setAdapter(adapter);
        infoSettingsRecycler.setAdapter(adapter);

        for(int i=0; i<4; i++) {
            createListData("Configuração exemplo", "");
        }

        return view;
    }

    public void createListData(String title, String imgSource) {
        // This method adds data to the recyclerView
        Settings settings = new Settings(title, imgSource);
        settingsList.add(settings);
    }
}