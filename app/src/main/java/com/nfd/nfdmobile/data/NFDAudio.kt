package com.nfd.nfdmobile.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "texts")
data class NFDAudio(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "mp3Url") val mp3Url: String?,
    @ColumnInfo(name = "type") val type: String?
)