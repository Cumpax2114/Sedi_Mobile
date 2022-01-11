package dev.franklinbg.sedimobile.model;

import androidx.annotation.NonNull;

public class ConceptoMovCaja {
    private int id;
    private String nombre;
    private char estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    @NonNull
    @Override
    public String toString() {
        return nombre;
    }
}
