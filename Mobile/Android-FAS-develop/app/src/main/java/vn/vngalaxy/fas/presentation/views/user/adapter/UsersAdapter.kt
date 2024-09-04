package vn.vngalaxy.fas.presentation.views.user.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.vngalaxy.fas.databinding.ItemUserBinding
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.model.User
import vn.vngalaxy.fas.shared.utils.CustomDiffCallBack

class UsersAdapter(
    private var listener: ((User, Room) -> Unit),
    private val room: Room,
) : ListAdapter<User, UsersAdapter.UsersViewHolder>(CustomDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(currentList[position], room)
    }

    inner class UsersViewHolder(
        private val binding: ItemUserBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(user: User, room: Room) {
            binding.userNameTxt.text = user.name
            binding.phoneNumberTxt.text = user.phoneNumber

            //Handle event
            binding.root.setOnClickListener { listener.invoke(user, room) }
        }
    }
}