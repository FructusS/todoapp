package com.example.myapplication.data


import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.model.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
//    fun getAll(): LiveData<List<Todo>>
   fun getAll(): LiveData<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(todo: Todo)

    @Query("SELECT * FROM todo where id=:uid")
    fun getTodoItem(uid:Int) : Todo

    @Update
    fun updateTodo(todo: Todo)

    @Delete
    fun removeTodo(todo: Todo)
}