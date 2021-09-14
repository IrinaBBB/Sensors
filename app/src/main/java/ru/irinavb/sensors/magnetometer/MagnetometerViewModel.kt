package ru.irinavb.sensors.magnetometer

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

private const val FILE_MAGNETOMETER = "magnetometer.txt"

class MagnetometerViewModel(application: Application, private val sensorManager: SensorManager) :
    AndroidViewModel(application),
    SensorEventListener {

    private val _magnetometerValues = MutableLiveData<FloatArray>()
    val magnetometerValues: LiveData<FloatArray>
        get() = _magnetometerValues

    fun createMagnetometer(): Sensor? {
        return sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    fun registerMagnetometerListener(): Sensor? {
        val sensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        sensorManager.registerListener(
            this,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        return sensor
    }

    fun unregisterMagnetometerListener() {
        sensorManager.unregisterListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSensorChanged(event: SensorEvent?) {
        _magnetometerValues.value = event!!.values

        val text =  "magnetometer ${LocalDateTime.now()}: X -> ${event.values[0]}, " +
                "Y -> ${event.values[1]}, Z -> ${event.values[2]}\n"

        Util.writeToInternalStorage(getApplication(), FILE_MAGNETOMETER, text)
    }

    override fun onAccuracyChanged(event: Sensor?, p1: Int) {}
}