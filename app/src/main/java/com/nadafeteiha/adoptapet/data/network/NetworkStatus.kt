package com.nadafeteiha.adoptapet.data.network

sealed class NetworkStatus<out T> {
    data class Success<T>(val data: T?) : NetworkStatus<T>()
    class Failure(val message: String) : NetworkStatus<Nothing>()
    class Loading<T> : NetworkStatus<T>()
}