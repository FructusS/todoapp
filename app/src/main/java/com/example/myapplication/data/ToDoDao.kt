package com.example.myapplication.data


import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.model.ToDo

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo")
//    fun getAll(): LiveData<List<ToDo>>
   fun getAll(): LiveData<List<ToDo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(todo: ToDo)

    @Query("SELECT * FROM todo where id=:uid")
    fun getTodoItem(uid:Int) : ToDo

    @Update
    fun updateTodo(todo: ToDo)

    @Delete
    fun removeTodo(todo: ToDo)
}