package com.progressor.progressor.services

import com.progressor.progressor.dataobjects.Model
import com.progressor.progressor.interfaces.ApiInterface
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ApiUtil @Inject constructor() {

    @field:Inject
    lateinit var apiInterface: ApiInterface

    fun getHitCount() : Observable<Model.Result> {
        return apiInterface.hitCountCheck("query", "json", "search", "pogba")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }
}