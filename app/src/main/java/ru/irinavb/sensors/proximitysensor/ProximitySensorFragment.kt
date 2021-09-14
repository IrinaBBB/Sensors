package ru.irinavb.sensors.proximitysensor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import ru.irinavb.sensors.databinding.FragmentProximitySensorBinding


class ProximitySensorFragment : Fragment() {

    private var _binding: FragmentProximitySensorBinding? = null
    private val binding get() = _binding!!

    private val viewModel by inject<ProximityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProximitySensorBinding.inflate(inflater, container, false)
        viewModel.createProximitySensor()
        viewModel.currentProximity.observe(viewLifecycleOwner, {
            binding.proximitySensorText.text = it.toString()
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val sensor = viewModel.registerProximityListener()
        if (sensor == null) {
            Toast.makeText(requireContext(), "No Proximity Sensor found!", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.unregisterProximityListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}