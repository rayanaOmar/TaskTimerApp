package com.example.tasktimerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var addBtn: Button
    lateinit var viewBtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addBtn = findViewById(R.id.btAdd)
        addBtn.setOnClickListener {
            val intent = Intent(this,AddNewTask::class.java)
            startActivity(intent)
            finish()
        }

        viewBtn = findViewById(R.id.buttonS)
        viewBtn.setOnClickListener {
            val intent = Intent(this,ViewTaskRV::class.java)
            startActivity(intent)
        }
    }
}