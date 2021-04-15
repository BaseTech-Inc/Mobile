package com.example.tupa_mobile.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tupa_mobile.Connections.Connection;
import com.example.tupa_mobile.R;

public class HistoryFragment extends Fragment {

    private TextView txtResult;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        txtResult = view.findViewById(R.id.txtCurrentWeather);

        Connection con = new Connection();
        con.requestCurrentWeather(txtResult, view.getContext());

        return view;
    }
}