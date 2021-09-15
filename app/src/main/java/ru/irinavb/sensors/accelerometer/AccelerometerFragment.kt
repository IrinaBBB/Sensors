package ru.irinavb.sensors.accelerometer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import ru.irinavb.sensors.databinding.FragmentAccelerometerBinding


class AccelerometerFragment : Fragment() {

    private var _binding: FragmentAccelerometerBinding? = null
    private val binding get() = _binding!!

    private val viewModel by inject<AccelerometerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccelerometerBinding.inflate(inflater, container, false)
        viewModel.createAccelerometerSensor()
        viewModel.accelerometerValues.observe(viewLifecycleOwner, {
            binding.xAccelerometerValueText.text = it[0].toString()
            binding.yAccelerometerValueText.text = it[1].toString()
            binding.zAccelerometerValueText.text = it[2].toString()
        })
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        val sensor = viewModel.registerAccelerometerSensorListener()
        if (sensor == null) {
            Toast.makeText(requireContext(), "No Accelerometer Sensor found!", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.unregisterAccelerometerSensorListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}