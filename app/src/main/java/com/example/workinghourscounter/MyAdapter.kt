package com.example.workinghourscounter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_list_fragment.view.*

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyHolder>() {

    private var timeList = emptyList<DataTime>()


    class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_list_fragment, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.itemView.start_time_work_hours.text = timeList[position].startTime
        holder.itemView.end_time_work.text = timeList[position].endTime

    }


    override fun getItemCount(): Int {
        return timeList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<DataTime>) {
        timeList = list
        notifyDataSetChanged()
    }
}

