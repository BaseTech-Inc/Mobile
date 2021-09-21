package com.example.tupa_mobile.Alerts;

import com.example.tupa_mobile.Districts.Districts;
import com.example.tupa_mobile.Markers.MarkerPoint;
import com.example.tupa_mobile.User.User;

public class AlertData {

    private String id, descricao, tempoFinal, tempoInicio;
    private boolean transitividade, atividade;
    private MarkerPoint ponto;
    private Districts distrito;

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTempoFinal() {
        return tempoFinal;
    }

    public String getTempoInicio() {
        return tempoInicio;
    }

    public boolean isTransitividade() {
        return transitividade;
    }

    public boolean isAtividade() {
        return atividade;
    }

    public MarkerPoint getPonto() {
        return ponto;
    }

    public Districts getDistrito() {
        return distrito;
    }
}
