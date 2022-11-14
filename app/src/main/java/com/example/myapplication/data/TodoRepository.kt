package com.example.myapplication.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.myapplication.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TodoRepository(application: Application) {
    private val  todoDao : TodoDao
    private val todoList: LiveData<List<Todo>>
    private val todoCompleteList: LiveData<List<Todo>>

    init {
        val db = AppDatabase.getDatabase(application.applicationContext)
        todoDao = db.todoDao()
        todoList = todoDao.getTodoList()
        todoCompleteList = todoDao.getCompleteTodoList()


    }
    fun saveTodo(todo: Todo) = runBlocking{
        this.launch(Dispatchers.IO) {
            todoDao.saveTodo(todo)
        }
    }
    fun updateTodo(todo: Todo) = runBlocking{
        this.launch(Dispatchers.IO) {
            todoDao.updateTodo(todo)
        }
    }
    fun deleteTodo(todo: Todo) = runBlocking{
        this.launch(Dispatchers.IO) {
            todoDao.deleteTodo(todo)
        }
    }
    fun getTodoList() : LiveData<List<Todo>> {
        return todoList
    }
    fun getCompleteTodoList() : LiveData<List<Todo>> {
        return todoCompleteList
    }


}