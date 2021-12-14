package dev.franklinbg.sedimobile.api.search
import dev.franklinbg.sedimobile.utils.SunatEmpresa
import dev.franklinbg.sedimobile.utils.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BuscarPorRUCApi {
    companion object {
        const val base = "api/ruc"
    }

    @GET("$base/{ruc}")
    fun find(@Path("ruc") ruc: String): Call<ApiResponse<SunatEmpresa>>
}