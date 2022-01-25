package dev.franklinbg.sedimobile.repsoitory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.franklinbg.sedimobile.api.ConfigApi
import dev.franklinbg.sedimobile.model.Apertura
import dev.franklinbg.sedimobile.model.Caja
import dev.franklinbg.sedimobile.model.DetalleCaja
import dev.franklinbg.sedimobile.model.MovCaja
import dev.franklinbg.sedimobile.model.dto.CajaWithDetallesDTO
import dev.franklinbg.sedimobile.utils.GenericResponse
import dev.franklinbg.sedimobile.utils.Global.*
import okhttp3.ResponseBody
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
                        TIPO_RESULT,
                        RPTA_ERROR,
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
                        TIPO_RESULT,
                        RPTA_ERROR,
                        "internal exception, no se pudo abrir la caja:${t.message!!}",
                    )
            }

        })
        return mld
    }

    fun close(idCaja: Int): LiveData<GenericResponse<ArrayList<DetalleCaja>>> {
        val mld = MutableLiveData<GenericResponse<ArrayList<DetalleCaja>>>()
        api.close(idCaja).enqueue(object : Callback<GenericResponse<ArrayList<DetalleCaja>>> {
            override fun onResponse(
                call: Call<GenericResponse<ArrayList<DetalleCaja>>>,
                response: Response<GenericResponse<ArrayList<DetalleCaja>>>
            ) {
                mld.value = response.body()
            }

            override fun onFailure(
                call: Call<GenericResponse<ArrayList<DetalleCaja>>>,
                t: Throwable
            ) {
                mld.value =
                    GenericResponse(
                        TIPO_RESULT,
                        RPTA_ERROR,
                        "internal exception, no se pudo cerrar la caja:${t.message!!}",
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
                        TIPO_RESULT,
                        RPTA_ERROR,
                        "internal exception:${t.message!!}",
                    )
            }

        })
        return mld
    }

    fun getAperturas(idCaja: Int): LiveData<GenericResponse<ArrayList<Apertura>>> {
        val mld = MutableLiveData<GenericResponse<ArrayList<Apertura>>>()
        api.getAperturas(idCaja).enqueue(object : Callback<GenericResponse<ArrayList<Apertura>>> {
            override fun onResponse(
                call: Call<GenericResponse<ArrayList<Apertura>>>,
                response: Response<GenericResponse<ArrayList<Apertura>>>
            ) {
                mld.value = response.body()
            }

            override fun onFailure(call: Call<GenericResponse<ArrayList<Apertura>>>, t: Throwable) {
                mld.value =
                    GenericResponse(
                        TIPO_RESULT,
                        RPTA_ERROR,
                        "internal exception:${t.message!!}",
                    )
            }

        })
        return mld
    }

    fun export(idCaja: Int, fechaApertura: String): LiveData<GenericResponse<ResponseBody>> {
        val mld = MutableLiveData<GenericResponse<ResponseBody>>()
        api.export(idCaja, fechaApertura).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
                    mld.value =
                        GenericResponse(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, response.body())
                    Log.d("exportComplaint", "file recived")
                } else {
                    mld.value = GenericResponse(
                        TIPO_DATA,
                        RPTA_WARNING,
                        response.errorBody()!!.string()
                    )
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mld.value = GenericResponse(
                    TIPO_DATA,
                    RPTA_ERROR,
                    "internal exception: no se ha podido descargar el reporte:${t.message!!}"
                )
            }
        })
        return mld
    }

    fun getCurrentDetails(idCaja: Int): LiveData<GenericResponse<ArrayList<DetalleCaja>>> {
        val mld = MutableLiveData<GenericResponse<ArrayList<DetalleCaja>>>()
        api.getCurrentDetails(idCaja)
            .enqueue(object : Callback<GenericResponse<ArrayList<DetalleCaja>>> {
                override fun onResponse(
                    call: Call<GenericResponse<ArrayList<DetalleCaja>>>,
                    response: Response<GenericResponse<ArrayList<DetalleCaja>>>
                ) {
                    mld.value = response.body()
                }

                override fun onFailure(
                    call: Call<GenericResponse<ArrayList<DetalleCaja>>>,
                    t: Throwable
                ) {
                    mld.value = GenericResponse(
                        TIPO_DATA,
                        RPTA_ERROR,
                        "internal exception: no se ha podido obtener los detalles de caja:${t.message!!}"
                    )
                }
            })
        return mld
    }

    fun getMovimientosByCajaId(idCaja: Int): LiveData<GenericResponse<ArrayList<MovCaja>>> {
        val mld = MutableLiveData<GenericResponse<ArrayList<MovCaja>>>()
        api.getMovimientosByCajaId(idCaja)
            .enqueue(object : Callback<GenericResponse<ArrayList<MovCaja>>> {
                override fun onResponse(
                    call: Call<GenericResponse<ArrayList<MovCaja>>>,
                    response: Response<GenericResponse<ArrayList<MovCaja>>>
                ) {
                    mld.value = response.body()
                }

                override fun onFailure(
                    call: Call<GenericResponse<ArrayList<MovCaja>>>,
                    t: Throwable
                ) {
                    mld.value = GenericResponse(
                        TIPO_DATA,
                        RPTA_ERROR,
                        "internal exception: no se ha podido listar los movimientos de caja:${t.message!!}"
                    )
                }

            })
        return mld;
    }
}