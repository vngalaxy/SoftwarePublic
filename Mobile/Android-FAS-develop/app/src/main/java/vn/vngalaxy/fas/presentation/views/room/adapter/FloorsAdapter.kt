package vn.vngalaxy.fas.presentation.views.room.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.vngalaxy.fas.R
import vn.vngalaxy.fas.databinding.ItemFloorBinding
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.shared.extensions.setOnClick
import vn.vngalaxy.fas.shared.utils.CustomDiffCallBack

class FloorsAdapter(
    private var listener: ((Floor) -> Unit)
) : ListAdapter<Floor, FloorsAdapter.FloorViewHolder>(CustomDiffCallBack()) {

    val defaultItem = Floor(DEFAULT_FLOOR_ID, DEFAULT_FLOOR_NAME)
    private var selectedItem: Floor = defaultItem


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FloorViewHolder {
        val binding = ItemFloorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FloorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FloorViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class FloorViewHolder(
        private val binding: ItemFloorBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(floor: Floor) {
            if (floor == selectedItem) {
                binding.root.setBackgroundResource(R.drawable.button_primary_bg)
            } else {
                binding.root.setBackgroundResource(R.drawable.chip_disable_bg)
            }

            binding.floorNameTxt.text = floor.name
            binding.root.setOnClick {
                selectedItem = floor
                listener.invoke(floor)
                notifyDataSetChanged()
            }
        }
    }

    companion object {
        const val DEFAULT_FLOOR_ID = "all"
        const val DEFAULT_FLOOR_NAME = "Tất cả"
    }
}