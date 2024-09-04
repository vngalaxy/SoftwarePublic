package vn.vngalaxy.fas.presentation.views.change_name

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.vngalaxy.fas.R
import vn.vngalaxy.fas.databinding.FragmentChangeNameDialogBinding
import vn.vngalaxy.fas.presentation.base.BaseDialogFragment
import vn.vngalaxy.fas.shared.extensions.navigate
import vn.vngalaxy.fas.shared.extensions.navigateBack
import vn.vngalaxy.fas.shared.extensions.setOnClick
import vn.vngalaxy.fas.shared.extensions.updateAppearance
import vn.vngalaxy.fas.shared.scheduler.DataResult
import vn.vngalaxy.fas.shared.widget.showFailedSnackbar

class ChangeNameDialogFragment :
    BaseDialogFragment<FragmentChangeNameDialogBinding, ChangeNameDialogViewModel>(R.layout.fragment_change_name_dialog) {
    override val viewModel: ChangeNameDialogViewModel by viewModel()
    private val args: ChangeNameDialogFragmentArgs by navArgs()

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentChangeNameDialogBinding.inflate(inflater)

    override fun bindView() {
        with(viewBinding) {
            when (args.action) {
                ACTION_CHANGE_USER_NAME -> {
                    dialogTitle.text = getString(R.string.change_user_name)
                    nameEdt.hint = getString(R.string.user_name_edt)
                    nameEdt.setText(args.user?.name)
                }

                ACTION_CHANGE_FLOOR_NAME -> {
                    dialogTitle.text = getString(R.string.change_floor_name)
                    nameEdt.hint = getString(R.string.floor_name_edt)
                    nameEdt.setText(args.floor?.name)
                }

                ACTION_CHANGE_APARTMENT_NAME -> {
                    dialogTitle.text = getString(R.string.change_apartment_name)
                    nameEdt.hint = getString(R.string.apartment_name_edt)
                    nameEdt.setText(args.apartment?.name)
                }

                ACTION_CHANGE_ROOM_NAME -> {
                    dialogTitle.text = getString(R.string.change_room_name)
                    nameEdt.hint = getString(R.string.room_name_edt)
                    nameEdt.setText(args.room?.name)
                }
            }
        }
    }

    override fun registerLiveData() {
        with(viewModel) {
            changeNameResult.observe(viewLifecycleOwner) {
                when (it) {
                    is DataResult.Success -> {
                        val (action, navOptions) = when (args.action) {
                            ACTION_CHANGE_USER_NAME -> {
                                Pair(
                                    ChangeNameDialogFragmentDirections.actionToEditUser(args.user!!),
                                    navOptions {
                                        popUpTo(R.id.edit_user) { inclusive = true }
                                    }
                                )
                            }

                            ACTION_CHANGE_FLOOR_NAME -> {
                                Pair(
                                    ChangeNameDialogFragmentDirections.actionToEditFloor(args.floor!!),
                                    navOptions {
                                        popUpTo(R.id.edit_floor) { inclusive = true }
                                    }
                                )
                            }

                            ACTION_CHANGE_APARTMENT_NAME -> {
                                Pair(
                                    ChangeNameDialogFragmentDirections.actionToEditApartment(args.apartment!!),
                                    navOptions {
                                        popUpTo(R.id.edit_floor) { inclusive = true }
                                    }
                                )
                            }

                            ACTION_CHANGE_ROOM_NAME -> {
                                Pair(
                                    ChangeNameDialogFragmentDirections.actionToEditRoom(args.room!!),
                                    navOptions {
                                        popUpTo(R.id.edit_room) { inclusive = true }
                                    }
                                )
                            }

                            else -> {
                                Pair(
                                    ChangeNameDialogFragmentDirections.actionToChangeNameDialog(
                                        action = args.action,
                                        user = args.user
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
                    ACTION_CHANGE_USER_NAME -> {
                        viewModel.changeUserName(args.user!!.copy(name = viewModel.newName))
                    }

                    ACTION_CHANGE_FLOOR_NAME -> {
                        viewModel.changeFloorName(args.floor!!.copy(name = viewModel.newName))
                    }

                    ACTION_CHANGE_APARTMENT_NAME -> {
                        viewModel.changeApartmentName(args.apartment!!.copy(name = viewModel.newName))
                    }

                    ACTION_CHANGE_ROOM_NAME -> {
                        viewModel.changeRoomName(args.room!!.copy(name = viewModel.newName))
                    }
                }
            }

            nameEdt.addTextChangedListener(textWatcher)
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            with(viewBinding) {
                with(viewModel) {
                    newName = nameEdt.text.toString().trim()

                    if (!newName.isNullOrEmpty() && newName != args.user?.name.toString()) {
                        btnConfirm.updateAppearance(true)
                    } else {
                        btnConfirm.updateAppearance(false)
                    }
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    companion object {
        const val ACTION_CHANGE_USER_NAME = 0
        const val ACTION_CHANGE_FLOOR_NAME = 1
        const val ACTION_CHANGE_APARTMENT_NAME = 2
        const val ACTION_CHANGE_ROOM_NAME = 3
    }
}