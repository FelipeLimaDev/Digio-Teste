package br.com.digio.androidtest

import android.app.Application
import br.com.digio.androidtest.presentation.di.modulesDigio
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DigioTesteApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger()
            androidContext(this@DigioTesteApplication)
            modules(
                modulesDigio
            )
        }
    }
}