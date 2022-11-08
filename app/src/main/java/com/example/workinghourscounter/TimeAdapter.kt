package com.example.workinghourscounter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workinghourscounter.databinding.ViewListFragmentBinding

class TimeAdapter : RecyclerView.Adapter<TimeAdapter.TimeHolder>() {

    var onDeleteClick: ((TimeUI) -> Unit)? = null

    private var timeList = emptyList<TimeUI>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeHolder {
        val binding =
            ViewListFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeHolder(binding, onDeleteClick)
    }

    override fun onBindViewHolder(holder: TimeHolder, position: Int) {
        holder.bind(timeList[position])
    }

    override fun getItemCount(): Int {
        return timeList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<TimeUI>) {
        timeList = list
        notifyDataSetChanged()
    }

    class TimeHolder(
        private val binding: ViewListFragmentBinding,
        private val onDeleteClick: ((TimeUI) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(timeUI: TimeUI) {
            val context = itemView.context
            with(timeUI) {
                binding.apply {
                    startTimeWorkHours.text =
                        context.getString(R.string.holder_start_time, startHours, startMinutes)
                    endTimeWork.text =
                        context.getString(R.string.holder_end_time, endHours, endMinutes)
                    dayHours.text =
                        context.getString(R.string.holder_total_hours, totalHours, totalMinutes)
                    dayEarnings.text = context.getString(R.string.holder_total_earning, earning)
                    delete.setOnClickListener {
                        onDeleteClick?.invoke(this@with)
                    }
                }
            }
        }
    }
}


