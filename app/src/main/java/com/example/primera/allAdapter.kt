package com.example.primera

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class allAdapter ( private val todo: List<Todos>) : RecyclerView.Adapter<allAdapter.ViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val all = todo[position]
        holder.userId.text = all.userId.toString()
        holder.id.text = all.id.toString()
        holder.title.text = all.title
        holder.completed.text = all.completed.toString()

    }

    override fun getItemCount() = todo.size

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val userId: TextView = itemView.findViewById(R.id.txtuserid)
        val id: TextView = itemView.findViewById(R.id.txtid)
        val title: TextView = itemView.findViewById(R.id.txttitle)
        val completed: TextView = itemView.findViewById(R.id.txtcompleted)

    }
}