package ru.irinavb.sensors.magnetometer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import ru.irinavb.sensors.databinding.FragmentMagnetometerBinding

class MagnetometerFragment : Fragment(){

    private var _binding: FragmentMagnetometerBinding? = null
    private val binding get() = _binding!!

    private val viewModel by inject<MagnetometerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMagnetometerBinding.inflate(inflater, container, false)
        viewModel.createMagnetometer()
        viewModel.magnetometerValues.observe(viewLifecycleOwner, {
            binding.xMagnetometerValueText.text = it[0].toString()
            binding.yMagnetometerValueText.text = it[1].toString()
            binding.zMagnetometerValueText.text = it[2].toString()
        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val sensor = viewModel.registerMagnetometerListener()
        if (sensor == null) {
            Toast.makeText(requireContext(), "No Magnetometer Sensor found!", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.unregisterMagnetometerListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}