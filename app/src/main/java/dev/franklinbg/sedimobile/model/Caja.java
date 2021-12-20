package dev.franklinbg.sedimobile.model;

import java.util.Date;

public class Caja {
    private int id;
    private Date fechaApertura;
    private Date fechaCierre;
    private double montoApertura;
    private double montoCierre;
    private char estado;
    private Usuario usuario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public double getMontoApertura() {
        return montoApertura;
    }

    public void setMontoApertura(double montoApertura) {
        this.montoApertura = montoApertura;
    }

    public double getMontoCierre() {
        return montoCierre;
    }

    public void setMontoCierre(double montoCierre) {
        this.montoCierre = montoCierre;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
