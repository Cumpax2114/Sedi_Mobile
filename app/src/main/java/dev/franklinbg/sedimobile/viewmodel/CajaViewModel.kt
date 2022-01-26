package dev.franklinbg.sedimobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.franklinbg.sedimobile.model.Apertura
import dev.franklinbg.sedimobile.model.Caja
import dev.franklinbg.sedimobile.model.DetalleCaja
import dev.franklinbg.sedimobile.model.MovCaja
import dev.franklinbg.sedimobile.model.dto.CajaWithDetallesDTO
import dev.franklinbg.sedimobile.repsoitory.CajaRepository
import dev.franklinbg.sedimobile.utils.GenericResponse
import okhttp3.ResponseBody

class CajaViewModel : ViewModel() {
    val repository = CajaRepository()
    fun getByUserId(idU: Int): LiveData<GenericResponse<Caja>> = repository.getByUserId(idU)
    fun open(dto: CajaWithDetallesDTO): LiveData<GenericResponse<CajaWithDetallesDTO>> =
        repository.open(dto)

    fun close(idCaja: Int): LiveData<GenericResponse<ArrayList<DetalleCaja>>> =
        repository.close(idCaja)

    fun saveMovimiento(movCaja: MovCaja): LiveData<GenericResponse<MovCaja>> =
        repository.saveMovimiento(movCaja)

    fun getAperturas(idCaja: Int): LiveData<GenericResponse<ArrayList<Apertura>>> =
        repository.getAperturas(idCaja)

    fun export(idCaja: Int, fechaApertura: String): LiveData<GenericResponse<ResponseBody>> =
        repository.export(idCaja, fechaApertura)

    fun getCurrentDetails(idCaja: Int): LiveData<GenericResponse<ArrayList<DetalleCaja>>> =
        repository.getCurrentDetails(idCaja)

    fun getMovimientosByCajaId(idCaja: Int): LiveData<GenericResponse<ArrayList<MovCaja>>> =
        repository.getMovimientosByCajaId(idCaja)

    fun anularMovimiento(id: Int): LiveData<GenericResponse<MovCaja>> =
        repository.anularMovimiento(id)
}