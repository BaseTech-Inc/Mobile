package com.example.tupa_mobile.Route;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Route {

    @SerializedName("coordinates")
    private ArrayList<ArrayList> coordinates;

    public Route(ArrayList<ArrayList> coordinates) {
        this.coordinates = coordinates;
    }
}
