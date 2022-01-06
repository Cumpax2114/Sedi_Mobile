package dev.franklinbg.sedimobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.franklinbg.sedimobile.model.Cliente
import dev.franklinbg.sedimobile.repsoitory.ClienteRepository
import dev.franklinbg.sedimobile.utils.GenericResponse

class ClienteViewModel : ViewModel() {
    val repository = ClienteRepository()
    fun save(cliente: Cliente): LiveData<GenericResponse<Cliente>> = repository.save(cliente)
    fun listAll(): LiveData<GenericResponse<ArrayList<Cliente>>> = repository.listAll()
}