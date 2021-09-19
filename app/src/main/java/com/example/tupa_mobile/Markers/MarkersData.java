package com.example.tupa_mobile.Markers;

import com.example.tupa_mobile.User.User;

import retrofit2.http.Field;

public class MarkersData {

    private String id, nome;
    private MarkerPoint ponto;
    private User usuario;
    private boolean clicked = false;

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public MarkerPoint getPonto() {
        return ponto;
    }

    public User getUsuario() {
        return usuario;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }
}
