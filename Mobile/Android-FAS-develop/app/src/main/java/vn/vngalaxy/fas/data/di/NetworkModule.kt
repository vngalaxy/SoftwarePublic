package vn.vngalaxy.fas.data.di

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vn.vngalaxy.fas.data.di.Properties.CONNECTION_TIMEOUT
import vn.vngalaxy.fas.data.source.remote.api.ChirpStackApi
import vn.vngalaxy.fas.shared.constant.Constant.AUTH_TOKEN
import vn.vngalaxy.fas.shared.constant.Constant.END_POINT_URL
import java.util.concurrent.TimeUnit

val networkModule: Module = module {
    single { createRetrofit() }
    single { createChirpStackApi() }
}

object Properties {
    const val CONNECTION_TIMEOUT: Long = 60
}

fun createRetrofit(): Retrofit {
    val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    httpClient.addInterceptor { chain ->
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("Authorization", "Bearer $AUTH_TOKEN")
        val request = requestBuilder.build()
        chain.proceed(request)
    }
    httpClient.addInterceptor(createLoggingInterceptor())
    httpClient.apply {
        readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
    }

    val gson = GsonBuilder().create()

    return Retrofit.Builder().baseUrl(END_POINT_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient.build())
        .build()
}

fun createChirpStackApi(): ChirpStackApi = createRetrofit().create(ChirpStackApi::class.java)

fun createLoggingInterceptor(): Interceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}

