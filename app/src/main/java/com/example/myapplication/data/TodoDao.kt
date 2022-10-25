package com.example.myapplication.data


import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.model.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo where isComplete = 0 ")
    fun getTodoList(): LiveData<List<Todo>>


    @Query("SELECT * FROM todo where isComplete = 1")
    fun getCompleteTodoList(): LiveData<List<Todo>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveTodo(todo: Todo)



    @Update
    fun updateTodo(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)
}