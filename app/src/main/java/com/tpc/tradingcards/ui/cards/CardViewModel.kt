package com.tpc.tradingcards.ui.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpc.tradingcards.data.model.Card
import com.tpc.tradingcards.data.model.CardSet
import com.tpc.tradingcards.data.repository.pokemon.PokemonCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel exposes:
 * - a StateFlow of List<Card> that reflects the cards in database which can be filtered by setId
 * - a StateFlow of List<CardSet> that reflects the sets in database
 * - a method that will fetch a new Set from remote database
 */
@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class CardViewModel @Inject constructor(
    private val cardRepository: PokemonCardRepository
) : ViewModel() {

    private val cardSetIdSelected = MutableStateFlow("")

    val cards: StateFlow<List<Card>> by lazy {
        cardRepository.cards
            .combine(cardSetIdSelected) { list, filter ->
                list.filter { it.idSet == filter }
            }
            .flatMapLatest { data ->
                if (data.isEmpty()) {
                    viewModelScope.launch { // We don't want that the flow suspend on the network call
                        cardRepository.fetchCards(cardSetIdSelected.value)
                    }
                }
                flowOf(data)
            }
            .stateIn(
                initialValue = emptyList(),
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000)
            )
    }

    val sets: StateFlow<List<CardSet>> by lazy {
        cardRepository.sets
            .flatMapLatest { data ->
                if (data.isEmpty()) cardRepository.fetchCardSets()
                flowOf(data)
            }
            .stateIn(
                initialValue = emptyList(),
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000)
            )
    }

    fun fetchCards(idSet: String) {
        cardSetIdSelected.value = idSet
    }
}