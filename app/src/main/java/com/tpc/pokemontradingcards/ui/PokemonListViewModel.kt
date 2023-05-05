package com.tpc.pokemontradingcards.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpc.pokemontradingcards.data.PokemonCardRepository
import com.tpc.pokemontradingcards.data.dto.UIState
import com.tpc.pokemontradingcards.data.model.ModelCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * only load from local source
 * trigger a network call that update the database
 * with a flow that watch the database, the ui data will automatically reflects
 */
@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonCardRepository: PokemonCardRepository
) : ViewModel() {

    lateinit var pokemonCardsData: StateFlow<UIState<List<ModelCard>>>
    var currentPokemonSet by mutableStateOf(PokemonSet.JUNGLE)

    init {
        viewModelScope.launch {
            pokemonCardsData = pokemonCardRepository.loadPokemonCards()
                .map { UIState.Success(it) }
                .catch { UIState.Error<List<ModelCard>>() }
                .stateIn(
                    initialValue = UIState.Loading(),
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000)
                )
        }
    }

    fun updatePokemonSet(pokemonSet: PokemonSet) = viewModelScope.launch {
        pokemonCardRepository.downloadPokemonSet(pokemonSet.id)
        currentPokemonSet = pokemonSet
    }
}

enum class PokemonSet(val id: String) {
    BASE("base1"),
    JUNGLE("base2"),
    FOSSIL("base3"),
    POKEMON_GO("pgo")
}