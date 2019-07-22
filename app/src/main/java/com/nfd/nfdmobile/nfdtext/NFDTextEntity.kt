package com.nfd.nfdmobile.nfdtext

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "texts")
data class NFDTextEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "type") val type: String?
)
