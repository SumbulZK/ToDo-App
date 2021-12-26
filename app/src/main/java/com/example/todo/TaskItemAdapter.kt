package com.example.todo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent as Intent
/**
 * A bridge that tells the recylerView how to display the data we give it
 */
class TaskItemAdapter(val listOfItems: List<String>, val longClickListener : OnLongClickListener, val shortClickListener: OnShortClickListener): RecyclerView.Adapter<TaskItemAdapter.ViewHolder>(){

    interface OnLongClickListener{

        fun onItemLongClicked(position: Int)
    }


    interface  OnShortClickListener{
        fun onItemShortClicked(position: Int)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //todo
        val textView: TextView

        init{
            textView = itemView.findViewById(android.R.id.text1)
            itemView.setOnLongClickListener{
               longClickListener.onItemLongClicked(adapterPosition)
                true
            }

            itemView.setOnClickListener(){
                shortClickListener.onItemShortClicked(adapterPosition)
                true
            }


        }

    }
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listOfItems.get(position)
        holder.textView.text = item
    }

    override fun getItemCount(): Int {
       return listOfItems.size
    }
}