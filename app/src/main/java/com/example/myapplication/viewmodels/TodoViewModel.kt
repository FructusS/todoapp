package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.TodoRepository
import com.example.myapplication.model.Todo

class TodoViewModel(application: Application): AndroidViewModel(application) {

    private val repository : TodoRepository = TodoRepository(application)

    fun deleteTodo(todo: Todo){
        repository.deleteTodo(todo)

    }

    fun getTodoList() :LiveData<List<Todo>>{
        return  repository.getTodoList()
    }
    fun getCompleteTodoList() :LiveData<List<Todo>>{
        return  repository.getCompleteTodoList()
    }

    fun saveTodo(todo: Todo){
        repository.saveTodo(todo)
    }

    fun completeTodo(todo: Todo){
        todo.isComplete = true
        repository.updateTodo(todo)
    }
    fun notcompleteTodo(todo: Todo){
        todo.isComplete = false
        repository.updateTodo(todo)
    }
}