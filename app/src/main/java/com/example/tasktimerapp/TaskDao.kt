package com.example.tasktimerapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tasktimerapp.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM Task ORDER BY id  ")
    fun getAllTasks(): List<Task>

    @Insert
    fun insertTask(input: Task)
    @Update
    fun updateTimeTask(input: Task)
}