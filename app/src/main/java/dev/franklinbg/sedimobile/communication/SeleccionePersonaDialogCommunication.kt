package dev.franklinbg.sedimobile.communication

import dev.franklinbg.sedimobile.model.Cliente
import dev.franklinbg.sedimobile.model.Proveedor
import dev.franklinbg.sedimobile.model.Usuario

interface SeleccionePersonaDialogCommunication {
    fun addCliente(cliente: Cliente)
    fun addUsuario(usuario: Usuario)
    fun appProveedor(proveedor: Proveedor)
}