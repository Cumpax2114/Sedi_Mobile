package dev.franklinbg.sedimobile.repsoitory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.franklinbg.sedimobile.api.ConfigApi
import dev.franklinbg.sedimobile.model.TipoContrato
import dev.franklinbg.sedimobile.utils.GenericResponse
import dev.franklinbg.sedimobile.utils.Global
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TipoContratoRepository {
    val api = ConfigApi.tipoControApi
    fun listTiposActivos(): LiveData<GenericResponse<ArrayList<TipoContrato>>> {
        val mld = MutableLiveData<GenericResponse<ArrayList<TipoContrato>>>()
        api.listActivos().enqueue(object : Callback<GenericResponse<ArrayList<TipoContrato>>> {
            override fun onResponse(
                call: Call<GenericResponse<ArrayList<TipoContrato>>>,
                response: Response<GenericResponse<ArrayList<TipoContrato>>>
            ) {
                mld.value = response.body()
            }

            override fun onFailure(
                call: Call<GenericResponse<ArrayList<TipoContrato>>>,
                t: Throwable
            ) {
                mld.value = GenericResponse(
                    Global.TIPO_RESULT,
                    Global.RPTA_ERROR,
                    "internal exception, no se pudieron listar los tipos de contrato:${t.message!!}",
                )
            }

        })
        return mld;
    }
}