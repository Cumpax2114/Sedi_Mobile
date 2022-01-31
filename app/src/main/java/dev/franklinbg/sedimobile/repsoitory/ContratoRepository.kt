package dev.franklinbg.sedimobile.repsoitory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.franklinbg.sedimobile.api.ConfigApi
import dev.franklinbg.sedimobile.model.Contrato
import dev.franklinbg.sedimobile.model.PagoContrato
import dev.franklinbg.sedimobile.utils.GenericResponse
import dev.franklinbg.sedimobile.utils.Global.RPTA_ERROR
import dev.franklinbg.sedimobile.utils.Global.TIPO_RESULT
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContratoRepository {
    val api = ConfigApi.contratoApi
    fun listAll(): LiveData<GenericResponse<ArrayList<Contrato>>> {
        val mld = MutableLiveData<GenericResponse<ArrayList<Contrato>>>()
        api.listAll().enqueue(object : Callback<GenericResponse<ArrayList<Contrato>>> {
            override fun onResponse(
                call: Call<GenericResponse<ArrayList<Contrato>>>,
                response: Response<GenericResponse<ArrayList<Contrato>>>
            ) {
                mld.value = response.body()
            }

            override fun onFailure(call: Call<GenericResponse<ArrayList<Contrato>>>, t: Throwable) {
                mld.value = GenericResponse(
                    TIPO_RESULT,
                    RPTA_ERROR,
                    "internal exception:${t.message!!}",
                )
            }

        })
        return mld;
    }

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

    fun pagar(pagos: ArrayList<PagoContrato>): LiveData<GenericResponse<ArrayList<PagoContrato>>> {
        val mld = MutableLiveData<GenericResponse<ArrayList<PagoContrato>>>()
        api.pagar(pagos).enqueue(object : Callback<GenericResponse<ArrayList<PagoContrato>>> {
            override fun onResponse(
                call: Call<GenericResponse<ArrayList<PagoContrato>>>,
                response: Response<GenericResponse<ArrayList<PagoContrato>>>
            ) {
                mld.value = response.body()
            }

            override fun onFailure(
                call: Call<GenericResponse<ArrayList<PagoContrato>>>,
                t: Throwable
            ) {
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