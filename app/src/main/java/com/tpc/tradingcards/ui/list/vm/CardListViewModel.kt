package com.tpc.tradingcards.ui.list.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpc.tradingcards.data.repository.pokemon.PokemonCardRepository
import com.tpc.tradingcards.ui.list.state.CardListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardListViewModel(
    private val cardRepository: PokemonCardRepository
) : ViewModel() {

    private val _state: MutableStateFlow<CardListState> = MutableStateFlow(CardListState.Loading)
    val state = _state.asStateFlow()

    fun fetchCardSets() = viewModelScope.launch {
        _state.value = CardListState.Loading
        runCatching { cardRepository.getSets() }
            .onSuccess { _state.value = CardListState.Success(it) }
            .onFailure { _state.value = CardListState.Error(it) }
    }
}