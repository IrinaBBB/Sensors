package ru.irinavb.sensors

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import ru.irinavb.sensors.databinding.FragmentAccelerometerBinding
import ru.irinavb.sensors.databinding.FragmentCompassBinding
import ru.irinavb.sensors.databinding.FragmentHomeBinding

class AccelerometerFragment : Fragment(), SensorEventListener {

    private var _binding: FragmentAccelerometerBinding? = null
    private val binding get() = _binding!!

    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccelerometerBinding.inflate(inflater, container, false)
        sensorManager = activity?.getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val accelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (accelerationSensor != null) {
            sensorManager.registerListener(
                this,
                accelerationSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        } else {
            Toast.makeText(requireContext(), "No acceleration Sensor found!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        binding.xAccelerationValueText.text = event!!.values[0].toString()
        binding.yAccelerationValueText.text = event.values[1].toString()
        binding.zAccelerationValueText.text = event.values[2].toString()
    }

    override fun onAccuracyChanged(event: Sensor?, p1: Int) {}
}