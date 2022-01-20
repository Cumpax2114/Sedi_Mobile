package dev.franklinbg.sedimobile.model;

import androidx.annotation.NonNull;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Apertura {
    private int id;
    private Caja caja;
    private Date fechaApertura;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    @NonNull
    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        return simpleDateFormat.format(fechaApertura);
    }
}
