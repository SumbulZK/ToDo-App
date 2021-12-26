
package com.example.todo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.IntentSenderRequest
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.lang.NullPointerException
import java.nio.charset.Charset
import java.nio.file.attribute.PosixFileAttributeView
import android.content.Intent as Intent
import com.example.todo.MainActivity2 as MainActivity2
import android.R.string.no
import android.app.Activity
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts


class MainActivity : AppCompatActivity() {

    lateinit var adapter:  TaskItemAdapter
    var editPosition: Int  = 0


    var listOfTasks = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
              // remove item from list
                listOfTasks.removeAt(position)
                adapter.notifyDataSetChanged()
             // notify the adapter

                saveItems()

             }


        }

        var launchSomeActivity = this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val extras = data?.extras

                if (extras != null) {
                    val updated_task = extras.getString("task").toString()

                   // Log.i("sumbul", updated_task)
                   // Log.i("sumbul", editPosition.toString())

                    listOfTasks[editPosition] = updated_task
                    adapter.notifyDataSetChanged()
                    saveItems()
                }


            }
        }

        val onShortClickListener = object : TaskItemAdapter.OnShortClickListener{
            override fun onItemShortClicked(position: Int) {
                //get text from the position
                var task = listOfTasks.get(position);

                //open pop up with editable text of position item and ok button

                val intent = Intent(this@MainActivity, MainActivity2::class.java)
                intent.putExtra("key",task)
                intent.putExtra("position", position)
                editPosition = position
                launchSomeActivity.launch(intent)


            }


        }



        loadItems()
        //look up recycler view in the layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener, onShortClickListener)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        findViewById<Button>(R.id.button).setOnClickListener{
            //grab the text that the user has inputted
            //add to list of tasks
            //clear field
            var field = findViewById<EditText>(R.id.addTaskView)
            var userinput = field.text.toString()
            listOfTasks.add(userinput)
            adapter.notifyItemInserted(listOfTasks.size-1)

            field.setText("")
            saveItems()
        }







    }

    //save data user has on the app by writing and reading a file
    // create a method to get the data file we need
    fun getDataFile() : File {
        return File(filesDir, "data.txt")
    }

    //Load the items by reading every line in the data file
    fun loadItems() {
        try {
            listOfTasks =
                org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }
        catch(ioExeption :IOException){
            ioExeption.printStackTrace()
        }
    }

    // Save items by writing them into a data file
    fun saveItems(){
     try{
        org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTasks)
     }
     catch (ioExeption : IOException){
         ioExeption.printStackTrace()
     }

    }
}