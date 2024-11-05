package com.tpc.tradingcards.ui.list.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpc.tradingcards.data.repository.pokemon.PokemonCardRepository
import com.tpc.tradingcards.ui.list.state.CardListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CardListViewModel(
    private val cardRepository: PokemonCardRepository
) : ViewModel() {

    val state: StateFlow<CardListState>
        field: MutableStateFlow<CardListState> = MutableStateFlow(CardListState.Loading)

    init {
        fetchCardSets()
    }

    private fun fetchCardSets() = viewModelScope.launch {
        runCatching {
            state.value = CardListState.Loading

            val sets = cardRepository.getSets()
            state.value = CardListState.Success(sets)
        }.onFailure { e ->
            state.value = CardListState.Error(e)
        }
    }
}