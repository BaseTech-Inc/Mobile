package com.example.tupa_mobile.Alerts;

import com.example.tupa_mobile.Districts.Districts;
import com.example.tupa_mobile.Markers.MarkerPoint;

public class AlerBairro {

//    private String date, title, description, address, imageSource;
//
//    public AlerBairro(String title, String address) {
//        this.title = title;
//        this.address = address;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public String getImageSource() {
//        return imageSource;
//    }
private String id;
private MarkerPoint ponto;
private Districts distrito;
private String tempoInicio;
private String tempoFinal;
private String descricao;
private boolean transitividade;
private boolean atividade;

    public AlerBairro(String descricao, Districts districts) {
    }

    public String getId() {
        return id;
    }

    public MarkerPoint getMarkerPoint() {
        return ponto;
    }

    public Districts getDistricts() {
        return distrito;
    }

    public String getTempoInicio() {
        return tempoInicio;
    }

    public String getTempoFinal() {
        return tempoFinal;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isTransitividade() {
        return transitividade;
    }

    public boolean isAtividade() {
        return atividade;
    }
}
