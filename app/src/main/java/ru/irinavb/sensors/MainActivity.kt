package ru.irinavb.sensors

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import ru.irinavb.sensors.databinding.ActivityMainBinding
import java.io.FileOutputStream
import java.io.IOException
import java.lang.System.currentTimeMillis
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime

private const val TAG = "MainActivity"
private const val FILE_ACCELEROMETER = "accelerometer.txt"

class MainActivity : AppCompatActivity(), SensorEventListener{

    private lateinit var binding: ActivityMainBinding

    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    @RequiresApi(Build.VERSION_CODES.O)
    private var lastUpdated: Long  =  0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    @SuppressLint("NewApi", "StringFormatMatches")
    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        val now = currentTimeMillis()
            val text = "accelerometer ${LocalDateTime.now()}: X -> ${sensorEvent!!.values[0]}, " +
                    "Y -> ${sensorEvent.values[1]}, Z -> ${sensorEvent.values[2]}\n"
            Log.d(TAG, "onSensorChanged: $text")

            binding.accelerometerValueTextView.text = resources.getString(R.string.accelerometer_text,
                sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2])


            writeToInternalStorage(FILE_ACCELEROMETER, text)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(this)
    }

    private fun writeToInternalStorage(file: String, text: String) {
        var fos: FileOutputStream? = null
        try {
            fos = openFileOutput(file, MODE_APPEND)
            fos.write(text.toByteArray(StandardCharsets.UTF_8))
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}