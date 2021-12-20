package dev.franklinbg.sedimobile.model.dto;

import java.util.ArrayList;

import dev.franklinbg.sedimobile.model.DetalleCaja;

public class CajaWithDetallesDTO {
    private int idCaja;
    private ArrayList<DetalleCaja> detalles;

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }

    public ArrayList<DetalleCaja> getDetalles() {
        return detalles;
    }

    public void setDetalles(ArrayList<DetalleCaja> detalles) {
        this.detalles = detalles;
    }
}
