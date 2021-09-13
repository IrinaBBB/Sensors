package ru.irinavb.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import ru.irinavb.sensors.databinding.FragmentMagnetometerBinding
import java.time.LocalDateTime

private const val FILE_MAGNETOMETER = "magnetometer.txt"

class MagnetometerFragment : Fragment(), SensorEventListener {

    private var _binding: FragmentMagnetometerBinding? = null
    private val binding get() = _binding!!

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMagnetometerBinding.inflate(inflater, container, false)
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val magnetometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        if (magnetometerSensor != null) {
            sensorManager.registerListener(
                this,
                magnetometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        } else {
            Toast.makeText(requireContext(), "No Magnetic Field Sensor found!", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSensorChanged(event: SensorEvent?) {
        binding.xMagnetometerValueText.text = event!!.values[0].toString()
        binding.yMagnetometerValueText.text = event.values[1].toString()
        binding.zMagnetometerValueText.text = event.values[2].toString()

        val text =  "magnetometer ${LocalDateTime.now()}: X -> ${event.values[0]}, " +
                "Y -> ${event.values[1]}, Z -> ${event.values[2]}\n"

        Util.writeToInternalStorage(requireContext(), FILE_MAGNETOMETER, text)
    }

    override fun onAccuracyChanged(event: Sensor?, p1: Int) {}
}