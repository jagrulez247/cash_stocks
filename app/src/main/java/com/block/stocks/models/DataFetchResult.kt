package com.block.stocks.models

data class DataFetchResult<out T>(
    val status: Status,
    val data: T?,
    val error: ApiError?,
    val message: String?
) {

    fun isLoadingStatus(): Boolean = status == Status.LOADING
    fun isSuccessStatus(): Boolean = status == Status.SUCCESS

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {

        fun <T> success(data: T?): DataFetchResult<T> {
            return DataFetchResult(Status.SUCCESS, data, null, null)
        }

        fun <T> error(message: String, error: ApiError? = null): DataFetchResult<T> {
            return DataFetchResult(Status.ERROR, null, error, message)
        }

        fun <T> loading(data: T? = null): DataFetchResult<T> {
            return DataFetchResult(Status.LOADING, data, null, null)
        }
    }

    override fun toString(): String {
        return "Result(status=$status, data=$data, error=$error, message=$message)"
    }
}