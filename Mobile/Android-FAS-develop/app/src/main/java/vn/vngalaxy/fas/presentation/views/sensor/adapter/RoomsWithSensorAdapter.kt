package vn.vngalaxy.fas.presentation.views.sensor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.vngalaxy.fas.databinding.ItemRoomBinding
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.model.Sensor
import vn.vngalaxy.fas.shared.utils.CustomDiffCallBack

class RoomsWithSensorAdapter(
    private var listener: ((Room) -> Unit),
    private var sensorListener: ((Sensor, Room) -> Unit),
    private var sensorSwitchListener: ((Sensor, Boolean) -> Unit),
) : ListAdapter<Room, RoomsWithSensorAdapter.RoomsRoomsWithSensorViewHolder>(CustomDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsRoomsWithSensorViewHolder {
        val binding = ItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoomsRoomsWithSensorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoomsRoomsWithSensorViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class RoomsRoomsWithSensorViewHolder(
        private val binding: ItemRoomBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(room: Room) {
            binding.roomNameTxt.text = room.name
            binding.sensorQuantityTxt.text = room.sensors.size.toString()

            val sensorAdapter = SensorsAdapter(::onClickItem, ::onItemSwitchChange, room)

            binding.sensorList.adapter = sensorAdapter
            sensorAdapter.submitList(room.sensors)

            binding.root.setOnClickListener { listener.invoke(room) }
        }

        private fun onClickItem(sensor: Sensor, room: Room) {
            sensorListener.invoke(sensor, room)
        }

        private fun onItemSwitchChange(sensor: Sensor, boolean: Boolean) {
            sensorSwitchListener.invoke(sensor, boolean)
        }
    }
}