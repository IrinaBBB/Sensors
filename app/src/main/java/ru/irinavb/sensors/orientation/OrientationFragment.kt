package ru.irinavb.sensors.orientation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import ru.irinavb.sensors.databinding.FragmentOrientationBinding

class OrientationFragment : Fragment() {

    private var _binding: FragmentOrientationBinding? = null
    private val binding get() = _binding!!

    private val viewModel by inject<OrientationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrientationBinding.inflate(inflater, container, false)
        viewModel.createOrientation()
        viewModel.orientationValues.observe(viewLifecycleOwner, {
            binding.xOrientationValueText.text = it[0].toString()
            binding.yOrientationValueText.text = it[1].toString()
            binding.zOrientationValueText.text = it[2].toString()
        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val sensor = viewModel.registerOrientationListener()
        if (sensor == null) {
            Toast.makeText(requireContext(), "No Orientation Sensor found!", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.unregisterOrientationListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}