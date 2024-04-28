package com.tpc.tradingcards.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.tpc.tradingcards.data.model.CardType
import com.tpc.tradingcards.data.model.TradingCardGame

@Dao
interface CardTypeDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(types: List<CardType>)

    @Query("SELECT * FROM CardType WHERE tradingCardGame=:tradingCardGame")
    suspend fun get(tradingCardGame: TradingCardGame): List<CardType>

    @Update(CardType::class, onConflict = REPLACE)
    suspend fun update(cardType: CardType)
}