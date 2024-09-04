package vn.vngalaxy.fas.presentation.views.building.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.vngalaxy.fas.R
import vn.vngalaxy.fas.databinding.ItemBuildingBinding
import vn.vngalaxy.fas.model.Building
import vn.vngalaxy.fas.shared.utils.CustomDiffCallBack

class BuildingAdapter(private var listener: ((Building) -> Unit)) :
    ListAdapter<Building, BuildingAdapter.ComingSoonViewHolder>(CustomDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComingSoonViewHolder {
        val binding = DataBindingUtil.inflate<ItemBuildingBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_building,
            parent,
            false
        )
        return ComingSoonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComingSoonViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ComingSoonViewHolder(
        private val binding: ItemBuildingBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(building: Building) {
            binding.building = building
            binding.executePendingBindings()
            binding.root.setOnClickListener { listener.invoke(building) }
        }
    }
}