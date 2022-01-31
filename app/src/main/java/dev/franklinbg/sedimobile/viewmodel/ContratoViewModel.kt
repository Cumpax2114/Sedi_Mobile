package dev.franklinbg.sedimobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.franklinbg.sedimobile.model.Contrato
import dev.franklinbg.sedimobile.model.PagoContrato
import dev.franklinbg.sedimobile.repsoitory.ContratoRepository
import dev.franklinbg.sedimobile.utils.GenericResponse

class ContratoViewModel : ViewModel() {
    val repository = ContratoRepository()
    fun listAll(): LiveData<GenericResponse<ArrayList<Contrato>>> = repository.listAll()
    fun save(contrato: Contrato): LiveData<GenericResponse<Contrato>> = repository.save(contrato)
    fun pagar(pagos: ArrayList<PagoContrato>): LiveData<GenericResponse<ArrayList<PagoContrato>>> =
        repository.pagar(pagos)
}