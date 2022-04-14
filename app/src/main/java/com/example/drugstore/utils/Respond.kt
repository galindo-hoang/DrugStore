package com.example.drugstore.utils

data class Respond<T>(val status:Status, val data: T?, val message:String?){
    companion object {
        fun <T> success(data: T): Respond<T> =
            Respond(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Respond<T> =
            Respond(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): Respond<T> =
            Respond(status = Status.LOADING, data = data, message = null)
    }
}
