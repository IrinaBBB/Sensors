package ru.irinavb.sensors

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Sensors : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Sensors)
            modules(listOf(appModule))
        }
    }
}