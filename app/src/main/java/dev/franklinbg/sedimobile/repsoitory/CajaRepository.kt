package dev.franklinbg.sedimobile.repsoitory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.franklinbg.sedimobile.api.ConfigApi
import dev.franklinbg.sedimobile.model.Caja
import dev.franklinbg.sedimobile.model.MovCaja
import dev.franklinbg.sedimobile.model.dto.CajaWithDetallesDTO
import dev.franklinbg.sedimobile.utils.GenericResponse
import dev.franklinbg.sedimobile.utils.Global
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CajaRepository {
    val api = ConfigApi.cajaApi
    fun getByUserId(idU: Int): LiveData<GenericResponse<Caja>> {
        val mld = MutableLiveData<GenericResponse<Caja>>()
        api.getByUserId(idU).enqueue(object : Callback<GenericResponse<Caja>> {
            override fun onResponse(
                call: Call<GenericResponse<Caja>>,
                response: Response<GenericResponse<Caja>>
            ) {
                mld.value = response.body()
            }

            override fun onFailure(call: Call<GenericResponse<Caja>>, t: Throwable) {
                mld.value =
                    GenericResponse(
                        Global.TIPO_RESULT,
                        Global.RPTA_ERROR,
                        "internal exception:${t.message!!}",
                    )
            }

        })
        return mld
    }

    fun open(dto: CajaWithDetallesDTO): LiveData<GenericResponse<CajaWithDetallesDTO>> {
        val mld = MutableLiveData<GenericResponse<CajaWithDetallesDTO>>()
        api.open(dto).enqueue(object : Callback<GenericResponse<CajaWithDetallesDTO>> {
            override fun onResponse(
                call: Call<GenericResponse<CajaWithDetallesDTO>>,
                response: Response<GenericResponse<CajaWithDetallesDTO>>
            ) {
                mld.value = response.body()
            }

            override fun onFailure(call: Call<GenericResponse<CajaWithDetallesDTO>>, t: Throwable) {
                mld.value =
                    GenericResponse(
                        Global.TIPO_RESULT,
                        Global.RPTA_ERROR,
                        "internal exception:${t.message!!}",
                    )
            }

        })
        return mld
    }

    fun saveMovimiento(movCaja: MovCaja): LiveData<GenericResponse<MovCaja>> {
        val mld = MutableLiveData<GenericResponse<MovCaja>>()
        api.saveMovimiento(movCaja).enqueue(object : Callback<GenericResponse<MovCaja>> {
            override fun onResponse(
                call: Call<GenericResponse<MovCaja>>,
                response: Response<GenericResponse<MovCaja>>
            ) {
                mld.value = response.body()
            }

            override fun onFailure(call: Call<GenericResponse<MovCaja>>, t: Throwable) {
                mld.value =
                    GenericResponse(
                        Global.TIPO_RESULT,
                        Global.RPTA_ERROR,
                        "internal exception:${t.message!!}",
                    )
            }

        })
        return mld
    }
}