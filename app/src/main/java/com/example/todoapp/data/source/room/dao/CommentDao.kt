package com.example.todoapp.data.source.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.todoapp.data.source.room.entity.CommentData

@Dao
interface CommentDao : BaseDao<CommentData> {
    @Query("Select * from CommentData")
    fun getAll(): List<CommentData>

    @Query("Delete from CommentData where groupId=:id")
    fun deleteAllByGroup(id: Long)

    @Query("Select * From CommentData where groupId=:groupId")
    fun getCommentsByGroupId(groupId: Long): List<CommentData>
}