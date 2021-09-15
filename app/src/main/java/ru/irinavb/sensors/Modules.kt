package ru.irinavb.sensors

import android.app.Application
import android.content.Context
import android.hardware.SensorManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.irinavb.sensors.accelerometer.AccelerometerViewModel
import ru.irinavb.sensors.lightsensor.LightSenorViewModel
import ru.irinavb.sensors.magnetometer.MagnetometerViewModel
import ru.irinavb.sensors.orientation.OrientationViewModel
import ru.irinavb.sensors.proximitysensor.ProximityViewModel
import ru.irinavb.sensors.steps.StepsViewModel

val appModule = module {
    single { androidContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager }
}

val viewModelModule = module {
    viewModel { LightSenorViewModel(androidContext() as Application, get()) }
    viewModel { AccelerometerViewModel(androidContext() as Application, get()) }
    viewModel { MagnetometerViewModel(androidContext() as Application, get()) }
    viewModel { OrientationViewModel(androidContext() as Application, get()) }
    viewModel { ProximityViewModel(androidContext() as Application, get()) }
    viewModel { StepsViewModel(androidContext() as Application, get()) }
}
