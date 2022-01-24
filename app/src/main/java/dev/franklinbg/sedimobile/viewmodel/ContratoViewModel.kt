package dev.franklinbg.sedimobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.franklinbg.sedimobile.model.Contrato
import dev.franklinbg.sedimobile.repsoitory.ContratoRepository
import dev.franklinbg.sedimobile.utils.GenericResponse

class ContratoViewModel : ViewModel() {
    val repository = ContratoRepository()
    fun save(contrato: Contrato): LiveData<GenericResponse<Contrato>> = repository.save(contrato)
}