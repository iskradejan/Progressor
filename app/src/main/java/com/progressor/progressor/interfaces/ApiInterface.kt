package com.progressor.progressor.interfaces

import com.progressor.progressor.dataobjects.Model
import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.Observable

interface ApiInterface {

    @GET("api.php")
    fun hitCountCheck(
            @Query("action") action: String,
            @Query("format") format: String,
            @Query("list") list: String,
            @Query("srsearch") srsearch: String
    ): Observable<Model.Result>

    @GET("api.php")
    fun xxx(
            @Query("action") action: String,
            @Query("format") format: String,
            @Query("list") list: String,
            @Query("srsearch") srsearch: String
    ): Observable<Model.Result>
}