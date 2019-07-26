package com.nfd.nfdmobile.data

import androidx.room.*

@Dao
interface NFDAudioDao {
    @Insert
    fun insert(vararg texts: NFDText)

    @Query("SELECT * FROM texts WHERE type = :type")
    fun getAudiosByType(type: String?): List<NFDText>
}
