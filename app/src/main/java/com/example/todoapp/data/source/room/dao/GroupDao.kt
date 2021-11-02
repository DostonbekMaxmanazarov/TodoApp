package com.example.todoapp.data.source.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.todoapp.data.source.room.entity.GroupData

@Dao
interface GroupDao : BaseDao<GroupData> {
    @Query("Select * from GroupData")
    fun getAll(): List<GroupData>
}