package com.example.tupa_mobile.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tupa_mobile.Connections.Connection;
import com.example.tupa_mobile.PagerAdapter.PagerAdapter;
import com.example.tupa_mobile.R;
import com.google.android.material.tabs.TabLayout;

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

        View view = inflater.inflate(R.layout.fragment_history,container, false);

        ViewPager viewPager = view.findViewById(R.id.viewPager);

        TabLayout tabs = view.findViewById(R.id.tabLayout);

        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager(), tabs.getTabCount());

        viewPager.setAdapter(pagerAdapter);

        return view;
    }
}