package com.kiran.retrofitstarterbatch26.ui

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.kiran.retrofitstarterbatch26.R
import com.kiran.retrofitstarterbatch26.api.ServiceBuilder
import com.kiran.retrofitstarterbatch26.model.User
import com.kiran.retrofitstarterbatch26.repository.UserRespository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var tvRegister: TextView
    private lateinit var btnLogin: Button
    private lateinit var chkRememberMe: CheckBox
    private lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        tvRegister = findViewById(R.id.tvRegister)
        btnLogin = findViewById(R.id.btnLogin)
        linearLayout = findViewById(R.id.linearLayout)

        if(!permissionCheck()){
            requestPermission()
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        btnLogin.setOnClickListener {
            login()
        }


    }



    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private fun permissionCheck(): Boolean {
        var hasPermission = true
        for(permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
                break
            }
        }
        return hasPermission
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this@LoginActivity, permissions,1)
    }

    private fun login() {
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()

//        val user = User(username = username, password = password)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = UserRespository()
                val response = repository.loginUser(username, password)

                if (response.success == true) {
                    ServiceBuilder.token = "Bearer ${response.token}"

                    withContext(Dispatchers.Main) {
                        val snack =
                            Snackbar.make(
                                linearLayout,
                                "login successful",
                                Snackbar.LENGTH_LONG
                            )
                        snack.setAction("OK", View.OnClickListener {
                            snack.dismiss()
                        })
                        snack.show()
                        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main){
                    val snack =
                        Snackbar.make(
                            linearLayout,
                            "Invalid credentials",
                            Snackbar.LENGTH_LONG
                        )
                    snack.setAction("OK", View.OnClickListener {
                        snack.dismiss()
                    })
                    snack.show()
                }


//                withContext(Dispatchers.Main) {
//                    Toast.makeText(this@LoginActivity, ex.toString(), Toast.LENGTH_SHORT)
//                        .show()
//                }
            }
        }
    }
}

