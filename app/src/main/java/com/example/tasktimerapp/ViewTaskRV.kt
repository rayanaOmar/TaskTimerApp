package com.example.tasktimerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewTaskRV : AppCompatActivity() {

    private lateinit var recyclerV: RecyclerView
    private lateinit var taskList:List<Task>
    lateinit var myDBRoom: TaskDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_task_rv)

        taskList= listOf()
        recyclerV=findViewById(R.id.rv)

        myDBRoom = TaskDatabase.getInstance(this)
        taskList= myDBRoom.taskDao().getAllTasks()

        Log.d("MainTest",taskList.toString())

        recyclerV.adapter = RVAdapter(this, taskList)
        recyclerV.layoutManager = LinearLayoutManager(this)

        recyclerV.scrollToPosition(taskList.size - 1)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item,menu)
        return super.onCreateOptionsMenu(menu)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.homeId -> {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                Toast.makeText(this, "Home Page", Toast.LENGTH_SHORT).show()

            }
            R.id.addTaskId -> {
                val i = Intent(this, AddNewTask::class.java)
                startActivity(i)
                Toast.makeText(this, "New Task Page", Toast.LENGTH_SHORT).show()

            }

            R.id.viewTaskId -> {
                val i = Intent(this, ViewTaskRV::class.java)
                startActivity(i)
                Toast.makeText(this, "View Tasks Page", Toast.LENGTH_SHORT).show()

            }

            R.id.viewTaskWithTimeId -> {
                val i = Intent(this, TaskWithTime::class.java)
                startActivity(i)
                Toast.makeText(this, "Tasks With Timer Page", Toast.LENGTH_SHORT).show()

            }
        }
        return super.onOptionsItemSelected(item)
    }
}