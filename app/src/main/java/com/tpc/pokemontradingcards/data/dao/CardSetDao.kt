package com.tpc.pokemontradingcards.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tpc.pokemontradingcards.data.model.CardSet
import com.tpc.pokemontradingcards.data.model.CardType
import kotlinx.coroutines.flow.Flow

@Dao
interface CardSetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sets: List<CardSet>)

    @Query("SELECT * FROM CardSet")
    fun getAllCardSets(): Flow<List<CardSet>>

    @Query("SELECT * FROM CardSet WHERE cardType=:cardType")
    fun getCardSetsWithType(cardType: CardType): List<CardSet>
}