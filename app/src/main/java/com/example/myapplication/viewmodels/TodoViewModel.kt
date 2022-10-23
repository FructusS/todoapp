package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.model.Todo

class TodoViewModel(application: Application): ViewModel() {
    private val db: AppDatabase = AppDatabase.getDatabase(application)
    internal val allTodo : LiveData<List<Todo>> = db.todoDao().getAll()

    fun removeTodo(todo: Todo){
        db.todoDao().removeTodo(todo)

    }

}