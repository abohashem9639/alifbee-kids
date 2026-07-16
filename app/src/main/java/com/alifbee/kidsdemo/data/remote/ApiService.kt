package com.alifbee.kidsdemo.data.remote

import com.alifbee.kidsdemo.data.remote.dto.InitialResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    @Headers("Accept: application/json")
    @GET("general/initial")
    suspend fun getInitial(): InitialResponse
}
