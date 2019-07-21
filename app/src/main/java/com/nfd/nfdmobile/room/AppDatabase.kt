package com.nfd.nfdmobile.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [TextEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val getTextDAO: TextDAO
}
