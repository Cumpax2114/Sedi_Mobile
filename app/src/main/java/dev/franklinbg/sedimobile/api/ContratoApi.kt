package dev.franklinbg.sedimobile.api

import dev.franklinbg.sedimobile.model.Contrato
import dev.franklinbg.sedimobile.utils.GenericResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ContratoApi {
    companion object {
        const val baseUrl = "rest/contrato"
    }

    @GET(baseUrl)
    fun listAll(): Call<GenericResponse<ArrayList<Contrato>>>

    @POST(baseUrl)
    fun save(@Body contrato: Contrato): Call<GenericResponse<Contrato>>
}