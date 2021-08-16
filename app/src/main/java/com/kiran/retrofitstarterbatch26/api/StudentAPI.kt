package com.kiran.retrofitstarterbatch26.api

import com.kiran.retrofitstarterbatch26.model.Student
import com.kiran.retrofitstarterbatch26.response.DeleteStudentResponse
import com.kiran.retrofitstarterbatch26.response.StudentGetResponse

import retrofit2.Response
import retrofit2.http.*

interface StudentAPI {

    @POST("student")
    suspend fun addStudent(
        @Body student: Student,
        @Header("Authorization") token: String
    ): Response<StudentGetResponse>

    @GET("student/")
    suspend fun showStudent(
        @Header("Authorization") token: String
    ): Response<StudentGetResponse>

    @DELETE("student/{id}")
    suspend fun deleteStudent(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<DeleteStudentResponse>
}