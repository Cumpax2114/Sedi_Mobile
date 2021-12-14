package dev.franklinbg.sedimobile.model;

public class Cliente {
    private int id;
    private String documento;
    private String nombre;
    private String direccion;
    private String ubigeo;
    private String telefono;
    private String correo;
    private char estado;
    private double monto_compra;

    public Cliente() {
        this.estado = 'A';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public double getMonto_compra() {
        return monto_compra;
    }

    public void setMonto_compra(double monto_compra) {
        this.monto_compra = monto_compra;
    }
}
