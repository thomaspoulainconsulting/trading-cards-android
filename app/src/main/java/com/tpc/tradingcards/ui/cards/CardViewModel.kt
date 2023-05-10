package com.tpc.tradingcards.ui.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpc.tradingcards.core.extention.defaultStateIn
import com.tpc.tradingcards.data.model.Card
import com.tpc.tradingcards.data.model.CardSet
import com.tpc.tradingcards.data.repository.pokemon.PokemonCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
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

    private val cardSetSelected = MutableStateFlow("")

    val cards: StateFlow<List<Card>> =
        cardSetSelected
            .flatMapLatest { cardRepository.getCards(it) }
            .onEach { data ->
                if (data.isEmpty()) {
                    viewModelScope.launch { // We don't want that the flow suspend on the network call
                        cardRepository.fetchCards(cardSetSelected.value)
                    }
                }
            }
            .defaultStateIn(viewModelScope, emptyList())

    val sets: StateFlow<List<CardSet>> =
        cardRepository.getSets()
            .onEach { data ->
                if (data.isEmpty()) cardRepository.fetchCardSets()
            }
            .defaultStateIn(viewModelScope, emptyList())

    fun fetchCards(idSet: String) = viewModelScope.launch {
        cardSetSelected.emit(idSet)
    }
}