package com.kiran.retrofitstarterbatch26.response

import com.kiran.retrofitstarterbatch26.model.Student

data class StudentGetResponse (
    val success: Boolean? = null,
    val data: MutableList<Student>? =null,
)
