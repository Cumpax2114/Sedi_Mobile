package dev.franklinbg.sedimobile.api

import dev.franklinbg.sedimobile.model.TipoContrato
import dev.franklinbg.sedimobile.utils.GenericResponse
import retrofit2.Call
import retrofit2.http.GET

interface ControApi {
    companion object {
        const val base = "rest/contrato"
    }

    @GET("${base}/tipo")
    fun listActivos(): Call<GenericResponse<ArrayList<TipoContrato>>>
}