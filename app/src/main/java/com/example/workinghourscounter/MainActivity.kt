package com.example.workinghourscounter


import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import com.example.workinghourscounter.databinding.ActivityMainBinding
import com.example.workinghourscounter.viewModel.MyViewModel
import java.util.*
import kotlin.collections.ArrayList
import android.text.format.DateFormat.is24HourFormat
import androidx.fragment.app.FragmentManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat


private lateinit var binding: ActivityMainBinding
private lateinit var adapter: MyAdapter
val dataList = ArrayList<DataTime>()
private lateinit var viewModel: MyViewModel
val calendar = Calendar.getInstance()
val hour = calendar.get(Calendar.HOUR_OF_DAY)
val minet = calendar.get(Calendar.MINUTE)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = MyAdapter()
        binding.recyclerviewView.layoutManager
        binding.recyclerviewView.adapter = adapter
        binding.startTime.setOnClickListener {
            openTinePicker()
        }

        binding.endTime.setOnClickListener{
            openTinePicker()
        }
//        binding.button.setOnClickListener {
//            if (binding.startWork.text.isNullOrEmpty()) {
//            } else {
//                dataList.add(
//                    DataTime(
//                        binding.startWork.text.toString(),
//                        binding.endWork.text.toString()
//                    )
//                )
//                adapter.setList(dataList)
//            }
//
//
//        }


//    private fun addListItems(): String{
//        val saved  = binding.startWork.toString()
//        return  saved
//    }
    }

    fun openTinePicker() {
        val picker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        }, hour, minet, false)
        picker.show()
    }

}

