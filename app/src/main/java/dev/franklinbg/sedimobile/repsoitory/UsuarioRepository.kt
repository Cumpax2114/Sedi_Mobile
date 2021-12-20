package dev.franklinbg.sedimobile.repsoitory


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.franklinbg.sedimobile.api.ConfigApi
import dev.franklinbg.sedimobile.model.Usuario
import dev.franklinbg.sedimobile.utils.GenericResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import dev.franklinbg.sedimobile.utils.Global.*

class UsuarioRepository {
    private val api = ConfigApi.usuarioApi
    fun login(email: String, password: String): LiveData<GenericResponse<Usuario>> {
        val mld = MutableLiveData<GenericResponse<Usuario>>()
        api.login(email, password).enqueue(object : Callback<GenericResponse<Usuario>> {
            override fun onResponse(
                call: Call<GenericResponse<Usuario>>,
                response: Response<GenericResponse<Usuario>>
            ) {
                mld.value = response.body()
            }

            override fun onFailure(call: Call<GenericResponse<Usuario>>, t: Throwable) {
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