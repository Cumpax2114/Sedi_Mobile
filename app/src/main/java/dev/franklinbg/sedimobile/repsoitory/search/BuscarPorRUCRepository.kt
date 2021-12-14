package dev.franklinbg.sedimobile.repsoitory.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.franklinbg.sedimobile.api.search.SearchConfigApi
import dev.franklinbg.sedimobile.utils.ApiResponse
import dev.franklinbg.sedimobile.utils.SunatEmpresa
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuscarPorRUCRepository {
    private val api = SearchConfigApi.buscarPorRUCApi
    fun find(ruc: String): LiveData<ApiResponse<SunatEmpresa>> {
        val mld = MutableLiveData<ApiResponse<SunatEmpresa>>()
        api.find(ruc).enqueue(object : Callback<ApiResponse<SunatEmpresa>> {
            override fun onResponse(
                call: Call<ApiResponse<SunatEmpresa>>,
                response: Response<ApiResponse<SunatEmpresa>>
            ) {
                mld.value = response.body()
            }

            override fun onFailure(call: Call<ApiResponse<SunatEmpresa>>, t: Throwable) {
                mld.value =
                    ApiResponse(message = "ocurrió un problema al intentar realizar esta solicitud:${t.message}")
            }
        })
        return mld
    }
}