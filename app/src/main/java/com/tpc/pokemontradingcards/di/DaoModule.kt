package com.tpc.pokemontradingcards.di

import com.tpc.pokemontradingcards.data.dao.ModelCardDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    @Singleton
    fun provideModelCardDao(database: PokemonCardDatabase): ModelCardDao {
        return database.provideModelCardDao()
    }
}