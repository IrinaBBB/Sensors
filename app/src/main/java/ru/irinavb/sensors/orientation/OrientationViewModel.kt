package ru.irinavb.sensors.orientation

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

private const val FILE_ORIENTATION = "orientation.txt"

class OrientationViewModel(application: Application, private val sensorManager: SensorManager) :
    AndroidViewModel(application),
    SensorEventListener {

    private val _orientationValues = MutableLiveData<FloatArray>()
    val orientationValues: LiveData<FloatArray>
        get() = _orientationValues

    fun createOrientation(): Sensor? {
        return sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
    }

    fun registerOrientationListener(): Sensor? {
        val sensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        sensorManager.registerListener(
            this,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        return sensor
    }

    fun unregisterOrientationListener() {
        sensorManager.unregisterListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSensorChanged(event: SensorEvent?) {
        _orientationValues.value = event!!.values

        val text =  "orientation ${LocalDateTime.now()}: X -> ${event.values[0]}, " +
                "Y -> ${event.values[1]}, Z -> ${event.values[2]}\n"

        Util.writeToInternalStorage(getApplication(), FILE_ORIENTATION, text)
    }

    override fun onAccuracyChanged(event: Sensor?, p1: Int) {}
}