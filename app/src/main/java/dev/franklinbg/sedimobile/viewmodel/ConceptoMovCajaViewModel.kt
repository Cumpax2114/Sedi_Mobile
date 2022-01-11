package dev.franklinbg.sedimobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.franklinbg.sedimobile.model.ConceptoMovCaja
import dev.franklinbg.sedimobile.repsoitory.ConceptoMovCajaRepository
import dev.franklinbg.sedimobile.utils.GenericResponse

class ConceptoMovCajaViewModel : ViewModel() {
    val repository = ConceptoMovCajaRepository()
    fun listActivos(): LiveData<GenericResponse<ArrayList<ConceptoMovCaja>>> =
        repository.listActivos()
}