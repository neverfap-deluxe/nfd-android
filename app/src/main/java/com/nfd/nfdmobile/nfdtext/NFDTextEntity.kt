package com.nfd.nfdmobile.nfdtext

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "texts")

class NFDTextEntity {
    @PrimaryKey
    @NonNull
    private val title: String? = null
    private val date: String? = null
    private val content: String? = null
    private val type: String? = null
}
