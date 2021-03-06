package com.progressor.progressor.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.progressor.progressor.MainActivity
import com.progressor.progressor.di.interfaces.ApiInterface
import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.SidePaneManager
import com.progressor.progressor.service.UserManager
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
open class ApiModule(private val mainActivity: MainActivity) {
    private val context: Context = mainActivity.baseContext

    @Provides
    @Singleton
    fun getSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences("progressorSharedPreferences", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun buildGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun buildOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun createRetrofitBuilder(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    @Provides
    @Singleton
    fun createRetrofitInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun authenticationManager(): AuthenticationManager {
        return AuthenticationManager(mainActivity)
    }

    @Provides
    @Singleton
    fun userManager(): UserManager {
        return UserManager(mainActivity)
    }

    @Provides
    @Singleton
    fun sidePaneManager(): SidePaneManager {
        return SidePaneManager(mainActivity)
    }
}