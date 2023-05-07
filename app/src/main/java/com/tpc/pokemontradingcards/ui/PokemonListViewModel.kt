package com.tpc.pokemontradingcards.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpc.pokemontradingcards.CardRepository
import com.tpc.pokemontradingcards.data.dto.UIState
import com.tpc.pokemontradingcards.data.model.Card
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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

    var currentCardSet by mutableStateOf("")
    val cardsData: StateFlow<UIState<List<Card>>> by lazy {
        pokemonCardRepository.loadCards()
            .map { UIState.Success(it) }
            .stateIn(
                initialValue = UIState.Loading(),
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000)
            )
    }

    fun updatePokemonSet(idSet: String) = viewModelScope.launch {
        pokemonCardRepository.fetchCards(idSet)
        currentCardSet = idSet
    }
}