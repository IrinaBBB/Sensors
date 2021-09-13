package ru.irinavb.sensors

import android.hardware.SensorManager

class Test(private val sensorManager: SensorManager) {
    private lateinit var sensorManager1: SensorManager

    fun getSensorManagerFromTest(): SensorManager {
        sensorManager1 = sensorManager
        return sensorManager1
    }
}