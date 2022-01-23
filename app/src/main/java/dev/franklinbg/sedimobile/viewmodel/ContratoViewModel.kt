package dev.franklinbg.sedimobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.franklinbg.sedimobile.model.TipoContrato
import dev.franklinbg.sedimobile.repsoitory.ContratoRepository
import dev.franklinbg.sedimobile.utils.GenericResponse

class ContratoViewModel : ViewModel() {
    val repository = ContratoRepository()
    fun listTiposActivos(): LiveData<GenericResponse<ArrayList<TipoContrato>>> =
        repository.listTiposActivos()
}