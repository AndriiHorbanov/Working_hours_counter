package com.example.workinghourscounter

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.workinghourscounter.databinding.ActivityMainBinding
import com.example.workinghourscounter.databinding.WarningDialogBinding
import com.example.workinghourscounter.viewModel.TimeViewModel
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    private val adapter by lazy { TimeAdapter() }
    private lateinit var binding: ActivityMainBinding
    private val viewModel: TimeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()

        setContentView(binding.root)

        setAdapter()

        observeState()

//        observeTotalEarning()

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
                viewModel.state.collect { state ->
                    adapter.setList(state.timeList)
                    setTotalEarning(state.totalEarning)
                    setTotalTime(state.totalHours, state.totalMinutes)
                    if (state.errorMessage.isNotEmpty()) showWarningDialog()
                }
            }

        }
    }

    fun setTotalTime(totalHours: Int, totalMinutes: Int){
        binding.hours.text = getString(R.string.total_time, totalHours, totalMinutes)
    }

    fun setTotalEarning(totalEarning: Double) {
        binding.money.text = getString(R.string.total_earned_money, totalEarning)
    }

    private fun showWarningDialog() {
        val dialogBinding = WarningDialogBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setView(dialogBinding.root)
        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialogBinding.okButton.setOnClickListener {
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
            }, hour, pickerMinutes, true)
        picker.show()
    }
}

