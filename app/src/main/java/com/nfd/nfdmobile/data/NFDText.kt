package com.nfd.nfdmobile.data

import android.content.Context
import android.widget.ListView
import androidx.room.Room
import com.nfd.nfdmobile.AppDatabase
import com.nfd.nfdmobile.services.ContentAPIService
import com.nfd.nfdmobile.services.NFDArticlesData
import com.nfd.nfdmobile.services.NFDPracticesData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

package com.nfd.nfdmobile.nfdtext

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "texts")
data class NFDText(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "type") val type: String?
)