package com.tpc.tradingcards.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.tpc.tradingcards.data.model.Card
import com.tpc.tradingcards.data.model.CardType
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(cards: List<Card>)

    @Query("SELECT * FROM Card WHERE cardType=:cardType AND idSet=:idSet ORDER BY number")
    fun getAllCards(cardType: CardType, idSet: String): Flow<List<Card>>
}