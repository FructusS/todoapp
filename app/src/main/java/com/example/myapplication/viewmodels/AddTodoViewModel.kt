package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.model.Todo

class AddTodoViewModel(application: Application): ViewModel() {
    private val db: AppDatabase = AppDatabase.getDatabase(application)
    internal val allTodo : LiveData<List<Todo>> = db.todoDao().getAll()

    fun insert(todo: Todo){
        db.todoDao().insert(todo)
    }
    fun update(todo: Todo){
        db.todoDao().updateTodo(todo)
    }


    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0

}
class AddTodoViewModelFactory(private var application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddTodoViewModel::class.java)) {
            return AddTodoViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
