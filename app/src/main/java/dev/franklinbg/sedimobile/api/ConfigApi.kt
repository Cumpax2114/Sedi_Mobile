package dev.franklinbg.sedimobile.api

import com.google.gson.GsonBuilder
import dev.franklinbg.sedimobile.utils.DateSerializer
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

        init {
            val gson = GsonBuilder()
                .registerTypeAdapter(Date::class.java, DateSerializer())
                //.registerTypeAdapter(Time::class.java, TimeSerializer())
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getClient())
                .build()
            usuarioApi = retrofit.create(UsuarioApi::class.java)
        }

        private fun getClient(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val builder = OkHttpClient.Builder()
            builder.addInterceptor(logging)
                .cache(null)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS) //.addInterceptor(authInterceptor)
            return builder.build()
        }
    }
}