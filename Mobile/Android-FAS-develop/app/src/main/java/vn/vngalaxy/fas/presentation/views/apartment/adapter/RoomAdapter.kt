package vn.vngalaxy.fas.presentation.views.apartment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.vngalaxy.fas.databinding.ItemCommonBinding
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.shared.utils.CustomDiffCallBack

class RoomAdapter(
    private var listener: ((Room) -> Unit),
) : ListAdapter<Room, RoomAdapter.RoomViewHolder>(CustomDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val binding = ItemCommonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class RoomViewHolder(
        private val binding: ItemCommonBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(room: Room) {
            binding.commonNameTxt.text = room.name

            //Handle event
            binding.root.setOnClickListener { listener.invoke(room) }
        }
    }
}