package com.tpc.tradingcards.ui.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpc.tradingcards.core.extention.defaultStateIn
import com.tpc.tradingcards.core.ui.UIState
import com.tpc.tradingcards.data.model.Card
import com.tpc.tradingcards.data.model.CardSet
import com.tpc.tradingcards.data.model.CardSetEmpty
import com.tpc.tradingcards.data.repository.pokemon.PokemonCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
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

    private val _cardSetSelected = MutableStateFlow(CardSetEmpty)
    val cardSetSelected = _cardSetSelected.asStateFlow()

    val cards: StateFlow<List<Card>> =
        cardSetSelected
            .flatMapLatest { cardRepository.getCards(it.id) }
            .onEach { data ->
                if (data.isEmpty()) {
                    viewModelScope.launch { // We don't want that the flow suspends on the network call
                        cardRepository.fetchCards(cardSetSelected.value.id)
                    }
                }
            }
            .defaultStateIn(viewModelScope, emptyList())

    val sets: StateFlow<UIState<List<CardSet>>> =
        cardRepository.getSets()
            .onEach { data ->
                if (data.isEmpty()) cardRepository.fetchCardSets()
            }
            .mapLatest { UIState.Success(it) }
            .defaultStateIn(viewModelScope, UIState.Loading())

    fun fetchCards(cardSet: CardSet) = viewModelScope.launch {
        _cardSetSelected.emit(cardSet)
    }
}