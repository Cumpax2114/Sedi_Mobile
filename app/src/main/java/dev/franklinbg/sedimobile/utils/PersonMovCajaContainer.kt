package dev.franklinbg.sedimobile.utils

import dev.franklinbg.sedimobile.model.Cliente
import dev.franklinbg.sedimobile.model.Proveedor
import dev.franklinbg.sedimobile.model.Usuario

class PersonMovCajaContainer private constructor() {
    companion object {
        fun clear() {
            type = 0
            cliente = null
            usuario = null
            proveedor = null
        }

        var type = 0
        var cliente: Cliente? = null
        var usuario: Usuario? = null
        var proveedor: Proveedor? = null
    }
}