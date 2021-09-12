package ru.irinavb.sensors

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.irinavb.sensors.databinding.FragmentCompassBinding
import ru.irinavb.sensors.databinding.FragmentLightSensorBinding

class LightSensorFragment : Fragment() {

    private var _binding: FragmentLightSensorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLightSensorBinding.inflate(inflater, container, false)
        return binding.root
    }
}