package dev.franklinbg.sedimobile.api

import dev.franklinbg.sedimobile.model.Proveedor
import dev.franklinbg.sedimobile.utils.GenericResponse
import retrofit2.Call
import retrofit2.http.GET

interface ProveedorApi {
    companion object {
        private const val baseUrl = "rest/proveedor"
    }

    @GET("${baseUrl}/activos")
    fun listActivos(): Call<GenericResponse<ArrayList<Proveedor>>>
}