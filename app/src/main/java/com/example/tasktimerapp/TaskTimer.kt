package com.example.tasktimerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Chronometer
import android.widget.Toast
import com.example.tasktimerapp.room.Task
import com.example.tasktimerapp.room.TaskDatabase
import kotlinx.android.synthetic.main.activity_task_timer.*

class TaskTimer : AppCompatActivity() {

    lateinit var chronometer: Chronometer
    lateinit var myDBRoom: TaskDatabase
    lateinit var taskList:List<Task>

    private var pauseOffset: Long = 0
    private var running = false

    var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_timer)

        taskList= listOf()
        position = (intent.extras?.getInt("Position")!!)

        //Database Section
        myDBRoom = TaskDatabase.getInstance(this)
        taskList = myDBRoom.taskDao().getAllTasks()

        val taskName = taskList[position].name
        val taskDescription = taskList[position].description

        nameTV.text = taskName
        desTV.text = taskDescription

        //------------------Timer Section Start------------------
        chronometer = findViewById(R.id.chronometer)

        chronometer.setFormat("Time: %s")
        chronometer.setBase(SystemClock.elapsedRealtime())


        chronometer.setOnChronometerTickListener(Chronometer.OnChronometerTickListener { chronometer ->
            if (SystemClock.elapsedRealtime() - chronometer.base >= 10000000) {
                chronometer.base = SystemClock.elapsedRealtime()
                Toast.makeText(this, "Bing!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //onClick Start the time
    fun startChronometer(v: View?) {
        if (!running) {
            chronometer!!.base = SystemClock.elapsedRealtime() - pauseOffset
            chronometer!!.start()
            running = true
        }
    }

    //onClick Pause the time and save it
    fun pauseChronometer(v: View?) {
        if (running) {
            chronometer!!.stop()
            pauseOffset = SystemClock.elapsedRealtime() - chronometer!!.base
            running = false

            val time = chronometer!!.text.toString().subSequence(5,11)
            showTimer.text= time

            //save the time to the database
            myDBRoom = TaskDatabase.getInstance(this)
            taskList = myDBRoom.taskDao().getAllTasks()

            taskList[position].time = time.toString()

            val taskName = taskList[position].name
            val taskDescription = taskList[position].description
            val taskTime = taskList[position].time
            val taskID = taskList[position].id

            Log.d("MainTest", "pauseChronometer: $taskID")

            myDBRoom.taskDao().updateTimeTask(Task(taskID, taskName,taskDescription,pauseOffset,taskTime))

        }
    }
    fun resetChronometer(v: View?) {
        chronometer!!.base = SystemClock.elapsedRealtime()
        pauseOffset = 0
    }

    //val intent = Intent(activity, TaskTimer::class.java)
    //                intent.putExtra("Position",position)
    //                startActivity(activity,intent, Bundle())
    //Log.d("taskTime1","taskTime1 + $taskTime1")
}