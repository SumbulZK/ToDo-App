package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.content.Intent as Intent
class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        // Get the Intent that started this activity and extract the string
        val extras = intent.extras
        var task = extras?.getString("key")
     //   var position =extras?.getString("position")
        findViewById<EditText>(R.id.editTask).setText(task)



        findViewById<Button>(R.id.button2).setOnClickListener{

            var updated_task = findViewById<EditText>(R.id.editTask).text.toString()

            val intent = Intent(this@MainActivity2, MainActivity::class.java)
            intent.putExtra("task",updated_task)


            setResult(RESULT_OK, intent) // set result code and bundle data for response
            finish() // closes the activity, pass data to parent
        }


    }




}