package com.tpc.pokemontradingcards.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.tpc.pokemontradingcards.data.model.Card
import com.tpc.pokemontradingcards.data.model.CardType
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(cards: List<Card>)

    @Query("SELECT * FROM Card WHERE idSet = :idSet")
    suspend fun getAllCards(idSet: String): List<Card>

    @Query("SELECT * FROM Card WHERE cardType=:cardType")
    fun getAllCards(cardType: CardType): Flow<List<Card>>
}