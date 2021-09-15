package ru.irinavb.sensors.sensorlist

import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.ext.android.inject
import ru.irinavb.sensors.databinding.FragmentListBinding

private const val TAG = "ListFragment"

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<SensorListRecyclerView.ViewHolder>? = null

    private val sensorManager: SensorManager by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
        val sensorList = ArrayList<String>()
        sensors.forEach {
            Log.d(TAG, "Sensor: ${it.stringType} : ${it.name}")
            val text = "${it.stringType} -> ${it.name}"
            sensorList.add(text)
        }
        layoutManager = LinearLayoutManager(requireContext())
        binding.sensorListRecyclerView.layoutManager = layoutManager

        adapter = SensorListRecyclerView(sensorList)
        binding.sensorListRecyclerView.adapter = adapter

        return binding.root
    }
}