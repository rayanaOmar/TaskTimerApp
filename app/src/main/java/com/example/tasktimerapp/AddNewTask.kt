package com.example.tasktimerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddNewTask : AppCompatActivity() {

    lateinit var edName: EditText
    lateinit var edDes: EditText
    lateinit var saveBtn: Button
    lateinit var viewBtn: Button
    lateinit var taskList: List<Task>
    lateinit var myDBRoom: TaskDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_task)

        edName = findViewById(R.id.name_ed)
        edDes = findViewById(R.id.des_ed)
        saveBtn = findViewById(R.id.buttonS)
        viewBtn = findViewById(R.id.buttonView)

        myDBRoom = TaskDatabase.getInstance(this)
        taskList = listOf()

        saveBtn.setOnClickListener {

            //save the user enters to the database
            val userEnterName = edName.text.toString()
            val userEnterDesc = edDes.text.toString()

            if (userEnterName.isNotEmpty() && userEnterDesc.isNotEmpty()) {

                myDBRoom.taskDao().insertTask(Task(0, userEnterName, userEnterDesc, 0))
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
            //go to view task Activity
            val intent = Intent(this, ViewTaskRV::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeId -> {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                Toast.makeText(this, "This is Home Page", Toast.LENGTH_SHORT).show()

            }
            R.id.addTaskId -> {
                val i = Intent(this, AddNewTask::class.java)
                startActivity(i)
                Toast.makeText(this, "This is New Task Page", Toast.LENGTH_SHORT).show()

            }

            R.id.viewTaskId -> {
                val i = Intent(this, ViewTaskRV::class.java)
                startActivity(i)
                Toast.makeText(this, "This is View Tasks Page", Toast.LENGTH_SHORT).show()

            }
            R.id.viewTaskWithTimeId -> {
                val i = Intent(this, TaskWithTime::class.java)
                startActivity(i)
                Toast.makeText(this, "This is  Tasks With Timer Page", Toast.LENGTH_SHORT).show()

            }
        }
        return super.onOptionsItemSelected(item)
    }
}