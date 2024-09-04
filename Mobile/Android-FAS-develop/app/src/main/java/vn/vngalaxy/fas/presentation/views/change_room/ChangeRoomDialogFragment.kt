package vn.vngalaxy.fas.presentation.views.change_room

import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.vngalaxy.fas.R
import vn.vngalaxy.fas.databinding.FragmentChangeRoomDialogBinding
import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.presentation.base.BaseDialogFragment
import vn.vngalaxy.fas.shared.constant.Constant
import vn.vngalaxy.fas.shared.extensions.navigate
import vn.vngalaxy.fas.shared.extensions.navigateBack
import vn.vngalaxy.fas.shared.extensions.setOnClick
import vn.vngalaxy.fas.shared.scheduler.DataResult
import vn.vngalaxy.fas.shared.widget.showFailedSnackbar

class ChangeRoomDialogFragment :
    BaseDialogFragment<FragmentChangeRoomDialogBinding, ChangeRoomDialogViewModel>(R.layout.fragment_change_room_dialog) {
    override val viewModel: ChangeRoomDialogViewModel by viewModel()
    private val args: ChangeRoomDialogFragmentArgs by navArgs()

    //Dropdown adapter
    private lateinit var floorAdapter: ArrayAdapter<Floor>
    private lateinit var apartmentAdapter: ArrayAdapter<Apartment>
    private lateinit var roomAdapter: ArrayAdapter<Room>

    private var apartmentList = mutableListOf<Apartment>()
    private var roomList = mutableListOf<Room>()

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentChangeRoomDialogBinding.inflate(inflater)

    override fun bindView() {
        floorAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, args.listFloor.floors)
        viewBinding.floorsAutoTxt.setAdapter(floorAdapter)

        //Init default floor
        if (args.listFloor.floors.isNotEmpty()) {
            when (args.action) {
                ACTION_CHANGE_SENSOR_ROOM -> {
                    val floor = args.listFloor.floors.find { it.id == args.sensor?.room?.apartment?.floor?.id }
                    floor?.let { viewModel.setSelectedFloor(it) }

                    val apartment = floor?.apartments?.find { it.id == args.sensor?.room?.apartment?.id }
                    apartment?.let { viewModel.setSelectedApartment(it) }

                    val room = apartment?.rooms?.find { it.id == args.sensor?.room?.id }
                    room?.let { viewModel.setSelectedRoom(it) }
                }

                ACTION_CHANGE_USER_ROOM -> {
                    val floor = args.listFloor.floors.find { it.id == args.user?.room?.apartment?.floor?.id }
                    floor?.let { viewModel.setSelectedFloor(it) }

                    val apartment = floor?.apartments?.find { it.id == args.user?.room?.apartment?.id }
                    apartment?.let { viewModel.setSelectedApartment(it) }

                    val room = apartment?.rooms?.find { it.id == args.user?.room?.id }
                    room?.let { viewModel.setSelectedRoom(it) }

                }
            }
        }
    }

    override fun registerLiveData() {
        with(viewModel) {
            selectedFloor.observe(viewLifecycleOwner) {
                apartmentList = it.apartments.toMutableList()
                apartmentAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, apartmentList)
                viewBinding.apartmentAutoTxt.setAdapter(apartmentAdapter)
                viewBinding.floorsAutoTxt.setText(it.name, false)
            }

            selectedApartment.observe(viewLifecycleOwner) {
                roomList = it.rooms.toMutableList()
                roomAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, roomList)
                viewBinding.roomAutoTxt.setAdapter(roomAdapter)
                viewBinding.apartmentAutoTxt.setText(it.name, false)
            }

            selectedRoom.observe(viewLifecycleOwner) {
                viewBinding.roomAutoTxt.setText(it.name, false)
            }

            changeRoomResult.observe(viewLifecycleOwner) {
                when (it) {
                    is DataResult.Success -> {
                        val (action, navOptions) = when (args.action) {
                            ACTION_CHANGE_SENSOR_ROOM -> {
                                Pair(
                                    ChangeRoomDialogFragmentDirections.actionToEditSensor(args.sensor!!.copy(room = viewModel.selectedRoom.value)),
                                    navOptions {
                                        popUpTo(R.id.edit_sensor) { inclusive = true }
                                    }
                                )
                            }

                            ACTION_CHANGE_USER_ROOM -> {
                                Pair(
                                    ChangeRoomDialogFragmentDirections.actionToEditUser(args.user!!.copy(room = viewModel.selectedRoom.value)),
                                    navOptions {
                                        popUpTo(R.id.edit_user) { inclusive = true }
                                    }
                                )
                            }

                            else -> {
                                Pair(
                                    ChangeRoomDialogFragmentDirections.actionToChangeRoomDialog(
                                        action = args.action,
                                        sensor = args.sensor,
                                        user = args.user,
                                        listFloor = args.listFloor
                                    ),
                                    navOptions {
                                        popUpTo(R.id.change_name_dialog) { inclusive = true }
                                    }
                                )
                            }
                        }

                        navigate(action, navOptions)
                        reset()
                    }

                    is DataResult.Error -> {
                        view?.showFailedSnackbar(it.exception.message.toString())
                        reset()
                    }

                    is DataResult.Loading -> Unit
                    DataResult.Empty -> hideLoading()
                }

            }
        }
    }


    override fun handleEvent() {
        with(viewBinding) {
            btnCancel.setOnClick {
                navigateBack()
            }

            btnConfirm.setOnClick {
                when (args.action) {
                    ACTION_CHANGE_SENSOR_ROOM -> {
                        if (viewModel.selectedRoom.value != null && viewModel.selectedRoom.value!!.id != args.sensor?.room?.id.toString()) {
                            viewModel.changeSensorRoom(args.sensor!!.copy(room = viewModel.selectedRoom.value))
                        }
                    }

                    ACTION_CHANGE_USER_ROOM -> {
                        if (viewModel.selectedRoom.value != null && viewModel.selectedRoom.value!!.id != args.user?.room?.id.toString()) {
                            viewModel.changeUserRoom(args.user!!.copy(room = viewModel.selectedRoom.value))
                        }
                    }
                }
            }

            floorsAutoTxt.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                val selectedFloor = args.listFloor.floors[position]
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

    companion object {
        const val ACTION_CHANGE_SENSOR_ROOM = 0
        const val ACTION_CHANGE_USER_ROOM = 1
    }
}