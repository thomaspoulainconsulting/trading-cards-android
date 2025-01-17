package com.tpc.tradingcards.ui

import android.app.Application
import com.tpc.tradingcards.BuildConfig
import com.tpc.tradingcards.core.di.networkModule
import com.tpc.tradingcards.core.di.repositoryModule
import com.tpc.tradingcards.core.di.viewmodelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

class TradingCardsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@TradingCardsApplication)
            modules(
                networkModule,
                repositoryModule,
                viewmodelModule,
            )
        }
    }
}