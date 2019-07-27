package com.nfd.nfdmobile.data

import androidx.room.*

@Dao
interface NFDAudioDao {
    @Insert
    fun insert(vararg audios: NFDAudio)

    @Query("SELECT * FROM audios WHERE type = :type")
    fun getAudiosByType(type: String?): MutableList<NFDAudio>
}
