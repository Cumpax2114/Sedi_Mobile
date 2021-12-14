package dev.franklinbg.sedimobile.api.search

import dev.franklinbg.sedimobile.utils.ReniecPerson
import dev.franklinbg.sedimobile.utils.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BuscarPorDNIApi {
    companion object {
        const val base = "api/dni"
    }

    @GET("$base/{dni}")
    fun find(@Path("dni") dni: String): Call<ApiResponse<ReniecPerson>>
}