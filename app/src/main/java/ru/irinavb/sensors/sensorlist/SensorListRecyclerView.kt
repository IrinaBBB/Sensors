package ru.irinavb.sensors.sensorlist

import android.hardware.Sensor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.irinavb.sensors.R

class SensorListRecyclerView(sensorList: List<String>): RecyclerView.Adapter<SensorListRecyclerView.ViewHolder>() {

    private var sensorListNames = sensorList

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SensorListRecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SensorListRecyclerView.ViewHolder, position: Int) {
        holder.sensorLabel.text = sensorListNames[position]
    }

    override fun getItemCount(): Int {
        return sensorListNames.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
         var sensorLabel: TextView
            = itemView.findViewById(R.id.sensor_label)
    }
}