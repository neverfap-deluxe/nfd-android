package com.nfd.nfdmobile

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nfd.nfdmobile.nfdtext.TextDAO
import com.nfd.nfdmobile.nfdtext.TextEntity


@Database(entities = [TextEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val getTextDAO: TextDAO
}
