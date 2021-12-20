package dev.franklinbg.sedimobile.repsoitory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.franklinbg.sedimobile.api.ConfigApi
import dev.franklinbg.sedimobile.model.MetodoPago
import dev.franklinbg.sedimobile.utils.GenericResponse
import dev.franklinbg.sedimobile.utils.Global
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MetodoPagoRepository {
    val api = ConfigApi.metodoPagoApi
    fun getAll(): LiveData<GenericResponse<ArrayList<MetodoPago>>> {
        val mld = MutableLiveData<GenericResponse<ArrayList<MetodoPago>>>()
        api.getAll().enqueue(object : Callback<GenericResponse<ArrayList<MetodoPago>>> {
            override fun onResponse(
                call: Call<GenericResponse<ArrayList<MetodoPago>>>,
                response: Response<GenericResponse<ArrayList<MetodoPago>>>
            ) {
                mld.value = response.body()
            }

            override fun onFailure(
                call: Call<GenericResponse<ArrayList<MetodoPago>>>,
                t: Throwable
            ) {
                mld.value = GenericResponse(
                    Global.TIPO_RESULT,
                    Global.RPTA_ERROR,
                    "internal exception:${t.message!!}",
                )
            }

        })
        return mld;
    }
}