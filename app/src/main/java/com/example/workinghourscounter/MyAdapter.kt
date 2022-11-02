package com.example.workinghourscounter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_list_fragment.view.*

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyHolder>() {

    private var timeList = emptyList<TimeUI>()

    var onDeleteClick: ((TimeUI) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_list_fragment, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val timeUI = timeList[position]
        holder.itemView.start_time_work_hours.text = timeUI.startTime
        holder.itemView.end_time_work.text = timeUI.endTime
        holder.itemView.delete.setOnClickListener {
            onDeleteClick?.invoke(timeUI)
        }
    }

    override fun getItemCount(): Int {
        return timeList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<TimeUI>) {
        timeList = list
        notifyDataSetChanged()
    }


    class MyHolder(view: View) : RecyclerView.ViewHolder(view)
}


