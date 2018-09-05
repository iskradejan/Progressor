package com.progressor.progressor.dataobjects.helper

class FirebaseResponse {
    private var success: Boolean ?= null
    private var type: String ?= null
    private var errors: MutableList<String> ?= null

    fun getSuccess() : Boolean? {
        return success
    }

    fun setSuccess(success:Boolean?) : FirebaseResponse {
        this.success = success
        return this
    }

    fun getType() : String? {
        return type
    }

    fun setType(type: String) : FirebaseResponse {
        this.type = type
        return this
    }

    fun getErrors() : List<String>? {
        return errors
    }

    fun setErrors(errors: MutableList<String>) : FirebaseResponse {
        this.errors = errors
        return this
    }
}