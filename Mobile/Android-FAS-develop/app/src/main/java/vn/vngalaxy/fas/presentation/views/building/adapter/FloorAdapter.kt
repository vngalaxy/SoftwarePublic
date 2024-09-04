package vn.vngalaxy.fas.presentation.views.building.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.vngalaxy.fas.databinding.ItemCommonBinding
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.shared.utils.CustomDiffCallBack

class FloorAdapter(
    private var listener: ((Floor) -> Unit),
) : ListAdapter<Floor, FloorAdapter.FloorViewHolder>(CustomDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FloorViewHolder {
        val binding = ItemCommonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FloorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FloorViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class FloorViewHolder(
        private val binding: ItemCommonBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(floor: Floor) {
            binding.commonNameTxt.text = floor.name

            //Handle event
            binding.root.setOnClickListener { listener.invoke(floor) }
        }
    }
}