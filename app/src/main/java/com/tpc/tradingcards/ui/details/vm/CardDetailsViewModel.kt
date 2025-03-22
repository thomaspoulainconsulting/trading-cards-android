package com.tpc.tradingcards.ui.details.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpc.tradingcards.data.repository.pokemon.PokemonCardRepository
import com.tpc.tradingcards.ui.details.state.CardDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class CardDetailsViewModel(
    private val repository: PokemonCardRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<CardDetailsState>(CardDetailsState.Loading)
    val state = _state.asStateFlow()

    private val _selectedTypes = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val selectedTypes = _selectedTypes.asStateFlow()

    private lateinit var idSet: String

    init {
        getCardTypes()
    }

    fun setIdSet(idSet: String) {
        this.idSet = idSet
    }

    private fun getCardTypes() = viewModelScope.launch {
        runCatching { repository.getCardTypes() }
            .onSuccess { cardTypes ->
                _selectedTypes.emit(cardTypes.map { it.name }.associateWith { true })
            }
            .onFailure { Timber.e(it) }
    }

    fun getCards() = viewModelScope.launch {
        _state.value = CardDetailsState.Loading
        runCatching { repository.getCards(idSet) }
            .onSuccess { _state.emit(CardDetailsState.Success(it)) }
            .onFailure { _state.emit(CardDetailsState.Error(it)) }
    }

    fun updateSelectedType(cardType: String) {
        _selectedTypes.tryEmit(_selectedTypes.value.toMutableMap().apply {
            this[cardType] = !this[cardType]!!
        })

        getCards()
    }

}