package ru.irinavb.sensors.proximitysensor

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

private const val FILE_PROXIMITY = "proximity.txt"

class ProximityViewModel(application: Application, private val sensorManager: SensorManager) :
    AndroidViewModel(application),
    SensorEventListener {

    private val _currentProximity = MutableLiveData<Float>()
    val currentProximity: LiveData<Float>
        get() = _currentProximity

    fun createProximitySensor(): Sensor? {
        return sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    }

    fun registerProximityListener(): Sensor? {
        val sensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        sensorManager.registerListener(
            this,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        return sensor
    }

    fun unregisterProximityListener() {
        sensorManager.unregisterListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSensorChanged(event: SensorEvent?) {
        _currentProximity.value = event!!.values[0]

        val text =  "proximity sensor ${LocalDateTime.now()}: Proximity -> \n"
        Util.writeToInternalStorage(getApplication(), FILE_PROXIMITY, text)
    }

    override fun onAccuracyChanged(event: Sensor?, p1: Int) {}
}