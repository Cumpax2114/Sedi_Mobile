package dev.franklinbg.sedimobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.franklinbg.sedimobile.model.Caja
import dev.franklinbg.sedimobile.model.dto.CajaWithDetallesDTO
import dev.franklinbg.sedimobile.repsoitory.CajaRepository
import dev.franklinbg.sedimobile.utils.GenericResponse

class CajaViewModel : ViewModel() {
    val repository = CajaRepository()
    fun getByUserId(idU: Int): LiveData<GenericResponse<Caja>> = repository.getByUserId(idU)
    fun open(dto: CajaWithDetallesDTO): LiveData<GenericResponse<CajaWithDetallesDTO>> =
        repository.open(dto)
}