package com.tpc.pokemontradingcards.data.repository

import com.tpc.pokemontradingcards.data.model.Card
import com.tpc.pokemontradingcards.data.model.CardSet
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