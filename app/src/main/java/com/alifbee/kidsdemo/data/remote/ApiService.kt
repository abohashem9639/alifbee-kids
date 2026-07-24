package com.alifbee.kidsdemo.data.remote

import com.alifbee.kidsdemo.data.remote.dto.InitialBody
import com.alifbee.kidsdemo.data.remote.dto.SignInBody
import com.alifbee.kidsdemo.data.remote.dto.SignInRequest
import com.alifbee.kidsdemo.data.remote.dto.SyllabusBody
import com.alifbee.kidsdemo.data.remote.dto.UpdateProfileBody
import com.alifbee.kidsdemo.data.remote.dto.UpdateProfileRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @Headers("Accept: application/json")
    @GET("v2/general/initial")
    suspend fun getInitial(): ApiResponse<InitialBody>

    @Headers("Accept: application/json")
    @POST("v2/auth/signin")
    suspend fun signIn(@Body request: SignInRequest): ApiResponse<SignInBody>

    @Headers("Accept: application/json")
    @GET("v5/syllabus/get_syllabus")
    suspend fun getSyllabus(@Query("environment") environment: String): ApiResponse<SyllabusBody>

    @Headers("Accept: application/json")
    @POST("v2/users/update_user_profile")
    suspend fun updateUserProfile(@Body request: UpdateProfileRequest): ApiResponse<UpdateProfileBody>
}
