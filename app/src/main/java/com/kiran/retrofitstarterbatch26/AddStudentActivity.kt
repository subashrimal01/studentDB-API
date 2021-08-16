package com.kiran.retrofitstarterbatch26

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import com.kiran.retrofitstarterbatch26.model.Student
import com.kiran.retrofitstarterbatch26.repository.StudentRepository
import com.kiran.retrofitstarterbatch26.ui.DashboardActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddStudentActivity : AppCompatActivity() {
    private lateinit var fullname: EditText
    private lateinit var age: EditText
    private lateinit var gender: EditText
    private lateinit var address: EditText
    private lateinit var photo: EditText
    private lateinit var btnadd: Button
    private lateinit var profile: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        fullname = findViewById(R.id.fullname)
        age = findViewById(R.id.age)
        gender = findViewById(R.id.gender)
        address = findViewById(R.id.address)
        btnadd = findViewById(R.id.btnadd)
        photo = findViewById(R.id.photo)
        profile = findViewById(R.id.profile)


        btnadd.setOnClickListener {
            addStudent()
        }

        profile.setOnClickListener {
            loadPopUpMenu()
        }
    }

    private fun loadPopUpMenu() {
        val popMenu = PopupMenu(this@AddStudentActivity, profile)
        popMenu.menuInflater.inflate(R.menu.gallery_camera, popMenu.menu)
        popMenu.setOnMenuItemClickListener { item->
            if (item.itemId == R.id.menuGallery){
                openGallery()
            }else if (item.itemId == R.id.menuCamera){
                openCamera()
            }
            true
        }
        popMenu.show()
    }

    private val CAMERA_CODE = 1
    private val GALLERY_CODE = 0
    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent,GALLERY_CODE)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent,CAMERA_CODE)

    }

    private fun addStudent() {
        val fullname = fullname.text.toString()
        val age = age.text.toString().toInt()
        val gender = gender.text.toString()
        val address = address.text.toString()
        val photo = photo.text.toString()

        val student =
            Student(fullname = fullname, age = age,gender = gender, address = address, photo = photo )


        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = StudentRepository()
                val response = repository.addStudent(student)
                if(response.success == true){

                    withContext(Dispatchers.Main){
                        Toast.makeText(this@AddStudentActivity, "Successfully added", Toast.LENGTH_SHORT).show()
                    }
                    startActivity(Intent(this@AddStudentActivity, DashboardActivity::class.java))
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@AddStudentActivity, ex.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}