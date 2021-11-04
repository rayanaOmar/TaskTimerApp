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

class TaskWithTime : AppCompatActivity() {

    private lateinit var recyclerV: RecyclerView
    private lateinit var taskListTime:List<Task>
    private lateinit var myDBRoom: TaskDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_with_time)

        taskListTime= listOf()
        recyclerV=findViewById(R.id.rvTime)

        myDBRoom = TaskDatabase.getInstance(this)
        taskListTime= myDBRoom.taskDao().getAllTasks()

        Log.d("MainTest",taskListTime.toString())

        recyclerV.adapter = RVAdapterTime(this, taskListTime)
        recyclerV.layoutManager = LinearLayoutManager(this)

        recyclerV.scrollToPosition(taskListTime.size - 1)
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