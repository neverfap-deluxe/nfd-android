package com.nfd.nfdmobile.nfdtext

import androidx.room.*

@Dao
interface NFDTextDAO {
    @Insert
    fun insert(vararg texts: NFDTextEntity)

    @Query("SELECT * FROM texts WHERE type = :type")
    fun getTextsByType(type: String?): List<NFDTextEntity>

//    @Update
//    fun update(vararg texts: NFDTextEntity)
//
//    @Delete
//    fun delete(text: NFDTextEntity)


//    @Query("SELECT * FROM texts")
//    fun getTexts(): List<NFDTextEntity>

}
