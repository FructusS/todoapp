package com.example.myapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapters.TodoListAdapter
import com.example.myapplication.databinding.ActivityCompletedBinding
import com.example.myapplication.model.Todo
import com.example.myapplication.utils.SwipeToCompleteCallback
import com.example.myapplication.viewmodels.TodoViewModel
import com.example.myapplication.viewmodels.TodoViewModelFactory

class CompletedActivity : AppCompatActivity(), TodoListAdapter.ITodoListener {
    private lateinit var binding : ActivityCompletedBinding
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var adapter: TodoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCompletedBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val modelFactory = TodoViewModelFactory(application)

        todoViewModel = ViewModelProvider(this, modelFactory)[TodoViewModel::class.java]

        binding.recyclerviewTodo.layoutManager = LinearLayoutManager(this)
        adapter = TodoListAdapter(this)
        binding.recyclerviewTodo.adapter = adapter

        todoViewModel.getCompleteTodoList().observe(this) {
            adapter.setList(it)
        }

        ItemTouchHelper(SwipeToCompleteCallback(adapter,applicationContext,true)).attachToRecyclerView(binding.recyclerviewTodo)

    }

    override fun onItemComplete(item: Todo, position: Int) {
        todoViewModel.deleteTodo(item)
    }

    override fun onItemNotComplete(item: Todo, position: Int) {
        todoViewModel.notCompleteTodo(item)
    }

    override fun onLongClickItem(item: Todo) {
        editRemoveTodo(item)
    }

    private fun editRemoveTodo(item: Todo) {
        val alertDialog = AlertDialog.Builder(this)
            .setItems(R.array.dialog_list_completed_todo) { dialog, which ->
                if (which == 0) {
                    todoViewModel.deleteTodo(item)
                }
                dialog.dismiss()
            }
            .create()
        alertDialog.show()
    }
}