package com.tpc.tradingcards.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.tpc.tradingcards.data.model.Card
import com.tpc.tradingcards.data.model.TradingCardGame
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(cards: List<Card>)

    @Query("SELECT * FROM Card WHERE tradingCardGame=:tradingCardGame AND idSet=:idSet AND supertype IN (SELECT name FROM CardType WHERE isSelected = 1 AND name=supertype) ORDER BY number")
    fun get(tradingCardGame: TradingCardGame, idSet: String): Flow<List<Card>>
}