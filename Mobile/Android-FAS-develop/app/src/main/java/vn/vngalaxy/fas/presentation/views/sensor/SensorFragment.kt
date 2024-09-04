package vn.vngalaxy.fas.presentation.views.sensor

import android.util.Log
import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.vngalaxy.fas.R
import vn.vngalaxy.fas.databinding.FragmentSensorBinding
import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.model.Sensor
import vn.vngalaxy.fas.presentation.base.BaseFragment
import vn.vngalaxy.fas.presentation.views.sensor.adapter.RoomsWithSensorAdapter
import vn.vngalaxy.fas.shared.constant.Constant.STRING_EMPTY
import vn.vngalaxy.fas.shared.extensions.navigate
import vn.vngalaxy.fas.shared.extensions.setOnClick


class SensorFragment : BaseFragment<FragmentSensorBinding, SensorViewModel>() {
    override val viewModel: SensorViewModel by viewModel()
    private val args: SensorFragmentArgs by navArgs()

    //Dropdown adapter
    private lateinit var floorAdapter: ArrayAdapter<Floor>
    private lateinit var apartmentAdapter: ArrayAdapter<Apartment>
    private lateinit var roomAdapter: ArrayAdapter<Room>

    //Recycler view adapter
    private var roomWithSensorAdapter: RoomsWithSensorAdapter =
        RoomsWithSensorAdapter(::onClickItem, ::onSensorItemClick, ::onSensorSwitchItemClick)

    private fun onSensorSwitchItemClick(sensor: Sensor, b: Boolean) {
        Log.d("Daataaaaaaaa", "${sensor}+$b")
    }

    private fun onSensorItemClick(sensor: Sensor, room: Room) {
        navigate(SensorFragmentDirections.actionToSensorDetail(sensor.copy(room = room)))
    }

    private fun onClickItem(room: Room) {
        navigate(SensorFragmentDirections.actionToEditRoom(room))
    }

    private var apartmentList = mutableListOf<Apartment>()
    private var roomList = mutableListOf<Room>()

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentSensorBinding.inflate(inflater)

    override fun setUpView() {
        viewBinding.lifecycleOwner = this.viewLifecycleOwner
        viewBinding.sensorViewModel = viewModel
    }

    override fun bindView() {
        with(viewBinding) {
            roomWithSensorList.adapter = roomWithSensorAdapter
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
                    if (args.room != null) {
                        val floor = it.find { floor -> floor.id == args.room!!.apartment?.floor?.id }
                        val apartment = floor?.apartments?.find { apartment -> apartment.id == args.room!!.apartment?.id }
                        if (floor != null && apartment != null) {
                            viewModel.setSelectedFloor(floor)
                            viewModel.setSelectedApartment(apartment)
                            viewModel.setSelectedRoom(args.room!!)
                        }
                    } else {
                        viewModel.setSelectedFloor(it.first())
                    }
                }
            }

            roomsWithSensor.observe(viewLifecycleOwner) {
                roomWithSensorAdapter.submitList(it)
            }

            selectedFloor.observe(viewLifecycleOwner) {
                loadRoomsWithFloor()
                viewBinding.floorsAutoTxt.setText(it.name, false)
                apartmentList = it.apartments.toMutableList()
                apartmentAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, apartmentList)
                viewBinding.apartmentAutoTxt.setAdapter(apartmentAdapter)
            }

            selectedApartment.observe(viewLifecycleOwner) {
                loadRoomsWithApartment()
                viewBinding.apartmentAutoTxt.setText(it.name, false)
                roomList = it.rooms.toMutableList()
                roomAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, roomList)
                viewBinding.roomAutoTxt.setAdapter(roomAdapter)
            }

            selectedRoom.observe(viewLifecycleOwner) {
                loadRoomsDetail()
                viewBinding.roomAutoTxt.setText(it.name, false)
            }

        }
    }

    override fun handleEvent() {
        with(viewBinding) {
            floorsAutoTxt.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                val selectedFloor = viewModel.floors.value!![position]
                viewModel.setSelectedFloor(selectedFloor)

                // Reset apartment and room
                viewBinding.apartmentAutoTxt.setText(STRING_EMPTY)
                viewBinding.roomAutoTxt.setText(STRING_EMPTY)
            }

            apartmentAutoTxt.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                val selectedApartment = apartmentList[position]
                viewModel.setSelectedApartment(selectedApartment)

                // Reset room
                roomAutoTxt.setText(STRING_EMPTY)
            }

            roomAutoTxt.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                val selectedRoom = roomList[position]
                viewModel.setSelectedRoom(selectedRoom)
            }

            btnAddSensor.setOnClick {
                navigate(SensorFragmentDirections.actionToQrScan())
            }
        }
    }
}