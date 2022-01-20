package dev.franklinbg.sedimobile.model;

public class MovCaja {
    private int id;
    private Apertura apertura;
    private Caja caja;
    private Usuario usuario;
    private char tipoMov;
    private ConceptoMovCaja conceptoMovCaja;
    private MetodoPago metodoPago;
    private Usuario trabajador;
    private Cliente cliente;
    private Proveedor proveedor;
    private double total;
    private String descripcion;
    private char estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Apertura getApertura() {
        return apertura;
    }

    public void setApertura(Apertura apertura) {
        this.apertura = apertura;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public char getTipoMov() {
        return tipoMov;
    }

    public void setTipoMov(char tipoMov) {
        this.tipoMov = tipoMov;
    }

    public ConceptoMovCaja getConceptoMovCaja() {
        return conceptoMovCaja;
    }

    public void setConceptoMovCaja(ConceptoMovCaja conceptoMovCaja) {
        this.conceptoMovCaja = conceptoMovCaja;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Usuario getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Usuario trabajador) {
        this.trabajador = trabajador;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }
}
