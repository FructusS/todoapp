package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapters.TodoListAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.ToDo
import com.example.myapplication.viewmodels.ToDoViewModel
import com.example.myapplication.viewmodels.ToDoViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val modelFactory = ToDoViewModelFactory(application)

        val  model = ViewModelProvider(this, modelFactory)[ToDoViewModel::class.java]
        val adapter = TodoListAdapter(TodoListAdapter.OnClickListener{
            todo -> editRemoveTodo(model, todo)
        })

        binding.recyclerview.adapter = adapter

        adapter.notifyDataSetChanged()
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        model.allToDo.observe(this) { todo ->
            // Update the cached copy of the words in the adapter.
            todo?.let { adapter.submitList(it) }
        }

        binding.fab.setOnClickListener {
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivity(intent)
        }


    }
    private fun editRemoveTodo(model: ToDoViewModel,todo :ToDo){
        val alertDialog = AlertDialog.Builder(this)
            .setItems(R.array.dialog_list) { dialog, which ->
                if (which == 0) {
//                    val intent = Intent(this@MainActivity, AddTodoActivity::class.java)
//                    intent.putExtra("tId", todo.id)
//                    intent.putExtra("title", todo.title)
//                    intent.putExtra("time", todo.time)
//                    intent.putExtra("isComplete", todo.isComplete)
                    intent.putExtra(ToDo::class.java.simpleName , todo )

                    startActivity(intent)
                } else {

                    model.removeTodo(todo)

                }
                dialog.dismiss()
            }
            .create()
        alertDialog.show()
    }

}




