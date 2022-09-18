package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapters.ToDoListAdapter
import com.example.myapplication.viewmodels.ToDoViewModel
import com.example.myapplication.viewmodels.ToDoViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var model: ToDoViewModel
    private lateinit var rv: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv = findViewById(R.id.rv)
        val modelfactory= ToDoViewModelFactory(application);

        model = ViewModelProvider(this,modelfactory).get(ToDoViewModel::class.java)
        rv.layoutManager = LinearLayoutManager(this)
        model.allEmplyees.observe(this, Observer{ todo->
            // Data bind the recycler view
            rv.adapter = ToDoListAdapter(todo)
        })

    }
}