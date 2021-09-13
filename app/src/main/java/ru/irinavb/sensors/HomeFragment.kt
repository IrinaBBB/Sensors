package ru.irinavb.sensors

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import ru.irinavb.sensors.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var navHostFragment: NavHostFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        setUpAppNavigation(view)
        return view
    }

    private fun setUpAppNavigation(view: View) {
        binding.accelerometerCardView.setOnClickListener {
           Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_accelerometerFragment)
        }
        binding.compassCardView.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_compassFragment)
        }
        binding.lightCardView.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_lightSensorFragment)
        }
        binding.proximityCardView.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_proximitySensorFragment)
        }
        binding.orientationCardView.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_orientationFragment)
        }
        binding.stepsCardView.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_stepsFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}