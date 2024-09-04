package vn.vngalaxy.fas.presentation.views.room.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.vngalaxy.fas.databinding.ItemTextRoomBinding
import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.shared.extensions.setOnClick
import vn.vngalaxy.fas.shared.utils.CustomDiffCallBack

class RoomAdapter(
    private var listener: ((Room, Apartment) -> Unit),
    private val apartment: Apartment,
) : ListAdapter<Room, RoomAdapter.UsersViewHolder>(CustomDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = ItemTextRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(currentList[position], apartment)
    }

    inner class UsersViewHolder(
        private val binding: ItemTextRoomBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(room: Room, apartment: Apartment) {
            binding.roomNameTxt.text = room.name

            //Handle event
            binding.root.setOnClick { listener.invoke(room, apartment) }
        }
    }
}