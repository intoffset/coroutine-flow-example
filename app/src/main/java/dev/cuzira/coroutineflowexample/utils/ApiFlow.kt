package dev.cuzira.coroutineflowexample.utils

import dev.cuzira.coroutineflowexample.model.Future
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import retrofit2.Response


inline fun <reified T : Any> apiFlow(crossinline call: suspend () -> Response<T>): Flow<Future<T>> =
    flow<Future<T>> {
        val response = call()
        if (response.isSuccessful) emit(Future.Success(value = response.body()!!))
        else throw HttpException(response)
    }.catch { it: Throwable ->
        emit(Future.Error(it))
    }.onStart {
        emit(Future.Proceeding)
    }.flowOn(Dispatchers.IO)

inline fun <reified T : Any?> apiNullableFlow(crossinline call: suspend () -> Response<T?>): Flow<Future<T?>> =
    flow<Future<T?>> {
        val response = call()
        if (response.isSuccessful) emit(Future.Success(value = response.body()))
        else throw HttpException(response)
    }.catch { it: Throwable ->
        emit(Future.Error(it))
    }.onStart {
        emit(Future.Proceeding)
    }.flowOn(Dispatchers.IO)

