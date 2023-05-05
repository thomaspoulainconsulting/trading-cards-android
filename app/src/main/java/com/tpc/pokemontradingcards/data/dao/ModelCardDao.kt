package com.tpc.pokemontradingcards.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.tpc.pokemontradingcards.data.model.ModelCard
import kotlinx.coroutines.flow.Flow

@Dao
interface ModelCardDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(card: ModelCard)

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(cards: List<ModelCard>)

    @Query("SELECT * FROM ModelCard WHERE idSet = :idSet")
    suspend fun getAll(idSet: String): List<ModelCard>

    @Query("SELECT * FROM ModelCard")
    fun getAll(): Flow<List<ModelCard>>
}