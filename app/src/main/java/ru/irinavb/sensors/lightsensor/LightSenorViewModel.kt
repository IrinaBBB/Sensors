package ru.irinavb.sensors.lightsensor

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.irinavb.sensors.util.Util
import java.time.LocalDateTime

private const val FILE_LIGHT_SENSOR = "light_sensor.txt"

class LightSenorViewModel(application: Application, private val sensorManager: SensorManager) :
        AndroidViewModel(application),
        SensorEventListener {

    private val _currentLux = MutableLiveData<Float>()
    val currentLux: LiveData<Float>
        get() = _currentLux

    fun createLightSensor(): Sensor? {
        return sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    fun registerLightSensorListener(): Sensor? {
        val sensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        sensorManager.registerListener(
            this,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        return sensor
    }

    fun unregisterLightSensorListener() {
        sensorManager.unregisterListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSensorChanged(event: SensorEvent?) {
        _currentLux.value = event!!.values[0]
        val text = "light sensor ${LocalDateTime.now()}: Ambient Light (lx): ${event.values[0]}\n"

        Util.writeToInternalStorage(getApplication(), FILE_LIGHT_SENSOR, text)
    }

    override fun onAccuracyChanged(event: Sensor?, p1: Int) {}
}