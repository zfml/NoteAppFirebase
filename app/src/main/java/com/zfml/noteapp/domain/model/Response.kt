package com.zfml.noteapp.domain.model

sealed class Response<out T> {
    object Loading: Response<Nothing>()
    data class Success<T>(val data: T): Response<T>()
    data class Error(val error: Exception?): Response<Nothing>()
}