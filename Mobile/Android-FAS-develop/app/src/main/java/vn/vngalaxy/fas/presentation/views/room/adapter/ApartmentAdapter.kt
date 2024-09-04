package vn.vngalaxy.fas.presentation.views.room.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.vngalaxy.fas.databinding.ItemApartmentBinding
import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.shared.extensions.setOnClick
import vn.vngalaxy.fas.shared.utils.CustomDiffCallBack

class ApartmentAdapter(
    private var listener: ((Apartment) -> Unit),
    private var roomListener: ((Room, Apartment) -> Unit),
) : ListAdapter<Apartment, ApartmentAdapter.ApartmentViewHolder>(CustomDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApartmentViewHolder {
        val binding = ItemApartmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApartmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ApartmentViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ApartmentViewHolder(
        private val binding: ItemApartmentBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(apartment: Apartment) {
            binding.apartmentNameTxt.text = apartment.name
            binding.roomQuantityTxt.text = apartment.rooms.size.toString()

            binding.root.setOnClick { listener.invoke(apartment) }

            val roomAdapter = RoomAdapter(::onClickItem, apartment)

            binding.roomList.adapter = roomAdapter
            roomAdapter.submitList(apartment.rooms)
        }

        private fun onClickItem(room: Room, apartment: Apartment) {
            roomListener.invoke(room, apartment)
        }
    }
}