package dev.franklinbg.sedimobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.franklinbg.sedimobile.model.Usuario
import dev.franklinbg.sedimobile.repsoitory.UsuarioRepository
import dev.franklinbg.sedimobile.utils.GenericResponse

class UsuarioViewModel : ViewModel() {
    private val repository = UsuarioRepository()
    fun login(email: String, password: String): LiveData<GenericResponse<Usuario>> =
        repository.login(email, password)
}