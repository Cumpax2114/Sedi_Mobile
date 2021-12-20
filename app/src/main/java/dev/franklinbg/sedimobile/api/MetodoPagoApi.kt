package dev.franklinbg.sedimobile.api

import dev.franklinbg.sedimobile.model.MetodoPago
import dev.franklinbg.sedimobile.utils.GenericResponse
import retrofit2.Call
import retrofit2.http.GET

interface MetodoPagoApi {
    companion object {
        const val baseUrl = "rest/metodopago"
    }

    @GET(baseUrl)
    fun getAll(): Call<GenericResponse<ArrayList<MetodoPago>>>
}