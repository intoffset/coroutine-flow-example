package dev.cuzira.coroutineflowexample.model

sealed class Future<out T> {
    object Proceeding : Future<Nothing>()
    data class Success<out T>(val data: T) : Future<T>()
    data class Error(val error: Exception) : Future<Nothing>()
}
