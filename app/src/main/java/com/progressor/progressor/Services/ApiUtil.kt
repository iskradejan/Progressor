package com.progressor.progressor.Services

import com.progressor.progressor.Model.Model
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

class ApiUtil @Inject constructor() {

    @field:[Inject Named("rocketHq")]
    lateinit var rocketHqEndPoints: ApiInterface

    fun getHitCount() : Observable<Model.Result>{
        return rocketHqEndPoints.hitCountCheck("query", "json", "search", "pogba")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }
}