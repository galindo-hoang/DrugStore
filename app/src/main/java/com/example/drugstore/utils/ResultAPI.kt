package com.example.drugstore.utils

data class ResourceAPI<T>(val status:Status, val data: T?, val message:String?){
    companion object {
        fun <T> success(data: T): ResourceAPI<T> = ResourceAPI(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): ResourceAPI<T> =
            ResourceAPI(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): ResourceAPI<T> = ResourceAPI(status = Status.LOADING, data = data, message = null)
    }
}
