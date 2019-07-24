package com.nfd.nfdmobile.data

import androidx.room.*

@Dao
interface NFDTextDao {
    @Insert
    fun insert(vararg texts: NFDText)

    @Query("SELECT * FROM texts WHERE type = :type")
    fun getTextsByType(type: String?): List<NFDText>
}
