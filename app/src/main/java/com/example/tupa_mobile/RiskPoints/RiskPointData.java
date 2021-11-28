package com.example.tupa_mobile.RiskPoints;

import com.example.tupa_mobile.Districts.Districts;
import com.example.tupa_mobile.Markers.MarkerPoint;

public class RiskPointData {

    private String id, descricao;
    private MarkerPoint ponto;
    private Districts distrito;

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public MarkerPoint getPonto() {
        return ponto;
    }

    public Districts getDistrito() {
        return distrito;
    }
}
