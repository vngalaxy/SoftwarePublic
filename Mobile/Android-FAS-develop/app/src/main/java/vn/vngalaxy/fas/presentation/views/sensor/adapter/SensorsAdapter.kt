package vn.vngalaxy.fas.presentation.views.sensor.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.vngalaxy.fas.R
import vn.vngalaxy.fas.databinding.ItemSensorBinding
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.model.Sensor
import vn.vngalaxy.fas.model.SensorStatus
import vn.vngalaxy.fas.model.SensorType
import vn.vngalaxy.fas.shared.utils.CustomDiffCallBack

class SensorsAdapter(
    private var listener: ((Sensor, Room) -> Unit),
    private var switchListener: ((Sensor, Boolean) -> Unit),
    private val room: Room,
) : ListAdapter<Sensor, SensorsAdapter.SensorsViewHolder>(CustomDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorsViewHolder {
        val binding = ItemSensorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SensorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SensorsViewHolder, position: Int) {
        holder.bind(currentList[position], room)
    }

    inner class SensorsViewHolder(
        private val binding: ItemSensorBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(sensor: Sensor, room: Room) {
            val context = binding.root.context
            val normalColor = context.getColor(R.color.primary)
            val dangerColor = context.getColor(R.color.text_red)
            val warningColor = context.getColor(R.color.text_warning)

            binding.sensorNameTxt.text = sensor.name

            when (sensor.type) {
                SensorType.HEAT.value -> {
                    binding.icType.setImageDrawable(context.getDrawable(R.drawable.ic_heat))
                }

                SensorType.SMOKE.value -> {
                    binding.icType.setImageDrawable(context.getDrawable(R.drawable.ic_smoke))
                }

                else -> {
                    binding.icType.setImageDrawable(context.getDrawable(R.drawable.ic_sensor))
                }
            }

            when (sensor.status) {
                SensorStatus.ON.value -> {
                    binding.status.text = context.getString(R.string.normal)
                    binding.status.setTextColor(normalColor)
                    binding.switcherBtn.setChecked(true)
                }

                SensorStatus.OFF.value -> {
                    binding.status.text = context.getString(R.string.off)
                    binding.status.setTextColor(dangerColor)
                    binding.switcherBtn.setChecked(false)
                }

                SensorStatus.WARNING.value -> {
                    binding.status.text = context.getString(R.string.warning)
                    binding.status.setTextColor(warningColor)
                }

                SensorStatus.FIRE.value -> {
                    binding.status.text = context.getString(R.string.fire)
                    binding.status.setTextColor(dangerColor)
                }

                else -> {
                    binding.status.text = context.getString(R.string.unknown)
                    binding.status.setTextColor(warningColor)
                    binding.switcherBtn.setChecked(false)
                }
            }

            //Handle event
            binding.root.setOnClickListener { listener.invoke(sensor, room) }
            binding.switcherBtn.setOnCheckedChangeListener {
                switchListener.invoke(sensor, it)
            }
        }
    }
}