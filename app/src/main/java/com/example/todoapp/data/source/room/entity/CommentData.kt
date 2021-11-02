package com.example.todoapp.data.source.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "commentdata")
data class CommentData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String = "",
    var group: String = "",
    var groupId: Long = 0
)