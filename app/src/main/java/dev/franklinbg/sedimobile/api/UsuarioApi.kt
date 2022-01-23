package dev.franklinbg.sedimobile.api

import dev.franklinbg.sedimobile.model.Usuario
import dev.franklinbg.sedimobile.utils.GenericResponse
import retrofit2.Call
import retrofit2.http.*

interface UsuarioApi {
    companion object {
        const val baseUrl = "rest/usuario"
    }

    @FormUrlEncoded
    @POST("${baseUrl}/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<GenericResponse<Usuario>>

    @GET(baseUrl)
    fun listAll(): Call<GenericResponse<ArrayList<Usuario>>>

    @GET("${baseUrl}/fmc/{id}")
    fun listForMovCaja(@Path("id") id: Int): Call<GenericResponse<ArrayList<Usuario>>>
}