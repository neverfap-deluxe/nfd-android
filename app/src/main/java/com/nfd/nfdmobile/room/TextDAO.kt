package com.nfd.nfdmobile.room

import androidx.room.*


@Dao
interface TextDAO {
    @Insert
    fun insert(vararg texts: TextEntity)

    @Update
    fun update(vararg texts: TextEntity)

    @Delete
    fun delete(text: TextEntity)
}

@Query("SELECT * FROM texts")
fun getTexts(): List<TextEntity>

@Query("SELECT * FROM texts WHERE id = :id")
fun getTextById(id: Long?): TextEntity
