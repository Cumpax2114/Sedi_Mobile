package dev.franklinbg.sedimobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.franklinbg.sedimobile.model.MetodoPago
import dev.franklinbg.sedimobile.repsoitory.MetodoPagoRepository
import dev.franklinbg.sedimobile.utils.GenericResponse

class MetodoPagoViewModel : ViewModel() {
    val repository = MetodoPagoRepository()
    fun getAll(): LiveData<GenericResponse<ArrayList<MetodoPago>>> = repository.getAll()
}