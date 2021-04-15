package com.example.tupa_mobile.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tupa_mobile.ForecastPage.ForecastDay;
import com.example.tupa_mobile.ForecastPage.ForecastDayAdapter;
import com.example.tupa_mobile.NotificationPage.Notification;
import com.example.tupa_mobile.NotificationPage.NotificationAdapter;
import com.example.tupa_mobile.R;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    private RecyclerView cardRecyclerView;
    private NotificationAdapter adapter;
    private ArrayList<Notification> notifications;

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
        notifications = new ArrayList<>();
        adapter = new NotificationAdapter(view.getContext(), notifications);
        cardRecyclerView.setAdapter(adapter);

        for(int i=0; i<7; i++) {
            createListData("Sábado, na balada", "Ela começou a dançaaaaaar");
        }

        return view;
    }

    public void createListData(String date, String desc) {
        // This method adds data to the recyclerView
        Notification notification = new Notification(date, desc);
        notifications.add(notification);
    }
}