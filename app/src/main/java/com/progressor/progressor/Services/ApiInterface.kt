package com.progressor.progressor.Services

import com.progressor.progressor.Model.Model
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
}