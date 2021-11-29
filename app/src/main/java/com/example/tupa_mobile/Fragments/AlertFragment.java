package com.example.tupa_mobile.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tupa_mobile.Connections.Connection;
import com.example.tupa_mobile.R;

public class AlertFragment extends Fragment {

    private RecyclerView weekRecyclerView, monthRecyclerView, pastRecyclerView;
    public static final String TAG = "AlerBairro";

    public AlertFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alert, container, false);

        weekRecyclerView = view.findViewById(R.id.weekAlertRecycler);
//        monthRecyclerView = view.findViewById(R.id.monthAlertRecycler);
//        pastRecyclerView = view.findViewById(R.id.previousAlertRecycler);

        weekRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

//        monthRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//
//        pastRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        Connection connection = new Connection();
        connection.getAlertBairro(weekRecyclerView, view.getContext());
//        connection.getAlerBairroMonth(monthRecyclerView, view.getContext());
//        connection.getAlerBairroPast(pastRecyclerView, view.getContext());

        return view;
    }

//    public void createListData(String date, String desc) {
//        // This method adds data to the recyclerView
//        AlerBairro alert = new AlerBairro(date, desc);
//        alerts.add(alert);
//    }
}