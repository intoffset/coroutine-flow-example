package dev.cuzira.coroutineflowexample.utils

import dev.cuzira.coroutineflowexample.model.Future
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import retrofit2.Response


inline fun <reified T : Any> apiFlow(
    crossinline call: suspend () -> Response<T>,
): Flow<Future<T>> = flow {
    val response = call()
    val future: Future<T> = if (response.isSuccessful) {
        Future.Success(data = response.body()!!)
    } else {
        Future.Error(HttpException(response))
    }
    emit(future)
}
    .flowOn(Dispatchers.IO)
    .catch { it: Throwable ->
        if (it is Exception) {
            emit(Future.Error(error = it))
        } else {
            emit(Future.Error(error = Exception(it.message ?: "$it has been occurred")))
        }
    }.onStart { emit(Future.Proceeding) }

inline fun <reified T : Any?> apiNullableFlow(
    crossinline call: suspend () -> Response<T?>,
): Flow<Future<T?>> = flow {
    val response = call()
    val future = if (response.isSuccessful) {
        Future.Success(data = response.body())
    } else {
        Future.Error(HttpException(response))
    }
    emit(future)
}
    .flowOn(Dispatchers.IO)
    .catch { it: Throwable ->
        if (it is Exception) {
            emit(Future.Error(error = it))
        } else {
            emit(Future.Error(error = Exception(it.message ?: "$it has been occurred")))
        }
    }.onStart { emit(Future.Proceeding) }
