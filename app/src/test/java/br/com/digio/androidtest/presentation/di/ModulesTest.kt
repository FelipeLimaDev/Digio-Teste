package br.com.digio.androidtest.presentation.di

import android.app.Application
import io.mockk.mockk
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.stopKoin
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.check.checkModules

class KoinModulesTest : KoinTest {

    @Test
    fun `check modules with mocked Context`() {
        val mockApplication = mockk<Application>(relaxed = true)
        koinApplication {
            androidContext(mockApplication)
            modules(modulesDigio)
        }.checkModules()
        
        stopKoin()
    }
}
