package kady.muhammad.paytabstask.app

sealed class Result {
    object Loading : Result()
    data class Success<T>(val data: T) : Result()
    data class Error(val message: String) : Result()
}
