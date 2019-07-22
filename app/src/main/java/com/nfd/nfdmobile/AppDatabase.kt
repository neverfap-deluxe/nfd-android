package com.nfd.nfdmobile

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nfd.nfdmobile.nfdtext.NFDTextDAO
import com.nfd.nfdmobile.nfdtext.NFDTextEntity




@Database(entities = [NFDTextEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val getTextDAO(): NFDTextDAO
}

