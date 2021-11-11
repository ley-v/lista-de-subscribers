package com.example.a02crudcomroom.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a02crudcomroom.data.db.dao.SubscriberDao
import com.example.a02crudcomroom.data.db.entity.SubscriberEntity

@Database(entities = [SubscriberEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val subscriberDao: SubscriberDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE?: synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "app_database"
                    )
                        .build()
                }
                return INSTANCE as AppDatabase
            }
        }
    }
}