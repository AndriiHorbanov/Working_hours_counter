package com.example.workinghourscounter

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

class MainActivity : AppCompatActivity() {

    private val adapter by lazy { MyAdapter() }
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()

        setContentView(binding.root)

        setAdapter()

        observeState()

        setStartTimeListener()

        setEndTimeListener()

        setRateListener()

        setSaveTimeListener()

    }


    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    private fun setAdapter() {
        binding.recyclerviewView.adapter = adapter
        adapter.onDeleteClick = { viewModel.deleteTime(it) }
    }

    private fun setStartTimeListener() {
        binding.startTime.setOnClickListener {
            openTimePicker { hours, minutes ->
                viewModel.setStartTime(hours, minutes)
            }
        }
    }

    private fun setEndTimeListener() {
        binding.endTime.setOnClickListener {
            openTimePicker { hours, minutes ->
                viewModel.setEndTime(hours, minutes)
            }
        }
    }


    private fun setRateListener() {
        binding.rate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    viewModel.setRate(it.toString())
                }
            }
        })
    }

    private fun setSaveTimeListener() {
        binding.saveButton.setOnClickListener {
            viewModel.saveTime()

        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    adapter.setList(it.timeList)
                    if (it.errorMessage.isNotEmpty()) showWarningDialog()
                }
            }

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
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val pickerMinutes = calendar.get(Calendar.MINUTE)
        val picker =
            TimePickerDialog(this, { _, hourOfDay, minutes ->
                onTimeSet(hourOfDay, minutes)
            }, hour, pickerMinutes, is24HourFormat(this))
        picker.show()
    }

    //state
    //AM

}

