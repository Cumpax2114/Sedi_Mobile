package dev.franklinbg.sedimobile.repsoitory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.franklinbg.sedimobile.api.ConfigApi
import dev.franklinbg.sedimobile.model.Contrato
import dev.franklinbg.sedimobile.utils.GenericResponse
import dev.franklinbg.sedimobile.utils.Global.RPTA_ERROR
import dev.franklinbg.sedimobile.utils.Global.TIPO_RESULT
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContratoRepository {
    val api = ConfigApi.contratoApi
    fun save(contrato: Contrato): LiveData<GenericResponse<Contrato>> {
        val mld = MutableLiveData<GenericResponse<Contrato>>()
        api.save(contrato).enqueue(object : Callback<GenericResponse<Contrato>> {
            override fun onResponse(
                call: Call<GenericResponse<Contrato>>,
                response: Response<GenericResponse<Contrato>>
            ) {
                mld.value = response.body()
            }

            override fun onFailure(call: Call<GenericResponse<Contrato>>, t: Throwable) {
                mld.value = GenericResponse(
                    TIPO_RESULT,
                    RPTA_ERROR,
                    "internal exception:${t.message!!}",
                )
            }

        })
        return mld;
    }
}