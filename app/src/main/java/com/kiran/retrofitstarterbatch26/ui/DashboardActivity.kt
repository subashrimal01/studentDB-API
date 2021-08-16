package com.kiran.retrofitstarterbatch26.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.kiran.retrofitstarterbatch26.AddStudentActivity
import com.kiran.retrofitstarterbatch26.R
import com.kiran.retrofitstarterbatch26.ShowStudentActivity

class DashboardActivity : AppCompatActivity() {
    private lateinit var btnAddStudent: ImageButton
    private lateinit var btnViewStudent: ImageButton
    private lateinit var btnGoogleMap: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        btnAddStudent = findViewById(R.id.btnAddStudent)
        btnViewStudent = findViewById(R.id.btnViewStudent)
        btnGoogleMap = findViewById(R.id.btnGoogleMap)

        btnAddStudent.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, AddStudentActivity::class.java))
        }

        btnViewStudent.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, ShowStudentActivity::class.java ))
        }


    }
}