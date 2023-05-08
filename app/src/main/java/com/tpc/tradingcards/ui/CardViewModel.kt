package com.tpc.tradingcards.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpc.tradingcards.data.model.Card
import com.tpc.tradingcards.data.model.CardSet
 import com.tpc.tradingcards.data.repository.PokemonCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * The ViewModel exposes:
 * - a StateFlow of List<Card> that reflects the cards in database which can be filtered by setId
 * - a StateFlow of List<CardSet> that reflects the sets in database
 * - a method that will fetch a new Set from remote database
 */
@HiltViewModel
class CardViewModel @Inject constructor(
    private val cardRepository: PokemonCardRepository
) : ViewModel() {

    private val cardSetIdSelected = MutableStateFlow("")

    val cards: StateFlow<List<Card>> by lazy {
        cardRepository.loadCards()
            .combine(cardSetIdSelected) { list, filter ->
                list.filter { it.idSet == filter }
            }
            .stateIn(
                initialValue = emptyList(),
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000)
            )
    }

    val sets: StateFlow<List<CardSet>> by lazy {
        cardRepository.loadSets()
            .flatMapLatest { data ->
                if (data.isEmpty()) {
                    cardRepository.fetchCardSets()
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
        // Update the selected idSet
        cardSetIdSelected.emit(idSet)

        // Fetch remote cards if necessary
        try {
            cardRepository.fetchCards(idSet)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}