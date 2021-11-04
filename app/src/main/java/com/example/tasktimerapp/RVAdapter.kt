package com.example.tasktimerapp

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Update
import kotlinx.android.synthetic.main.item_row.view.*

class RVAdapter (private val activity: ViewTaskRV, private var taskList:List<Task>): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {

    private var chronometer: Chronometer?= null //Like runningTask
    private var running = false
    private var pauseOffset: Long = 0

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
            taskName.text = " \n${task.name}"
            taskDescription.text = "${task.description}\n\n${task.time} "
            linear.addView(countDownTimer)
            var taskpauseOffset = taskList[position].PauseOff
            //onClick
            cardView.setOnClickListener {
                taskDescription.text = "${task.description}\n "
                //taskList[position].time =chronometer!!.text.toString()

                if(!running && chronometer == null){
                    countDownTimer.isVisible = true
                    countDownTimer.compoundDrawablePadding
                    countDownTimer.base = SystemClock.elapsedRealtime() - taskpauseOffset
                    countDownTimer.start()
                    chronometer = countDownTimer
                    running = true

                }else{
                    chronometer?.stop()
                    countDownTimer.isVisible = true
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer!!.base
                    task.time = chronometer!!.text.toString()
                    countDownTimer.start()
                    chronometer = countDownTimer

                    //Update
                    myDBRoom.taskDao().updateTimeTask(Task
                        (task.id, task.name,task.description,pauseOffset,task.time)
                    )

                }
            }
        }
    }
    override fun getItemCount() = taskList.size
}