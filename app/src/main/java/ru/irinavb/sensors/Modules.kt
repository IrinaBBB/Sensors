package ru.irinavb.sensors

import android.content.Context
import android.hardware.SensorManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { androidContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    single { Test(get()) }
}