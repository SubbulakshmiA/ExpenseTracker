package com.example.project_expense_v01

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Item::class], version = 1, exportSchema = false)
//abstract class and extend
abstract class AppDatabase : RoomDatabase() {
    // 4 abstract and return dao
    abstract fun itemDao(): ItemDao

    //5 singleton
    companion object {
        var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            // 6 acquiring an instance of RoomDb builder
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "item3.db"
                    )
                        .allowMainThreadQueries().build()
                }
            }

            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }


}