package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
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
    private lateinit var list : List<Todo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCompletedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val modelFactory = TodoViewModelFactory(application)

        todoViewModel = ViewModelProvider(this, modelFactory)[TodoViewModel::class.java]

        binding.recyclerviewTodo.layoutManager = LinearLayoutManager(this)
        adapter = TodoListAdapter(this)
        binding.recyclerviewTodo.adapter = adapter

        todoViewModel.getCompleteTodoList().observe(this, Observer {
            adapter.setList(it)
        })
        list = listOf()

        ItemTouchHelper(SwipeToCompleteCallback(adapter,applicationContext,true)).attachToRecyclerView(binding.recyclerviewTodo)

    }

    override fun onItemComplete(item: Todo, position: Int) {
        todoViewModel.deleteTodo(item)
    }

    override fun onItemNotComplete(item: Todo, position: Int) {
        todoViewModel.notcompleteTodo(item)
    }

    override fun onLongClickItem(item: Todo, position: Int) {
        editRemoveTodo(item,position)
    }

    private fun editRemoveTodo(item: Todo, position: Int) {
        val alertDialog = AlertDialog.Builder(this)
            .setItems(R.array.dialog_list) { dialog, which ->
                if (which == 0) {

                    intent.putExtra("tId", item.id)
                    intent.putExtra("title", item.title)
                    intent.putExtra("time", item.time)
                    intent.putExtra("isComplete", item.isComplete)
                    intent.putExtra(Todo::class.java.simpleName , item )

                    startActivity(intent)
                } else {
                    todoViewModel.deleteTodo(item)
                }
                dialog.dismiss()
            }
            .create()
        alertDialog.show()
    }
}