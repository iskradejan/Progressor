package com.progressor.progressor.services

import com.progressor.progressor.dataobjects.Model
import com.progressor.progressor.interfaces.ApiInterface
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HitFetcher @Inject constructor() {

    @field:Inject
    lateinit var rocketHqEndPoints: ApiInterface

    fun fetch(): Observable<Model.Result> {
        return rocketHqEndPoints.xxx("query", "json", "search", "pogba")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }
}