package com.progressor.progressor.services

import com.progressor.progressor.di.interfaces.ApiInterface
import javax.inject.Inject

class ApiRequestor @Inject constructor() {

    @field:Inject
    lateinit var apiInterface: ApiInterface

}