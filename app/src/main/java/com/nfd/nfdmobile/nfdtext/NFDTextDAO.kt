package com.nfd.nfdmobile.nfdtext

import androidx.room.*
import androidx.room.OnConflictStrategy

@Dao
interface NFDTextDAO {
    @Insert
    fun insert(vararg texts: NFDTextEntity)

    @Update
    fun update(vararg texts: NFDTextEntity)

    @Delete
    fun delete(text: NFDTextEntity)
}


@Query("SELECT * FROM texts")
fun getTexts(): List<NFDTextEntity>

@Query("SELECT * FROM texts WHERE id = :id")
fun getTextById(id: Long?): NFDTextEntity

@Query("SELECT * FROM texts WHERE type = :type")
fun getTextByType(type: String?): NFDTextEntity

@Insert(onConflict = OnConflictStrategy.REPLACE)
fun addDevicePreferences(difficultType: NFDTextEntity)
