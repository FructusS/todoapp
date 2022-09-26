package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.model.ToDo

class ToDoViewModel(application: Application): ViewModel() {
    private val db: AppDatabase = AppDatabase.getDatabase(application)
    internal val allToDo : LiveData<List<ToDo>> = db.todoDao().getAll()

    fun insert(todo: ToDo){
        db.todoDao().insert(todo)
    }
    fun removeTodo(todo: ToDo){
        db.todoDao().removeTodo(todo)

    }
    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0
}