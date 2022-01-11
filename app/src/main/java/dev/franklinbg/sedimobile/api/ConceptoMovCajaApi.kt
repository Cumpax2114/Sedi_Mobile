package dev.franklinbg.sedimobile.api

import dev.franklinbg.sedimobile.model.ConceptoMovCaja
import dev.franklinbg.sedimobile.utils.GenericResponse
import retrofit2.Call
import retrofit2.http.GET

interface ConceptoMovCajaApi {
    companion object {
        private const val baseUrl = "rest/conceptoMcaja"
    }

    @GET("${baseUrl}/activos")
    fun listActivos(): Call<GenericResponse<ArrayList<ConceptoMovCaja>>>
}