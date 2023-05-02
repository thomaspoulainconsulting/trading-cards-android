package com.tpc.pokemontradingcards.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpc.pokemontradingcards.domain.PokemonCardData
import com.tpc.pokemontradingcards.domain.PokemonCardDataEmpty
import com.tpc.pokemontradingcards.domain.PokemonManager
import com.tpc.pokemontradingcards.domain.PokemonSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonManager: PokemonManager
) : ViewModel() {

    private var pokemonSet: PokemonSet by mutableStateOf(PokemonSet.Pokemon_Go)
    lateinit var pokemonCardsData: StateFlow<List<PokemonCardData>>

    init {
        loadPokemonCardsDate(pokemonSet)
    }

    private fun loadPokemonCardsDate(pokemonSet: PokemonSet) {
        pokemonCardsData = pokemonManager.getPokemonCards(pokemonSet)
            .catch {
                emptyList<List<PokemonCardData>>()
            }
            .stateIn(
                initialValue = List(8) { PokemonCardDataEmpty.copy(id = it.toString()) },
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000)
            )
    }

    /**
     * Updates the pokemon set displayed
     */
    fun updatePokemonSet(pokemonSet: PokemonSet) {
        this.pokemonSet = pokemonSet
        loadPokemonCardsDate(pokemonSet)
    }
}