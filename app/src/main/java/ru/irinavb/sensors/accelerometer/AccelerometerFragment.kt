package ru.irinavb.sensors

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
import org.koin.android.ext.android.get
import ru.irinavb.sensors.databinding.FragmentAccelerometerBinding
import java.time.LocalDateTime

private const val FILE_ACCELEROMETER = "accelerometer.txt"

class AccelerometerFragment : Fragment(), SensorEventListener {

    private var _binding: FragmentAccelerometerBinding? = null
    private val binding get() = _binding!!

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccelerometerBinding.inflate(inflater, container, false)
        //sensorManager = activity?.getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager = get<Test>().getSensorManagerFromTest()

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSensorChanged(event: SensorEvent?) {
        binding.xAccelerationValueText.text = event!!.values[0].toString()
        binding.yAccelerationValueText.text = event.values[1].toString()
        binding.zAccelerationValueText.text = event.values[2].toString()

        val text =  "accelerometer ${LocalDateTime.now()}: X -> ${event.values[0]}, " +
                "Y -> ${event.values[1]}, Z -> ${event.values[2]}\n"

        Util.writeToInternalStorage(requireContext(), FILE_ACCELEROMETER, text)
    }

    override fun onAccuracyChanged(event: Sensor?, p1: Int) {}
}