package com.example.selfdrvnassessment.roomDatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.selfdrvnassessment.roomDatabase.database.dao.AvengersListDao
import com.example.selfdrvnassessment.roomDatabase.database.entity.AvengersListEntity

@Database(
        entities = [AvengersListEntity::class],
        version = 1,
        exportSchema = false
)
abstract class AppDatabase:RoomDatabase() {
    abstract fun avengerDao(): AvengersListDao
    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private const val DB_NAME = "AVENGERS_LIST_ROOM_DATABASE"

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildRoomDB(context).also { instance = it }
            }
        }

        private fun buildRoomDB(context: Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DB_NAME
                )
                .fallbackToDestructiveMigration()
                .build()
    }

}