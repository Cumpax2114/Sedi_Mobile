package dev.franklinbg.sedimobile.utils

import dev.franklinbg.sedimobile.model.Usuario

class UsuarioContainer private constructor() {
    companion object {
        var currentUser: Usuario? = null
    }
}