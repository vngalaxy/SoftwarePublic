package vn.vngalaxy.fas.presentation.views.user

import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.ArrayAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.vngalaxy.fas.R
import vn.vngalaxy.fas.databinding.FragmentUserBinding
import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.model.ListFloor
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.model.User
import vn.vngalaxy.fas.presentation.base.BaseFragment
import vn.vngalaxy.fas.presentation.views.user.adapter.RoomsWithUserAdapter
import vn.vngalaxy.fas.shared.constant.Constant
import vn.vngalaxy.fas.shared.extensions.navigate
import vn.vngalaxy.fas.shared.extensions.setOnClick


class UserFragment : BaseFragment<FragmentUserBinding, UserViewModel>() {
    override val viewModel: UserViewModel by viewModel()

    //Dropdown adapter
    private lateinit var floorAdapter: ArrayAdapter<Floor>
    private lateinit var apartmentAdapter: ArrayAdapter<Apartment>
    private lateinit var roomAdapter: ArrayAdapter<Room>

    private var apartmentList = mutableListOf<Apartment>()
    private var roomList = mutableListOf<Room>()

    //Recycler view adapter
    private var roomWithUserAdapter: RoomsWithUserAdapter = RoomsWithUserAdapter(::onUserItemClick)

    private fun onUserItemClick(user: User, room: Room) {
        navigate(UserFragmentDirections.actionToEditUser(user.copy(room = room)))
    }

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentUserBinding.inflate(inflater)


    override fun setUpView() = Unit

    override fun bindView() {
        with(viewBinding) {
            roomWithUserList.adapter = roomWithUserAdapter
        }
    }

    override fun registerLiveData() {
        super.registerLiveData()

        with(viewModel) {
            floors.observe(viewLifecycleOwner) {
                floorAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, it)
                viewBinding.floorsAutoTxt.setAdapter(floorAdapter)

                //Init default floor
                if (it.isNotEmpty()) {
                    setSelectedFloor(it.first())
                }
            }

            roomsWithUser.observe(viewLifecycleOwner) {
                roomWithUserAdapter.submitList(it)
            }

            selectedFloor.observe(viewLifecycleOwner) {
                loadRoomsWithFloor(it)

                apartmentList = it.apartments.toMutableList()
                apartmentAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, apartmentList)
                viewBinding.apartmentAutoTxt.setAdapter(apartmentAdapter)
                viewBinding.floorsAutoTxt.setText(it.name, false)
            }

            selectedApartment.observe(viewLifecycleOwner) {
                loadRoomsWithApartment(it)

                roomList = it.rooms.toMutableList()
                roomAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, roomList)
                viewBinding.roomAutoTxt.setAdapter(roomAdapter)
                viewBinding.apartmentAutoTxt.setText(it.name, false)
            }

            selectedRoom.observe(viewLifecycleOwner) {
                loadRoomsDetail(it)

                viewBinding.roomAutoTxt.setText(it.name, false)
            }

        }
    }

    override fun handleEvent() {
        with(viewBinding) {
            btnAddUser.setOnClick {
                if (viewModel.floors.value != null) {
                    navigate(UserFragmentDirections.actionUserToAddUser(ListFloor(viewModel.floors.value!!)))
                }
            }

            floorsAutoTxt.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                val selectedFloor = viewModel.floors.value!![position]
                viewModel.setSelectedFloor(selectedFloor)

                // Reset room
                apartmentAutoTxt.setText(Constant.STRING_EMPTY)
                roomAutoTxt.setText(Constant.STRING_EMPTY)

            }

            apartmentAutoTxt.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                val selectedApartment = apartmentList[position]
                viewModel.setSelectedApartment(selectedApartment)

                // Reset room
                roomAutoTxt.setText(Constant.STRING_EMPTY)
            }

            roomAutoTxt.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                val selectedRoom = roomList[position]
                viewModel.setSelectedRoom(selectedRoom)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadAllFloors()
    }
}