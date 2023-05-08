package com.tpc.tradingcards.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpc.tradingcards.data.model.Card
import com.tpc.tradingcards.data.model.CardSet
import com.tpc.tradingcards.data.repository.CardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * The ViewModel exposes:
 * - a StateFlow of List<Card> that reflects what is in the database
 * - a method that will fetch a new Set from remote database
 */
@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonCardRepository: CardRepository
) : ViewModel() {

    val cards: StateFlow<List<Card>> by lazy {
        pokemonCardRepository.loadCards()
            .stateIn(
                initialValue = emptyList(),
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000)
            )
    }

    val sets: StateFlow<List<CardSet>> by lazy {
        pokemonCardRepository.loadSets()
            .flatMapLatest { data ->
                if (data.isEmpty()) {
                    pokemonCardRepository.fetchCardSets()
                }
                flowOf(data)
            }
            .stateIn(
                initialValue = emptyList(),
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000)
            )
    }

    fun fetchCards(idSet: String) = viewModelScope.launch {
        try {
            pokemonCardRepository.fetchCards(idSet)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}