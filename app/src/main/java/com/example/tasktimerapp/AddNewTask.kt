package com.example.tasktimerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.tasktimerapp.room.Task
import com.example.tasktimerapp.room.TaskDatabase

class AddNewTask : AppCompatActivity() {

    lateinit var edName: EditText
    lateinit var edDes: EditText
    lateinit var saveBtn: Button
    lateinit var viewBtn: Button

    lateinit var myDBRoom: TaskDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_task)

        edName = findViewById(R.id.name_ed)
        edDes = findViewById(R.id.des_ed)
        saveBtn = findViewById(R.id.buttonS)
        viewBtn = findViewById(R.id.buttonView)

        myDBRoom = TaskDatabase.getInstance(this)

        saveBtn.setOnClickListener {

            //save the user enters to the database
            val userEnterName = edName.text.toString()
            val userEnterDesc = edDes.text.toString()

            if (userEnterName.isNotEmpty() && userEnterDesc.isNotEmpty()) {

                myDBRoom.taskDao().insertTask(Task(0, userEnterName, userEnterDesc,0))

                Toast.makeText(this, "Data Save Successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Please Enter All  ", Toast.LENGTH_SHORT).show()

            }
            edName.text.clear()
            edName.clearFocus()

            edDes.text.clear()
            edDes.clearFocus()
        }

        viewBtn.setOnClickListener {
            //go to view task fragments
            val intent = Intent(this,ViewTaskRV::class.java)
            startActivity(intent)
        }
    }
}