package com.example.workinghourscounter


import android.app.AlertDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.workinghourscounter.databinding.ActivityMainBinding
import com.example.workinghourscounter.viewModel.MyViewModel
import kotlinx.android.synthetic.main.warning_dialog.view.*
import kotlinx.coroutines.launch
import java.util.*

val calendar = Calendar.getInstance()
val hour = calendar.get(Calendar.HOUR_OF_DAY)
val minet = calendar.get(Calendar.MINUTE)

class MainActivity : AppCompatActivity() {
    private val viewModel: MyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = MyAdapter()
        binding.recyclerviewView.adapter = adapter

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{
                    adapter.setList(it.timeList)
                }
            }

        }

        binding.startTime.setOnClickListener {
            openTimePicker { hours, minutes ->
                viewModel.setStartTime(hours, minutes)
            }
        }

        binding.endTime.setOnClickListener {
            openTimePicker { hours, minutes ->
                viewModel.setEndTime(hours, minutes)
            }
        }

        binding.saveButton.setOnClickListener {
            viewModel.saveTime()
        }
    }

    private fun showWarningDialog() {
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


    private fun openTimePicker(onTimeSet: (Int, Int) -> Unit) {
        val picker =
            TimePickerDialog(this, { view, hourOfDay, minute ->
                onTimeSet(hourOfDay, minute)
            }, hour, minet, is24HourFormat(this))
        picker.show()
    }

}

