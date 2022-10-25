package com.example.myapplication

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapters.TodoListAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.Todo
import com.example.myapplication.utils.SwipeToCompleteCallback
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


        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        todoAdapter = TodoListAdapter(this)
        ItemTouchHelper(SwipeToCompleteCallback(todoAdapter,applicationContext,false)).attachToRecyclerView(binding.recyclerview)

        binding.recyclerview.adapter = todoAdapter


        todoViewModel.getTodoList().observe(this, Observer {
            todoAdapter.setList(it)
        })

        binding.fab.setOnClickListener {
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivity(intent)
        }




    }

    private fun editRemoveTodo( todo :Todo, position : Int){
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
                    todoViewModel.deleteTodo(todo)
                }
                dialog.dismiss()
            }
            .create()
        alertDialog.show()
    }

    override fun onItemComplete(item: Todo, position: Int) {
        todoViewModel.completeTodo(item)

    }

    override fun onItemNotComplete(item: Todo, position: Int) {
        todoViewModel.notcompleteTodo(item)
    }


    override fun onLongClickItem(item: Todo , position: Int) {
        editRemoveTodo(item,position)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.search -> true
            R.id.archived -> {
                val intent = Intent(this,CompletedActivity::class.java)
                this.startActivity(intent)
                return true

            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}




