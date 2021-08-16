package com.kiran.retrofitstarterbatch26.adapter

import android.app.AlertDialog
import android.content.Context
import android.system.Os.remove
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kiran.retrofitstarterbatch26.R
import com.kiran.retrofitstarterbatch26.ShowStudentActivity
import com.kiran.retrofitstarterbatch26.model.Student
import com.kiran.retrofitstarterbatch26.repository.StudentRepository
import com.kiran.retrofitstarterbatch26.response.StudentGetResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class StudentAdapter(
    val lststudent: ArrayList<Student>,
    val context: Context

) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvAge: TextView = view.findViewById(R.id.tvAge)
        val tvGender: TextView = view.findViewById(R.id.tvGender)
        val tvAddress: TextView = view.findViewById(R.id.tvAddress)
        val btndelete: ImageButton = view.findViewById(R.id.btndelete)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_design, parent, false)

        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = lststudent[position]
        holder.tvName.text = student.fullname
        holder.tvAddress.text = student.address
        holder.tvAge.text = student.age.toString()
        holder.tvGender.text = student.gender


        holder.btndelete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete student")
            builder.setMessage("Are you sure you want to delete ${student.fullname} ??")
            builder.setIcon(android.R.drawable.ic_delete)
            builder.setPositiveButton("Yes") { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val studentRepository = StudentRepository()
                        val response = studentRepository.deleteStudent(student._id)
                        if (response.success == true) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "Student Deleted",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            withContext(Main) {
                                lststudent.remove(student)
                                notifyDataSetChanged()
                            }
                        }
                    } catch (ex: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                ex.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            builder.setNegativeButton("No") { _, _ ->
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

    }

    override fun getItemCount(): Int {
        return lststudent.size
    }
}