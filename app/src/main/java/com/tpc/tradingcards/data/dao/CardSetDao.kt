package com.tpc.tradingcards.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tpc.tradingcards.data.model.CardSet
import com.tpc.tradingcards.data.model.CardType
import kotlinx.coroutines.flow.Flow

@Dao
interface CardSetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sets: List<CardSet>)

    @Query("SELECT * FROM CardSet")
    fun getAllCardSets(): Flow<List<CardSet>>

    @Query("SELECT * FROM CardSet WHERE cardType=:cardType")
    fun getCardSetsWithType(cardType: CardType): Flow<List<CardSet>>
}