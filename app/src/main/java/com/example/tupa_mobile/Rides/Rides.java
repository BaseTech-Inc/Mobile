package com.example.tupa_mobile.Rides;

import com.example.tupa_mobile.Districts.Districts;
import com.example.tupa_mobile.User.User;
import com.google.gson.annotations.SerializedName;
public class Rides {
//    private String distance, time, events, floods, imageSource;
//private int map;
    private User user;
    private String Id;
    private String tempoChegada;
    private String tempoPartida;
    private String distanciaPercurso;
    private String rota;
    private Districts distro;

    public Rides(String tempoChegada, String tempoPartida, String distanciaPercurso) {
    }

//    public Rides(String distance, String time, int map) {
//        this.distance = distance;
//        this.time = time;
//        this.map = map;
//    }


    public String getId() {
        return Id;
    }
    public User getUser() {
        return user;
    }

    public String getTempoChegada() {
        return tempoChegada;
    }

    public String getTempoPartida() {
        return tempoPartida;
    }

    public String getDistanciaPercurso() {
        return distanciaPercurso;
    }
    public Districts getDistrito() {
        return distro;
    }
    public String getRota() {
        return rota;
    }
}


