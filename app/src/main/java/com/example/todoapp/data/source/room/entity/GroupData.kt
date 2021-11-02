package com.example.todoapp.data.source.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "groupdata")
data class GroupData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String = "",
    var degreeName: String = "",
    var degreeColor: String = ""
)