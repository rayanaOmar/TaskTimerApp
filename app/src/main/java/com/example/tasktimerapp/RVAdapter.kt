package com.example.tasktimerapp

import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class RVAdapter (private val activity: ViewTaskRV, private var taskList:List<Task>): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {

    private var chronometer: Chronometer?= null //Like runningTask
    private var oldTask: Task? = null

    lateinit var myDBRoom: TaskDatabase

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        myDBRoom = TaskDatabase.getInstance(activity)
        myDBRoom.taskDao().getAllTasks()

        val task = taskList[position]

        val countDownTimer = Chronometer(holder.itemView.context)
        countDownTimer.isVisible = false
        countDownTimer.gravity= 17


        holder.itemView.apply {
            var running = false
            taskName.text = " \n${task.name}"
            taskDescription.text = "${task.description}\n\n${task.time} "
            linear.addView(countDownTimer)

            //onClick
            cardView.setOnClickListener {
                taskDescription.text = "${task.description}\n "

                running = !running
                countDownTimer.isVisible = true

                if(chronometer == null){ //
                    if(running){
                        countDownTimer.base = SystemClock.elapsedRealtime() - task.PauseOff
                        countDownTimer.start()
                    }else{
                        countDownTimer.stop()
                        task.PauseOff = SystemClock.elapsedRealtime() - countDownTimer.base
                        task.time = countDownTimer.text.toString()
                        //Update
                        Log.d("MainTest", "onBindViewHolder: $task")
                        myDBRoom.taskDao().updateTimeTask(Task
                            (task.id, task.name,task.description,task.PauseOff,task.time)
                        )
                    }
                    chronometer = countDownTimer
                    oldTask = task
                }else{
                    if(chronometer == countDownTimer){
                        if(running){
                            countDownTimer.base = SystemClock.elapsedRealtime() - task.PauseOff
                            countDownTimer.start()
                        }else{
                            countDownTimer.stop()
                            task.PauseOff = SystemClock.elapsedRealtime() - countDownTimer.base
                            task.time = countDownTimer.text.toString()
                            //Update
                            Log.d("MainTest", "onBindViewHolder1: $task")
                            myDBRoom.taskDao().updateTimeTask(Task
                                (task.id, task.name,task.description,task.PauseOff,task.time)
                            )
                        }
                    }else{
                        if(chronometer == null){
                            countDownTimer.base = SystemClock.elapsedRealtime() - task.PauseOff
                            countDownTimer.start()
                            chronometer = countDownTimer
                        }else{
                            chronometer?.stop()

                            //Update
                            oldTask!!.PauseOff = SystemClock.elapsedRealtime() - chronometer!!.base
                            oldTask!!.time = chronometer!!.text.toString()
                            activity.myDBRoom.taskDao().updateTimeTask(oldTask!!)
                            //-----------------

                            countDownTimer.base = SystemClock.elapsedRealtime() - task.PauseOff
                            countDownTimer.start()
                            oldTask = task
                            chronometer = countDownTimer
                        }
                    }
                }
            }
        }
    }
    override fun getItemCount() = taskList.size
}