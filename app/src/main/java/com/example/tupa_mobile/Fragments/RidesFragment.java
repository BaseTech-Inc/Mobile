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


public class RidesFragment extends Fragment {

    private RecyclerView weekRecyclerView, monthRecyclerView, pastRecyclerView;
    public static final String TAG = "Rides";

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



      weekRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

      monthRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

      pastRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        Connection connection = new Connection();
        connection.getRides(weekRecyclerView, view.getContext());






//        ride = new ArrayList<>();
//
//        for(int i=0; i<3; i++) {
//            createListData("tempoChegada:" +ridees.getTempoChegada(),"tempoPartida:" +ridees.getTempoPartida(),R.drawable.ic_map );
//        }
//
//        adapter = new RidesAdapter(view.getContext(), ride);
//
//        weekRecyclerView.setAdapter(adapter);
//        weekRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//        monthRecyclerView.setAdapter(adapter);
//        monthRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//        pastRecyclerView.setAdapter(adapter);
//        pastRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }


}

//
// call.enqueue(new Callback<List<Rides>>() {
//@Override
//public void onResponse(Call<List<Rides>> call, Response<List<Rides>> response) {
//        if (!response.isSuccessful()){
//        return;
//        }
//        List<Rides> rides =response.body();
//        ride = new ArrayList<>();
//
//        for(int i=0; i<3; i++) {
//        createListData("tempoChegada:" +ridees.getTempoChegada(),"tempoPartida:" +ridees.getTempoPartida(), ridees.getDistanciaPercurso() );
//        }
//        }
//
//@Override
//public void onFailure(Call<List<Rides>> call, Throwable t) {
//
//        }
//        });