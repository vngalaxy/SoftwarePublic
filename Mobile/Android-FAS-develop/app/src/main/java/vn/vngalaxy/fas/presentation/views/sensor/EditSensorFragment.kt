package vn.vngalaxy.fas.presentation.views.sensor

import android.view.LayoutInflater
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.vngalaxy.fas.R
import vn.vngalaxy.fas.databinding.FragmentEditSensorBinding
import vn.vngalaxy.fas.model.ListFloor
import vn.vngalaxy.fas.model.SensorType
import vn.vngalaxy.fas.presentation.base.BaseFragment
import vn.vngalaxy.fas.presentation.views.change_room.ChangeRoomDialogFragment
import vn.vngalaxy.fas.shared.constant.Constant.UNKNOWN
import vn.vngalaxy.fas.shared.extensions.capitalize
import vn.vngalaxy.fas.shared.extensions.navigate
import vn.vngalaxy.fas.shared.extensions.setOnClick

class EditSensorFragment : BaseFragment<FragmentEditSensorBinding, EditSensorViewModel>() {
    override val viewModel: EditSensorViewModel by viewModel()
    private val args: EditSensorFragmentArgs by navArgs()

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentEditSensorBinding = FragmentEditSensorBinding.inflate(inflater)
    override fun setUpView() {}

    override fun bindView() {
        with(viewBinding) {
            sensorTypeTxt.text = SensorType.fromValue(args.sensor.type).viValue.capitalize()
            sensorIdTxt.text = args.sensor.id
            sensorRoomTxt.text = args.sensor.room?.name ?: UNKNOWN
        }
    }

    override fun handleEvent() {
        with(viewBinding) {
            btnBack.setOnClick {
                val action = EditSensorFragmentDirections.actionToSensorDetail(args.sensor)
                navigate(action, navOptions { popUpTo(R.id.sensor_detail) { inclusive = true } })
            }

            sensorRoomTxt.setOnClick {
                if (viewModel.floors.value != null && viewModel.floors.value != null) {
                    navigate(
                        EditSensorFragmentDirections.actionToChangeRoomDialog(
                            action = ChangeRoomDialogFragment.ACTION_CHANGE_SENSOR_ROOM,
                            sensor = args.sensor,
                            listFloor = ListFloor(viewModel.floors.value!!)
                        )
                    )
                }
            }
        }
    }
}