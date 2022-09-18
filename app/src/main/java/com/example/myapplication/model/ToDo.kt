package com.example.myapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    var id:Int?,

    @ColumnInfo(name = "todo_title")
    val  title: String

)
