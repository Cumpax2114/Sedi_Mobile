package dev.franklinbg.sedimobile.api

import dev.franklinbg.sedimobile.model.Apertura
import dev.franklinbg.sedimobile.model.Caja
import dev.franklinbg.sedimobile.model.DetalleCaja
import dev.franklinbg.sedimobile.model.MovCaja
import dev.franklinbg.sedimobile.model.dto.CajaWithDetallesDTO
import dev.franklinbg.sedimobile.utils.GenericResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface CajaApi {
    companion object {
        const val base = "rest/caja"
    }

    @GET("${base}/byusuarioid/{idU}")
    fun getByUserId(@Path("idU") idu: Int): Call<GenericResponse<Caja>>

    @POST("${base}/open")
    fun open(@Body dto: CajaWithDetallesDTO): Call<GenericResponse<CajaWithDetallesDTO>>

    @POST("${base}/movimiento")
    fun saveMovimiento(@Body movCaja: MovCaja): Call<GenericResponse<MovCaja>>

    @GET("${base}/aperturas/{idCaja}")
    fun getAperturas(@Path("idCaja") idCaja: Int): Call<GenericResponse<ArrayList<Apertura>>>

    @GET("${base}/report")
    fun export(
        @Query("idCaja") idCaja: Int,
        @Query("fechaApertura") fechaApertura: String
    ): Call<ResponseBody>

    @GET("${base}/detallesActuales/{idCaja}")
    fun getCurrentDetails(@Path("idCaja") idCaja: Int): Call<GenericResponse<ArrayList<DetalleCaja>>>

    @PUT("${base}/close/{idCaja}")
    fun close(@Path("idCaja") idCaja: Int): Call<GenericResponse<ArrayList<DetalleCaja>>>

    @GET("${base}/movimientos/{idCaja}")
    fun getMovimientosByCajaId(@Path("idCaja") idCaja: Int): Call<GenericResponse<ArrayList<MovCaja>>>
}