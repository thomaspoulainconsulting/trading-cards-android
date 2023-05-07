package com.tpc.pokemontradingcards.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tpc.pokemontradingcards.data.model.CardSet
import kotlinx.coroutines.flow.Flow

@Dao
interface CardSetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cards: List<CardSet>)

    @Query("SELECT * FROM CardSet")
    fun getAllCardSets(): Flow<List<CardSet>>
}