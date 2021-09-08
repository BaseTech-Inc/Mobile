package com.example.tupa_mobile.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tupa_mobile.Alerts.Alert;
import com.example.tupa_mobile.Alerts.AlertAdapter;
import com.example.tupa_mobile.R;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    private RecyclerView cardRecyclerView;
    private AlertAdapter adapter;
    private ArrayList<Alert> alerts;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        cardRecyclerView = view.findViewById(R.id.notificationRecycler);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        alerts = new ArrayList<>();
        adapter = new AlertAdapter(view.getContext(), alerts);
        cardRecyclerView.setAdapter(adapter);

       // for(int i=0; i<7; i++) {
            createListData("Alerta de Alagamento", "Avenida Bla Bli Blop 333");//puxar do banco de dados
       // }

        return view;
    }

    public void createListData(String date, String desc) {
        // This method adds data to the recyclerView
        Alert alert = new Alert(date, desc);
        alerts.add(alert);
    }
}