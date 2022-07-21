package com.example.workinghourscounter


import android.app.AlertDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.workinghourscounter.databinding.ActivityMainBinding
import com.example.workinghourscounter.viewModel.MyViewModel
import java.util.*
import kotlin.collections.ArrayList
import android.text.format.DateFormat.is24HourFormat
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.activity.viewModels
import kotlinx.android.synthetic.main.warning_dialog.view.*


private lateinit var binding: ActivityMainBinding
private lateinit var adapter: MyAdapter
val dataList = ArrayList<DataTime>()
val calendar = Calendar.getInstance()
val hour = calendar.get(Calendar.HOUR_OF_DAY)
val minet = calendar.get(Calendar.MINUTE)

class MainActivity : AppCompatActivity() {
    val viewModel: MyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = MyAdapter()
        binding.recyclerviewView.layoutManager
        binding.recyclerviewView.adapter = adapter
        binding.startTime.setOnClickListener {
            openTinePicker(true)
        }

        binding.endTime.setOnClickListener {
            openTinePicker(false)
        }

        binding.button.setOnClickListener {
            if (viewModel.hourEnd.value != null && viewModel.hourStart.value != null && viewModel.minutesEnd.value != null && viewModel.minutesStart.value != null) {
                dataList.add(
                    DataTime(
                        "${if(viewModel.hourStart.value!! < 10){" 0"}else{}}" + viewModel.hourStart.value.toString() + " : ${if(viewModel.minutesStart.value!! < 10){" 0"}else{}}"  + viewModel.minutesStart.value.toString(),
                        "${if(viewModel.hourEnd.value!! < 10){" 0"}else{}}" + viewModel.hourEnd.value.toString() + " : ${if(viewModel.minutesEnd.value!! < 10){" 0"}else{}}"  + viewModel.minutesEnd.value.toString()
                    )
                )
                adapter.setList(dataList)
            } else {
                val warning = View.inflate(this@MainActivity, R.layout.warning_dialog, null)
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setView(warning)
                val dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                warning.ok_button.setOnClickListener {
                    dialog.dismiss()
                }
            }

        }


//    private fun addListItems(): String{
//        val saved  = binding.startWork.toString()
//        return  saved
//    }
    }


    fun openTinePicker(flag: Boolean) {
        val picker =
            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                viewModel.setTime(hourOfDay, minute, flag)
            }, hour, minet, is24HourFormat(this))
        picker.show()
    }

}

