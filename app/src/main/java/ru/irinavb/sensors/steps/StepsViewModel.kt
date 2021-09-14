package ru.irinavb.sensors.steps

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

private const val FILE_STEPS = "Steps_sensor.txt"

class StepsViewModel(application: Application, private val sensorManager: SensorManager) :
    AndroidViewModel(application),
    SensorEventListener {

    private val _currentSteps = MutableLiveData<Float>()
    val currentSteps: LiveData<Float>
        get() = _currentSteps

    fun createStepsSensor(): Sensor? {
        return sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    }

    fun registerStepsListener(): Sensor? {
        val sensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        sensorManager.registerListener(
            this,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        return sensor
    }

    fun unregisterStepsListener() {
        sensorManager.unregisterListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSensorChanged(event: SensorEvent?) {
        _currentSteps.value = event!!.values[0]

        val text = "Steps ${LocalDateTime.now()}: Steps Count: ${event.values[0]}\n"
        Util.writeToInternalStorage(getApplication(), FILE_STEPS, text)
    }

    override fun onAccuracyChanged(event: Sensor?, p1: Int) {}
}