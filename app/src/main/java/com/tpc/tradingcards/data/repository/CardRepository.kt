package com.tpc.tradingcards.data.repository

import com.tpc.tradingcards.data.model.Card
import com.tpc.tradingcards.data.model.CardSet
import kotlinx.coroutines.flow.Flow

interface CardRepository {

    /**
     * Load cards from the local database
     */
    fun loadCards(): Flow<List<Card>>

    /**
     * Load card sets from the local database
     */
    fun loadSets(): Flow<List<CardSet>>

    /**
     * Fetch cards from a remote database
     */
    suspend fun fetchCards(idSet: String)

    /**
     * Fetch the card sets from a remote database
     */
    suspend fun fetchCardSets()
}