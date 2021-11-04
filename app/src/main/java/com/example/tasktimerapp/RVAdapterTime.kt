package com.example.tasktimerapp
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.taskinfo_row.view.*


class RVAdapterTime (private val activity: TaskWithTime, private var taskList:List<Task>): RecyclerView.Adapter<RVAdapterTime.ItemViewHolder>() {

        class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            return ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.taskinfo_row,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

            val task = taskList[position]

            holder.itemView.apply {
                tvCardTime.text = "${task.name}\n \n${task.time}"


            }
        }
        override fun getItemCount() = taskList.size

}