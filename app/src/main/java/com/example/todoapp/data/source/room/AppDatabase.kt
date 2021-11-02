package com.example.todoapp.data.source.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.data.source.room.dao.CommentDao
import com.example.todoapp.data.source.room.dao.GroupDao
import com.example.todoapp.data.source.room.entity.CommentData
import com.example.todoapp.data.source.room.entity.GroupData

@Database(entities = [GroupData::class, CommentData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun groupDao(): GroupDao
    abstract fun commentDao(): CommentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabas(context: Context): AppDatabase {
            val templInstance = INSTANCE
            if (templInstance != null) {
                return templInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "appdatabas"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}