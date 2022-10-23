package com.example.myapplication

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.CheckBox
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapters.TodoListAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.Todo
import com.example.myapplication.viewmodels.TodoViewModel
import com.example.myapplication.viewmodels.TodoViewModelFactory
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() , TodoListAdapter.ITodoListener{
    private val TAG = "123"
    private lateinit var binding : ActivityMainBinding
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var searchView: SearchView
    private lateinit var todoAdapter: TodoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val modelFactory = TodoViewModelFactory(application)

        todoViewModel = ViewModelProvider(this, modelFactory)[TodoViewModel::class.java]

        todoAdapter = TodoListAdapter(this)


        binding.recyclerview.adapter = todoAdapter

        todoAdapter.notifyDataSetChanged()
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        todoViewModel.allTodo.observe(this) { todo ->

            todo?.let { todoAdapter.setList(it) }
        }

        binding.fab.setOnClickListener {
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivity(intent)
        }




    }

    private fun editRemoveTodo( todo :Todo){
        val alertDialog = AlertDialog.Builder(this)
            .setItems(R.array.dialog_list) { dialog, which ->
                if (which == 0) {

                    intent.putExtra("tId", todo.id)
                    intent.putExtra("title", todo.title)
                    intent.putExtra("time", todo.time)
                    intent.putExtra("isComplete", todo.isComplete)
                    intent.putExtra(Todo::class.java.simpleName , todo )

                    startActivity(intent)
                } else {

                    todoViewModel.removeTodo(todo)

                }
                dialog.dismiss()
            }
            .create()
        alertDialog.show()
    }



    override fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked


                    if (checked) {
                        Snackbar.make(view, "Похоже на пустое задание", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show()
                    } else {
                        Snackbar.make(view, "Похоже на пустое задание", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show()
                    }


            }
        }


    override fun onLongClickItem(item: Todo) {
        editRemoveTodo(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)


        searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                todoAdapter.filter.filter(query)
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                todoAdapter.filter.filter(newText)
                return false
            }

        })
        return true
    }

}




