package dev.franklinbg.sedimobile.api

import com.google.gson.GsonBuilder
import dev.franklinbg.sedimobile.utils.DateSerializer
import dev.franklinbg.sedimobile.utils.SqlDateSerializer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.Date

class ConfigApi private constructor() {
    companion object {
        private const val baseUrl = "http://10.0.2.2:8089"
        var usuarioApi: UsuarioApi
        var clienteApi: ClienteApi
        var cajaApi: CajaApi
        var metodoPagoApi: MetodoPagoApi
        var proveedorApi: ProveedorApi
        var conceptoMovCajaApi: ConceptoMovCajaApi
        var tipoControApi: TipoContratoApi
        var contratoApi: ContratoApi

        init {
            val gson = GsonBuilder()
                .registerTypeAdapter(Date::class.java, DateSerializer())
                .registerTypeAdapter(java.sql.Date::class.java, SqlDateSerializer())
                //.registerTypeAdapter(Time::class.java, TimeSerializer())
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getClient())
                .build()
            usuarioApi = retrofit.create(UsuarioApi::class.java)
            clienteApi = retrofit.create(ClienteApi::class.java)
            cajaApi = retrofit.create(CajaApi::class.java)
            metodoPagoApi = retrofit.create(MetodoPagoApi::class.java)
            proveedorApi = retrofit.create(ProveedorApi::class.java)
            conceptoMovCajaApi = retrofit.create(ConceptoMovCajaApi::class.java)
            tipoControApi = retrofit.create(TipoContratoApi::class.java)
            contratoApi = retrofit.create(ContratoApi::class.java)
        }

        private fun getClient(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val builder = OkHttpClient.Builder()
            builder.addInterceptor(logging)
                .cache(null)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS) //.addInterceptor(authInterceptor)
            return builder.build()
        }
    }
}