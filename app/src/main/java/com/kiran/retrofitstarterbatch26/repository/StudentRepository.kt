package com.kiran.retrofitstarterbatch26.repository

import com.kiran.retrofitstarterbatch26.api.MyAPIRequest
import com.kiran.retrofitstarterbatch26.api.ServiceBuilder
import com.kiran.retrofitstarterbatch26.api.StudentAPI
import com.kiran.retrofitstarterbatch26.model.Student
import com.kiran.retrofitstarterbatch26.response.DeleteStudentResponse
import com.kiran.retrofitstarterbatch26.response.StudentGetResponse


class StudentRepository : MyAPIRequest() {
    private val studentAPI = ServiceBuilder.builderService(StudentAPI::class.java)

    suspend fun addStudent(student: Student): StudentGetResponse {
        return apiRequest {
            studentAPI.addStudent(student, ServiceBuilder.token!!)
        }
    }

    suspend fun showStudent(): StudentGetResponse {
        return apiRequest {
            studentAPI.showStudent(ServiceBuilder.token!!)
        }
    }

    suspend fun deleteStudent(id: String): DeleteStudentResponse {
        return apiRequest {
            studentAPI.deleteStudent(ServiceBuilder.token!!,id)
        }
    }

}