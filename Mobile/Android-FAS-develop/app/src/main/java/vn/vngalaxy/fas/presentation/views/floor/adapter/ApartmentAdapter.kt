package vn.vngalaxy.fas.presentation.views.floor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.vngalaxy.fas.databinding.ItemCommonBinding
import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.shared.utils.CustomDiffCallBack

class ApartmentAdapter(
    private var listener: ((Apartment) -> Unit),
) : ListAdapter<Apartment, ApartmentAdapter.ApartmentViewHolder>(CustomDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApartmentViewHolder {
        val binding = ItemCommonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApartmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ApartmentViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ApartmentViewHolder(
        private val binding: ItemCommonBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(apartment: Apartment) {
            binding.commonNameTxt.text = apartment.name

            //Handle event
            binding.root.setOnClickListener { listener.invoke(apartment) }
        }
    }
}