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

    private var _state: MutableStateFlow<CardDetailsState> = MutableStateFlow(CardDetailsState.Loading)
    val state = _state.asStateFlow()

    private var _selectedTypes: MutableStateFlow<Map<String, Boolean>> = MutableStateFlow(emptyMap())
    val selectedTypes = _selectedTypes.asStateFlow()

    private lateinit var idSet: String

    init {
        getCardTypes()
    }

    fun setIdSet(idSet: String) {
        this.idSet = idSet
    }

    private fun getCardTypes() = viewModelScope.launch {
        try {
            val cardTypes = repository.getCardTypes()
            _selectedTypes.tryEmit(cardTypes.map { it.name }.associateWith { true })
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    fun getCards() = viewModelScope.launch {
        try {
            _state.tryEmit(CardDetailsState.Loading)

            val cardTypes = selectedTypes.value.filter { it.value }.map { it.key }
            val cards = repository.getCards(idSet, cardTypes)
            _state.tryEmit(CardDetailsState.Success(cards))
        } catch (e: Throwable) {
            _state.tryEmit(CardDetailsState.Error(e))
        }
    }

    fun updateSelectedType(cardType: String) {
        val selectedCardValue = selectedTypes.value.toMutableMap().apply {
            this[cardType] = !this[cardType]!!
        }

        _selectedTypes.tryEmit(selectedCardValue)

        // reload
        getCards()
    }

}