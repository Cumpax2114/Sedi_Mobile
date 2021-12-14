package dev.franklinbg.sedimobile.viewmodel.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.franklinbg.sedimobile.repsoitory.search.BuscarPorDNIRepository
import dev.franklinbg.sedimobile.utils.ReniecPerson
import dev.franklinbg.sedimobile.utils.ApiResponse

class BuscarPorDNIViewModel : ViewModel() {
    private val repository = BuscarPorDNIRepository()
    fun find(dni: String): LiveData<ApiResponse<ReniecPerson>> = repository.find(dni)
}