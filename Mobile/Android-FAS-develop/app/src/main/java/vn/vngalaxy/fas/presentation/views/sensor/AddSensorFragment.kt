package vn.vngalaxy.fas.presentation.views.sensor


import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.vngalaxy.fas.R
import vn.vngalaxy.fas.databinding.FragmentAddSensorBinding
import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.model.SensorType
import vn.vngalaxy.fas.presentation.base.BaseFragment
import vn.vngalaxy.fas.shared.constant.Constant
import vn.vngalaxy.fas.shared.extensions.capitalize
import vn.vngalaxy.fas.shared.extensions.navigateBack
import vn.vngalaxy.fas.shared.extensions.setOnClick
import vn.vngalaxy.fas.shared.extensions.updateAppearance
import vn.vngalaxy.fas.shared.scheduler.DataResult
import vn.vngalaxy.fas.shared.widget.showFailedSnackbar
import vn.vngalaxy.fas.shared.widget.showSuccessSnackbar

class AddSensorFragment : BaseFragment<FragmentAddSensorBinding, AddSensorViewModel>() {
    override val viewModel: AddSensorViewModel by viewModel()
    private val args: AddSensorFragmentArgs by navArgs()

    //    private val typeList = SensorType.listTypeVi()
    private var apartmentList = mutableListOf<Apartment>()
    private var roomList = mutableListOf<Room>()

    //    private var sensorTypeAdapter: ArrayAdapter<String>? = null
    private var floorAdapter: ArrayAdapter<Floor>? = null
    private var apartmentAdapter: ArrayAdapter<Apartment>? = null
    private var roomAdapter: ArrayAdapter<Room>? = null

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentAddSensorBinding.inflate(inflater)

    override fun setUpView() {
//        sensorTypeAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, typeList.map {
//            it.replaceFirstChar {
//                if (it.isLowerCase()) it.titlecase(
//                    Locale.getDefault()
//                ) else it.toString()
//            }
//        })
//        viewBinding.sensorTypeAutoTxt.setAdapter(sensorTypeAdapter)
    }

    override fun bindView() {
        with(viewBinding) {
//                args.sensor.type?.let { viewModel.setSelectedType(it) }
            sensorTypeAutoTxt.setText(SensorType.fromId(args.qrDevice.deviceProfileID).viValue.capitalize())
            sensorTypeAutoTxt.isEnabled = false
            sensorIdEdt.setText(args.qrDevice.devEUI)
            sensorIdEdt.isEnabled = false
            sensorNameEdt.setText(args.qrDevice.sensorName)
            sensorNameEdt.isEnabled = false
        }
    }

    override fun registerLiveData() {
        super.registerLiveData()

        with(viewModel) {
            floors.observe(viewLifecycleOwner) {
                floorAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, it)
                viewBinding.floorsAutoTxt.setAdapter(floorAdapter)
            }

//            selectedType.observe(viewLifecycleOwner) {
//                viewBinding.sensorTypeAutoTxt.setText(
//                    SensorType.viName(it).replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }, false
//                )
//                viewBinding.btnAddSensor.updateAppearance(sensorId.isNotEmpty() && selectedRoom.value != null)
//            }

            selectedFloor.observe(viewLifecycleOwner) {
                apartmentList = it.apartments.toMutableList()
                apartmentAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, apartmentList)
                viewBinding.apartmentAutoTxt.setAdapter(apartmentAdapter)
                viewBinding.floorsAutoTxt.setText(it.name, false)
                viewBinding.btnAddSensor.updateAppearance(false)
            }

            selectedApartment.observe(viewLifecycleOwner) {
                roomList = it.rooms.toMutableList()
                roomAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, roomList)
                viewBinding.roomAutoTxt.setAdapter(roomAdapter)
                viewBinding.apartmentAutoTxt.setText(it.name, false)
                viewBinding.btnAddSensor.updateAppearance(false)
            }

            selectedRoom.observe(viewLifecycleOwner) {
                viewBinding.roomAutoTxt.setText(it.name, false)
                viewBinding.btnAddSensor.updateAppearance(true)
            }

            addSensorResult.observe(viewLifecycleOwner) {
                when (it) {
                    is DataResult.Success -> {
                        viewBinding.btnAddSensor.updateAppearance(false)
                        view?.showSuccessSnackbar(getString(R.string.add_sensor_success))
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
            btnBack.setOnClick {
                navigateBack()
            }

            btnAddSensor.setOnClick {
                viewModel.addSensor(args.qrDevice)
            }

//            sensorTypeAutoTxt.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
//                val selectedType = SensorType.enName(typeList[position])
//                viewModel.setSelectedType(selectedType)
//            }

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

//            sensorIdEdt.addTextChangedListener(textWatcher)
//            sensorNameEdt.addTextChangedListener(textWatcher)
        }
    }

//    private val textWatcher = object : TextWatcher {
//        override fun afterTextChanged(s: Editable?) {
//            with(viewModel) {
//                sensorId = viewBinding.sensorIdEdt.text.toString().trim()
//                sensorName = viewBinding.sensorNameEdt.text.toString().trim()
//                viewBinding.btnAddSensor.updateAppearance(
//                    selectedRoom.value != null &&
//                            selectedType.value != null &&
//                            sensorName.isNotEmpty() &&
//                            sensorId.isNotEmpty()
//                )
//            }
//        }
//
//        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//    }
}