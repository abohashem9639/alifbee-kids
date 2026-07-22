package com.alifbee.kidsdemo.data.result

sealed class BaseRes<out T> {
    data class Body<T>(val body: T) : BaseRes<T>()
    data class Error(val error: AppError) : BaseRes<Nothing>()
}

inline fun <T> BaseRes<T>.onSuccess(action: (T) -> Unit): BaseRes<T> {
    if (this is BaseRes.Body) action(body)
    return this
}

inline fun <T> BaseRes<T>.onFailure(action: (AppError) -> Unit): BaseRes<T> {
    if (this is BaseRes.Error) action(error)
    return this
}

val <T> BaseRes<T>.body: T?
    get() = (this as? BaseRes.Body)?.body

val <T> BaseRes<T>.error: AppError?
    get() = (this as? BaseRes.Error)?.error
