package com.progressor.progressor.model.constant

class FirebaseConstant {
    companion object {
        const val ERROR_EMAIL_ALREADY_IN_USE = "ERROR_EMAIL_ALREADY_IN_USE"
        const val ERROR_WEAK_PASSWORD = "ERROR_WEAK_PASSWORD"
        const val ERROR_WRONG_PASSWORD = "ERROR_WRONG_PASSWORD"
        const val ERROR_USER_NOT_FOUND = "ERROR_USER_NOT_FOUND"
        const val ERROR_INVALID_EMAIL = "ERROR_INVALID_EMAIL"

        const val TYPE_LOGIN = "TYPE_LOGIN"
        const val TYPE_CREATE_ACCOUNT = "TYPE_CREATE_ACCOUNT"
        const val TYPE_PASSWORD_RESET = "TYPE_PASSWORD_RESET"
        const val TYPE_EMAIL_VERIFICATION = "TYPE_EMAIL_VERIFICATION"
    }
}