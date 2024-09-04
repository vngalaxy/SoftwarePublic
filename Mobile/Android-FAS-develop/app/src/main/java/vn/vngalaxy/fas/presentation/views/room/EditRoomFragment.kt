package vn.vngalaxy.fas.presentation.views.room

import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.vngalaxy.fas.R
import vn.vngalaxy.fas.databinding.FragmentEditRoomBinding
import vn.vngalaxy.fas.presentation.base.BaseFragment
import vn.vngalaxy.fas.presentation.views.change_name.ChangeNameDialogFragment
import vn.vngalaxy.fas.shared.extensions.navigate
import vn.vngalaxy.fas.shared.extensions.navigateBack
import vn.vngalaxy.fas.shared.extensions.setOnClick
import vn.vngalaxy.fas.shared.scheduler.DataResult
import vn.vngalaxy.fas.shared.widget.showFailedSnackbar

class EditRoomFragment : BaseFragment<FragmentEditRoomBinding, EditRoomViewModel>() {
    override val viewModel: EditRoomViewModel by viewModel()
    private val args: EditRoomFragmentArgs by navArgs()

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentEditRoomBinding.inflate(inflater)

    override fun setUpView() {}

    override fun bindView() {}

    override fun registerLiveData() {
        super.registerLiveData()

        with(viewModel) {
            room.observe(viewLifecycleOwner) {
                viewBinding.roomTitleTxt.text = it.name
                viewBinding.roomNameTxt.text = it.name
                viewBinding.sensorCountTxt.text = it.sensors.size.toString()
                viewBinding.btnDeleteRoom.visibility = if (it.sensors.isEmpty()) View.VISIBLE else View.GONE
            }

            deleteRoomResult.observe(viewLifecycleOwner) {
                when (it) {
                    is DataResult.Success -> {
                        navigateBack()
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
            btnBack.setOnClickListener {
                navigateBack()
            }

            changeNameLayout.setOnClick {
                navigate(
                    EditRoomFragmentDirections.actionToChangeNameDialog(
                        ChangeNameDialogFragment.ACTION_CHANGE_ROOM_NAME,
                        room = viewModel.room.value
                    )
                )
            }

            qrCodeLayout.setOnClick {
                // TODO
            }

            manageSensorLayout.setOnClick {
                viewModel.room.value?.let {
                    val action = EditRoomFragmentDirections.actionToSensor(it)
                    navigate(action, navOptions { popUpTo(R.id.sensor) { inclusive = true } })
                }

            }

            btnDeleteRoom.setOnClickListener {
                viewModel.room.value?.let { it1 -> viewModel.deleteRoom(it1) }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadRoomDetail(args.room)
    }
}