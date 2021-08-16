package com.kiran.retrofitstarterbatch26.repository

import com.kiran.retrofitstarterbatch26.api.MyAPIRequest
import com.kiran.retrofitstarterbatch26.api.ServiceBuilder
import com.kiran.retrofitstarterbatch26.api.UserAPI
import com.kiran.retrofitstarterbatch26.model.User
import com.kiran.retrofitstarterbatch26.response.UserResponse

class UserRespository : MyAPIRequest() {

    private val userApi = ServiceBuilder.builderService(UserAPI::class.java)

    suspend fun registerUser(user: User): UserResponse{
        return  apiRequest {
            userApi.registerUser(user)
        }
    }
    suspend fun loginUser(username: String, password: String): UserResponse{
        return apiRequest {
            userApi.loginUser(username, password)
        }
    }
}