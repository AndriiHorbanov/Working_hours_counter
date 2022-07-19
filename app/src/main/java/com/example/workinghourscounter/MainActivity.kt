package com.example.workinghourscounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workinghourscounter.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

private lateinit var binding: ActivityMainBinding
private lateinit var adapter: MyAdapter
val dataList = ArrayList<DataTime>()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = MyAdapter()
        binding.recyclerviewView.layoutManager
        binding.recyclerviewView.adapter = adapter







        binding.button.setOnClickListener {
            if (binding.startWork.text.isNullOrEmpty()) {
                } else {
                dataList.add(DataTime(binding.startWork.text.toString(),binding.endWork.text.toString() ))
                    adapter.setList(dataList)
            }



        }

//    private fun addListItems(): String{
//        val saved  = binding.startWork.toString()
//        return  saved
//    }
    }
}