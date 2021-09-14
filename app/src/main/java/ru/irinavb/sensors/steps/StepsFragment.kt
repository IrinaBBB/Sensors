package ru.irinavb.sensors.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import ru.irinavb.sensors.databinding.FragmentStepsBinding


class StepsFragment : Fragment() {

    private var _binding: FragmentStepsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by inject<StepsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStepsBinding.inflate(inflater, container, false)
        viewModel.createStepsSensor()
        viewModel.currentSteps.observe(viewLifecycleOwner, {
            binding.stepsCounterText.text = it.toString()
        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val sensor = viewModel.registerStepsListener()
        if (sensor == null) {
            Toast.makeText(requireContext(), "No Steps Counter found!", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.unregisterStepsListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}