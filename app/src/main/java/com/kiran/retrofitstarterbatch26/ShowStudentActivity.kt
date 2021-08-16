package com.kiran.retrofitstarterbatch26

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kiran.retrofitstarterbatch26.adapter.StudentAdapter
import com.kiran.retrofitstarterbatch26.model.Student
import com.kiran.retrofitstarterbatch26.repository.StudentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowStudentActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

//    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_student)

        recyclerView = findViewById(R.id.recyclerView)

        showStudent()

    }

    private fun showStudent() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = StudentRepository()
                val response = repository.showStudent()
                if (response.success == true) {
                    val student = response.data
                    withContext(Main) {
                    val adapter = StudentAdapter(student!! as ArrayList<Student>, this@ShowStudentActivity)
                    recyclerView.layoutManager =
                        LinearLayoutManager(this@ShowStudentActivity)  //LinearlayoutManager.Horizontal for horizontal display
                    recyclerView.adapter = adapter

                    }
                }
            } catch (ex: Exception) {
                withContext(Main) {
                    Toast.makeText(this@ShowStudentActivity, ex.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}