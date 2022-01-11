package com.kaveri.byjutestapp.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Test::class), version = 1, exportSchema = false)
abstract class TestRoomDatabase : RoomDatabase() {

    abstract fun testDao(): TestDao

    companion object {
        private const val TEST_DATABASE: String = "Test_Database"
        private var ROOM_DB_INSTANCE: TestRoomDatabase? = null
        fun getDatabase(context: Context): TestRoomDatabase {
            return ROOM_DB_INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                TestRoomDatabase::class.java,
                TEST_DATABASE).build()
                ROOM_DB_INSTANCE = instance
                instance
            }
        }
    }
}