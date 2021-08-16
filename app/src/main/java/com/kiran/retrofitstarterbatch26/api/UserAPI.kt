package com.kiran.retrofitstarterbatch26.api

import com.kiran.retrofitstarterbatch26.model.User
import com.kiran.retrofitstarterbatch26.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserAPI {

    @POST("auth/register")
    suspend fun registerUser(
        @Body user: User
    ): Response<UserResponse>


//    @POST("auth/login")
//    suspend fun loginUser(
//        @Body user: User
//    ): Response<UserResponse>

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun loginUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<UserResponse>
}
