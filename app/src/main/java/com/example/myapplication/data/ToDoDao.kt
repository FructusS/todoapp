package com.example.myapplication.data


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.model.ToDo

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): LiveData<List<ToDo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(todo: ToDo)
}