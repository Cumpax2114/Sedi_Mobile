package dev.franklinbg.sedimobile.repsoitory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.franklinbg.sedimobile.api.ConfigApi
import dev.franklinbg.sedimobile.model.Proveedor
import dev.franklinbg.sedimobile.utils.GenericResponse
import dev.franklinbg.sedimobile.utils.Global.RPTA_ERROR
import dev.franklinbg.sedimobile.utils.Global.TIPO_RESULT
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProveedorRepository {
    val api = ConfigApi.proveedorApi
    fun listActivos(): LiveData<GenericResponse<ArrayList<Proveedor>>> {
        val mld = MutableLiveData<GenericResponse<ArrayList<Proveedor>>>()
        api.listActivos().enqueue(object : Callback<GenericResponse<ArrayList<Proveedor>>> {
            override fun onResponse(
                call: Call<GenericResponse<ArrayList<Proveedor>>>,
                response: Response<GenericResponse<ArrayList<Proveedor>>>
            ) {
                mld.value = response.body()
            }

            override fun onFailure(
                call: Call<GenericResponse<ArrayList<Proveedor>>>,
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