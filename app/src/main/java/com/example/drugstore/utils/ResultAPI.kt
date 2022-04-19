package com.example.drugstore.utils

data class ResultAPI<T>(val status:Status, val data: T?, val message:String?){
    companion object {
        fun <T> success(data: T): ResultAPI<T> = ResultAPI(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): ResultAPI<T> =
            ResultAPI(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): ResultAPI<T> = ResultAPI(status = Status.LOADING, data = data, message = null)
    }
}
