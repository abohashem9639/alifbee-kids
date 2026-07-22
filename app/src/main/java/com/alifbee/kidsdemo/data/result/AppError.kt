package com.alifbee.kidsdemo.data.result

sealed class AppError {
//    data object Timeout : AppError()
//    data object Network : AppError()
//    data object Serialization : AppError()
//    data object Unauthorized : AppError()
//    data object Client : AppError()
//    data object Server : AppError()
//    data object Redirect : AppError()
    data class General(val errorKey: String? = null) : AppError()
}
