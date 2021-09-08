package com.example.tupa_mobile.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tupa_mobile.R;

import java.util.ArrayList;

import com.example.tupa_mobile.Rides.Rides;
import com.example.tupa_mobile.Rides.RidesAdapter;


public class RidesFragment extends Fragment {

    private RecyclerView weekRecyclerView, monthRecyclerView, pastRecyclerView;
    private RidesAdapter adapter;
    private ArrayList<Rides> ride;

    public RidesFragment() {
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
        View view = inflater.inflate(R.layout.fragment_rides, container, false);

        weekRecyclerView = view.findViewById(R.id.weekAlertRecycler);
        monthRecyclerView = view.findViewById(R.id.monthAlertRecycler);
        pastRecyclerView = view.findViewById(R.id.previousAlertRecycler);

        ride = new ArrayList<>();

        for(int i=0; i<3; i++) {
            createListData("22Km","09:55-11:05 (1h 9min)",R.drawable.ic_map );
        }

        adapter = new RidesAdapter(view.getContext(), ride);

        weekRecyclerView.setAdapter(adapter);
        weekRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        monthRecyclerView.setAdapter(adapter);
        monthRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        pastRecyclerView.setAdapter(adapter);
        pastRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    public void createListData(String distance, String time, int map) {
        // This method adds data to the recyclerView
        Rides rides = new Rides(distance, time, map);
        ride.add(rides);
    }
}