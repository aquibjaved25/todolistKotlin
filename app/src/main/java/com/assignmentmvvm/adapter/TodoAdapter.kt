package com.retrofit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignmentmvvm.R
import com.assignmentmvvm.view.MainActivity
import com.retrofit.model.TodoDataModel
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdapter(
    private val context: MainActivity,
    private val chaptersList: ArrayList<TodoDataModel>
) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return chaptersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.UserId?.text = chaptersList.get(position).userId.toString()
        holder.Id?.text = chaptersList.get(position).id.toString()
        holder.Title?.text = chaptersList.get(position).title
        holder.Completed?.text = chaptersList.get(position).completed.toString()

        holder.itemView.setOnClickListener {
            //Toast.makeText(context, chaptersList.get(position), Toast.LENGTH_LONG).show()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val UserId = view.text_user_id
        val Id = view.text_id
        val Title = view.text_title
        val Completed = view.text_completed
    }
}
