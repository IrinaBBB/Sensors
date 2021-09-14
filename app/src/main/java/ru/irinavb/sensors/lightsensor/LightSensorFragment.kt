package ru.irinavb.sensors.lightsensor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import org.koin.android.ext.android.inject
import ru.irinavb.sensors.databinding.FragmentLightSensorBinding

class LightSensorFragment : Fragment() {

    private var _binding: FragmentLightSensorBinding? = null
    private val binding get() = _binding!!

    private val viewModel by inject<LightSenorViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLightSensorBinding.inflate(inflater, container, false)
        viewModel.createLightSensor()
        viewModel.currentLux.observe(viewLifecycleOwner, {
            binding.lightSensorText.text = it.toString()
        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val sensor = viewModel.registerLightSensorListener()
        if (sensor == null) {
            Toast.makeText(requireContext(), "No Light Sensor found!", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.unregisterLightSensorListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}