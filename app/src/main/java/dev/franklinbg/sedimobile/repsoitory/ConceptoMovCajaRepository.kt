package dev.franklinbg.sedimobile.repsoitory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.franklinbg.sedimobile.api.ConfigApi
import dev.franklinbg.sedimobile.model.ConceptoMovCaja
import dev.franklinbg.sedimobile.utils.GenericResponse
import dev.franklinbg.sedimobile.utils.Global
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConceptoMovCajaRepository {
    val api = ConfigApi.conceptoMovCajaApi
    fun listActivos(): LiveData<GenericResponse<ArrayList<ConceptoMovCaja>>> {
        val mld = MutableLiveData<GenericResponse<ArrayList<ConceptoMovCaja>>>()
        api.listActivos().enqueue(object : Callback<GenericResponse<ArrayList<ConceptoMovCaja>>> {
            override fun onResponse(
                call: Call<GenericResponse<ArrayList<ConceptoMovCaja>>>,
                response: Response<GenericResponse<ArrayList<ConceptoMovCaja>>>
            ) {
                mld.value = response.body()
            }

            override fun onFailure(
                call: Call<GenericResponse<ArrayList<ConceptoMovCaja>>>,
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