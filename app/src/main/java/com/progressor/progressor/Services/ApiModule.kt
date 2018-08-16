package com.progressor.progressor.Services

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
open class ApiModule {
    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    @Named("rocketOkHttp")
    fun provideRocketOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    @Named("retroRocketHq")
    fun provideRetrofitRocketHq(gson: Gson, @Named("rocketOkHttp") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    @Provides
    @Singleton
    @Named("rocketHq")
    fun provideRetrofitEndPointServiceRocketHq(@Named("retroRocketHq") retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
}