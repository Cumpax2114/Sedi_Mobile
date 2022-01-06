package dev.franklinbg.sedimobile.api

import dev.franklinbg.sedimobile.model.Caja
import dev.franklinbg.sedimobile.model.MovCaja
import dev.franklinbg.sedimobile.model.dto.CajaWithDetallesDTO
import dev.franklinbg.sedimobile.utils.GenericResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CajaApi {
    companion object {
        const val base = "rest/caja"
    }

    @GET("${base}/byusuarioid/{idU}")
    fun getByUserId(@Path("idU") idu: Int): Call<GenericResponse<Caja>>

    @POST("${base}/open")
    fun open(@Body dto: CajaWithDetallesDTO): Call<GenericResponse<CajaWithDetallesDTO>>
    @POST("${base}/movimiento")
    fun saveMovimiento(@Body movCaja:MovCaja):Call<GenericResponse<MovCaja>>
}