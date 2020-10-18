package com.bebesaurios.xcom2.service

sealed class Result<out T, out E> {
    data class Success<out T, out E>(val value: T) : Result<T, E>()
    data class Failure<out T, out E>(val error: E) : Result<T, E>()
}