package com.kavya.newsreader.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Kavya P S on 16/06/20.
 */
object NetworkService {
    private val retrofit: Retrofit
    private const val BASE_URL: String = "https://newsapi.org/v2/";

    private val builder: Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder();
    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor();

    init {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(
            interceptor
        )
        retrofit = builder
            .client(httpClient.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    }

    fun <S> getNetworkHook(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass);
    }

}

