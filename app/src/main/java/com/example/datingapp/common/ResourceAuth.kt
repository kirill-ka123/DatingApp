package com.example.datingapp.common

sealed class ResourceAuth<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Loading<T> : ResourceAuth<T>()
    class Success<T>(data: T) : ResourceAuth<T>(data)
    class Error<T>(message: String?, data: T? = null) : ResourceAuth<T>(data, message)
}