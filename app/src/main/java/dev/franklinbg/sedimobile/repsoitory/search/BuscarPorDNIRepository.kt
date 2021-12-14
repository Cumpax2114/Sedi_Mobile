package dev.franklinbg.sedimobile.repsoitory.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.franklinbg.sedimobile.api.search.SearchConfigApi
import dev.franklinbg.sedimobile.utils.ReniecPerson
import dev.franklinbg.sedimobile.utils.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class BuscarPorDNIRepository {
    private val api = SearchConfigApi.buscarDniApi

    @Throws(Exception::class)
    fun find(dni: String): LiveData<ApiResponse<ReniecPerson>> {
        val mld = MutableLiveData<ApiResponse<ReniecPerson>>()
        api.find(dni).enqueue(object : Callback<ApiResponse<ReniecPerson>> {
            override fun onResponse(
                call: Call<ApiResponse<ReniecPerson>>,
                response: Response<ApiResponse<ReniecPerson>>
            ) {
                mld.value = response.body()
            }

            override fun onFailure(call: Call<ApiResponse<ReniecPerson>>, t: Throwable) {
                mld.value =
                    ApiResponse(message = "ocurrió un problema al intentar realizar esta solicitud:${t.message}")
            }

        })
        return mld
    }
}