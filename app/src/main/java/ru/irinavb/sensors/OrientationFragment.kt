package ru.irinavb.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import ru.irinavb.sensors.databinding.FragmentOrientationBinding
import java.time.LocalDateTime

private const val FILE_ORIENTATION = "orientation.txt"

class OrientationFragment : Fragment(), SensorEventListener {

    private var _binding: FragmentOrientationBinding? = null
    private val binding get() = _binding!!

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrientationBinding.inflate(inflater, container, false)
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        sensorManager.registerListener(this, accelerometer, SensorManager
            .SENSOR_DELAY_NORMAL)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        if (sensor != null) {
            sensorManager.registerListener(
                this,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        } else {
            Toast.makeText(requireContext(), "No Orientation Sensor found!", Toast.LENGTH_LONG)
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
        binding.xOrientationValueText.text = event!!.values[0].toString()
        binding.yOrientationValueText.text = event.values[1].toString()
        binding.zOrientationValueText.text = event.values[2].toString()

        val text =  "orientation ${LocalDateTime.now()}: X -> ${event.values[0]}, " +
                "Y -> ${event.values[1]}, Z -> ${event.values[2]}\n"
        Util.writeToInternalStorage(requireContext(), FILE_ORIENTATION, text)
    }

    override fun onAccuracyChanged(event: Sensor?, p1: Int) {}
}