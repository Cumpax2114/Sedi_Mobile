package dev.franklinbg.sedimobile.api

import dev.franklinbg.sedimobile.model.Cliente
import dev.franklinbg.sedimobile.utils.GenericResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ClienteApi {
    companion object {
        const val base = "rest/cliente"
    }

    @POST(base)
    fun save(@Body cliente: Cliente): Call<GenericResponse<Cliente>>
}