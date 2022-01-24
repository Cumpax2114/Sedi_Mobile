package dev.franklinbg.sedimobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.franklinbg.sedimobile.model.TipoContrato
import dev.franklinbg.sedimobile.repsoitory.TipoContratoRepository
import dev.franklinbg.sedimobile.utils.GenericResponse

class TipoContratoViewModel : ViewModel() {
    val repository = TipoContratoRepository()
    fun listTiposActivos(): LiveData<GenericResponse<ArrayList<TipoContrato>>> =
        repository.listTiposActivos()
}