package com.example.tasktimerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimerapp.adapter.RVAdapter
import com.example.tasktimerapp.room.Task
import com.example.tasktimerapp.room.TaskDatabase

class ViewTaskRV : AppCompatActivity() {

    lateinit var recyclerV: RecyclerView
    lateinit var taskList:List<Task>
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
}