package com.tpc.pokemontradingcards.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tpc.pokemontradingcards.data.dao.ModelCardDao
import com.tpc.pokemontradingcards.data.model.ModelCard

@Database(entities = [ModelCard::class], version = 1)
abstract class PokemonCardDatabase : RoomDatabase() {
    abstract fun provideModelCardDao(): ModelCardDao
}