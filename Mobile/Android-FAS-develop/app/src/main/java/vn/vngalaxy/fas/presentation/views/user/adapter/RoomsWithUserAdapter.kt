package vn.vngalaxy.fas.presentation.views.user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.vngalaxy.fas.databinding.ItemRoomWithUsersBinding
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.model.User
import vn.vngalaxy.fas.shared.utils.CustomDiffCallBack

class RoomsWithUserAdapter(
    private var userListener: ((User, Room) -> Unit),
) : ListAdapter<Room, RoomsWithUserAdapter.RoomsWithSensorViewHolder>(CustomDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsWithSensorViewHolder {
        val binding = ItemRoomWithUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoomsWithSensorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoomsWithSensorViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class RoomsWithSensorViewHolder(
        private val binding: ItemRoomWithUsersBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(room: Room) {
            binding.roomNameTxt.text = room.name
            binding.userQuantityTxt.text = room.users.size.toString()

            val userAdapter = UsersAdapter(::onClickItem, room)

            binding.userList.adapter = userAdapter
            userAdapter.submitList(room.users)
        }

        private fun onClickItem(user: User, room: Room) {
            userListener.invoke(user, room)
        }
    }
}