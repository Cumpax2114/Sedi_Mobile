package dev.franklinbg.sedimobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.franklinbg.sedimobile.model.Proveedor
import dev.franklinbg.sedimobile.repsoitory.ProveedorRepository
import dev.franklinbg.sedimobile.utils.GenericResponse

class ProveedorViewModel : ViewModel() {
    val repository = ProveedorRepository()
    fun listActivos(): LiveData<GenericResponse<ArrayList<Proveedor>>> = repository.listActivos()
}