package dev.franklinbg.sedimobile.api.search

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.Interceptor
import retrofit2.create

class SearchConfigApi {
    companion object {
        private const val token =
            "aa965d5a9b03f04f82779df610c43e93d440ea2b73407183f0d9fab080afc772"
        private const val baseUrl = "https://apiperu.dev"
        var buscarDniApi: BuscarPorDNIApi
        var buscarPorRUCApi: BuscarPorRUCApi

        init {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .client(getClient())
                .build()
            buscarDniApi = retrofit.create(BuscarPorDNIApi::class.java)
            buscarPorRUCApi = retrofit.create(BuscarPorRUCApi::class.java)
        }

        private fun getClient(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val builder = OkHttpClient.Builder()
            val authInterceptor =
                Interceptor { chain ->
                    chain.proceed(
                        chain.request().newBuilder().addHeader("Authorization", "Bearer $token")
                            .build()
                    )
                }
            builder.addInterceptor(logging)
                .addInterceptor(authInterceptor)
                .cache(null)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS) //.addInterceptor(authInterceptor)
            return builder.build()
        }

    }
}