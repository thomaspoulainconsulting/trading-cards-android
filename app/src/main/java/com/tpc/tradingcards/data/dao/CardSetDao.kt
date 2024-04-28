package com.tpc.tradingcards.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tpc.tradingcards.data.model.CardSet
import com.tpc.tradingcards.data.model.TradingCardGame

@Dao
interface CardSetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sets: List<CardSet>)

    @Query("SELECT * FROM CardSet WHERE tradingCardGame=:tradingCardGame ORDER BY releaseDate")
    suspend fun get(tradingCardGame: TradingCardGame): List<CardSet>
}