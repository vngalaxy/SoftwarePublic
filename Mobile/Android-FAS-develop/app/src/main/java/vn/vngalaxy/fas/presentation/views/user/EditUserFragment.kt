package vn.vngalaxy.fas.presentation.views.user

import android.view.LayoutInflater
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.vngalaxy.fas.databinding.FragmentEditUserBinding
import vn.vngalaxy.fas.model.ListFloor
import vn.vngalaxy.fas.presentation.base.BaseFragment
import vn.vngalaxy.fas.presentation.views.change_name.ChangeNameDialogFragment
import vn.vngalaxy.fas.presentation.views.change_room.ChangeRoomDialogFragment
import vn.vngalaxy.fas.shared.constant.Constant.UNKNOWN
import vn.vngalaxy.fas.shared.extensions.navigate
import vn.vngalaxy.fas.shared.extensions.navigateBack
import vn.vngalaxy.fas.shared.extensions.setOnClick


class EditUserFragment : BaseFragment<FragmentEditUserBinding, EditUserViewModel>() {
    override val viewModel: EditUserViewModel by viewModel()
    private val args: EditUserFragmentArgs by navArgs()

    override fun onStart() {
        super.onStart()
        args.user.room?.let { viewModel.loadRoomsDetail(it) }
    }

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentEditUserBinding.inflate(inflater)

    override fun setUpView() {}

    override fun bindView() {
        with(viewBinding) {
            titleUserNameTxt.text = args.user.name
            phoneNumberTxt.text = args.user.phoneNumber
            userNameTxt.text = args.user.name
            userRoomTxt.text = args.user.room?.name ?: UNKNOWN
        }
    }

    override fun handleEvent() {
        with(viewBinding) {
            btnBack.setOnClick {
                navigateBack()
            }

            userRoomTxt.setOnClick {
                if (viewModel.floors.value != null && viewModel.room.value != null) {
                    navigate(
                        EditUserFragmentDirections.actionToChangeRoomDialog(
                            action = ChangeRoomDialogFragment.ACTION_CHANGE_USER_ROOM,
                            user = args.user.copy(room = viewModel.room.value),
                            listFloor = ListFloor(viewModel.floors.value!!)
                        )
                    )
                }
            }

            userNameTxt.setOnClick {
                navigate(
                    EditUserFragmentDirections.actionToChangeNameDialog(
                        action = ChangeNameDialogFragment.ACTION_CHANGE_USER_NAME,
                        user = args.user.copy(room = viewModel.room.value),
                    )
                )
            }
        }
    }
}