package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.AppDatabase
import com.example.myapplication.model.ToDo

class ToDoViewModel(var application: Application): ViewModel() {
    private val db: AppDatabase = AppDatabase.getDatabase(application)
    internal val allEmplyees : LiveData<List<ToDo>> = db.todoDao().getAll()

    fun insert(todo: ToDo){
        db.todoDao().insert(todo)
    }
}