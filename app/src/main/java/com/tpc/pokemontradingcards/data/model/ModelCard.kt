package com.tpc.pokemontradingcards.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ModelCard(
    @PrimaryKey val id: String,
    val label: String,
    val url: String,
    val number: Int,
    val idSet: String,
)

val ModelCardEmpty = ModelCard(id = "", label = "", url = "", number = 0, idSet = "")