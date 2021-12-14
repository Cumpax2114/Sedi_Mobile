package dev.franklinbg.sedimobile.repsoitory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.franklinbg.sedimobile.api.ConfigApi
import dev.franklinbg.sedimobile.model.Cliente
import dev.franklinbg.sedimobile.utils.GenericResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import dev.franklinbg.sedimobile.utils.Global.*

class ClienteRepository {
    val api = ConfigApi.clienteApi
    fun save(cliente: Cliente): LiveData<GenericResponse<Cliente>> {
        val mld = MutableLiveData<GenericResponse<Cliente>>()
        api.save(cliente).enqueue(object : Callback<GenericResponse<Cliente>> {
            override fun onResponse(
                call: Call<GenericResponse<Cliente>>,
                response: Response<GenericResponse<Cliente>>
            ) {
                mld.value = response.body()
            }

            override fun onFailure(call: Call<GenericResponse<Cliente>>, t: Throwable) {
                mld.value =
                    GenericResponse(
                        TIPO_RESULT,
                        RPTA_ERROR,
                        "internal exception:${t.message!!}",
                        null
                    )
            }
        }
        )
        return mld;
    }
}