package com.example.myapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "todo")

data class Todo(
    @PrimaryKey(autoGenerate = true) var id:Int?,
    @ColumnInfo(name = "title") var title: String,

    @ColumnInfo(name = "isComplete") var isComplete: Boolean = false,
    @ColumnInfo(name = "time")  var  time:  Long?



) : Serializable
