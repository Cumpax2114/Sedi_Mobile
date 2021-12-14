package dev.franklinbg.sedimobile.viewmodel.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.franklinbg.sedimobile.repsoitory.search.BuscarPorRUCRepository
import dev.franklinbg.sedimobile.utils.ApiResponse
import dev.franklinbg.sedimobile.utils.SunatEmpresa

class BuscarPorRUCViewModel : ViewModel() {
    val repository = BuscarPorRUCRepository()
    fun find(ruc: String): LiveData<ApiResponse<SunatEmpresa>> = repository.find(ruc)
}