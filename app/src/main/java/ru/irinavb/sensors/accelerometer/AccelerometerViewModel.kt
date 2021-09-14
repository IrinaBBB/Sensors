package ru.irinavb.sensors.accelerometer

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

private const val FILE_ACCELEROMETER = "accelerometer.txt"

class AccelerometerViewModel(application: Application, private val sensorManager: SensorManager) :
    AndroidViewModel(application),
    SensorEventListener {

    private val _accelerometerValues = MutableLiveData<FloatArray>()
    val accelerometerValues: LiveData<FloatArray>
        get() = _accelerometerValues

    fun createAccelerometerSensor(): Sensor? {
        return sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    fun registerAccelerometerSensorListener(): Sensor? {
        val sensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(
            this,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        return sensor
    }

    fun unregisterAccelerometerSensorListener() {
        sensorManager.unregisterListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSensorChanged(event: SensorEvent?) {
        _accelerometerValues.value = event!!.values
        val text =  "accelerometer ${LocalDateTime.now()}: X -> ${event.values[0]}, " +
                "Y -> ${event.values[1]}, Z -> ${event.values[2]}\n"

        Util.writeToInternalStorage(getApplication(), FILE_ACCELEROMETER, text)
    }

    override fun onAccuracyChanged(event: Sensor?, p1: Int) {}
}