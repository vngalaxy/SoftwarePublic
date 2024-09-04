package vn.vngalaxy.fas.presentation.views.add_common

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.vngalaxy.fas.R
import vn.vngalaxy.fas.databinding.FragmentAddCommonBinding
import vn.vngalaxy.fas.presentation.base.BaseDialogFragment
import vn.vngalaxy.fas.shared.extensions.navigate
import vn.vngalaxy.fas.shared.extensions.navigateBack
import vn.vngalaxy.fas.shared.extensions.setOnClick
import vn.vngalaxy.fas.shared.extensions.updateAppearance
import vn.vngalaxy.fas.shared.scheduler.DataResult
import vn.vngalaxy.fas.shared.widget.showFailedSnackbar

class AddCommonDialogFragment :
    BaseDialogFragment<FragmentAddCommonBinding, AddCommonViewModel>(R.layout.fragment_add_common) {
    override val viewModel: AddCommonViewModel by viewModel()
    private val args: AddCommonDialogFragmentArgs by navArgs()

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentAddCommonBinding.inflate(inflater)

    override fun bindView() {
        with(viewBinding) {
            when (args.action) {
                ACTION_ADD_FLOOR_NAME -> {
                    dialogTitle.text = getString(R.string.add_floor)
                    nameEdt.hint = getString(R.string.floor_name_edt)
                }

                ACTION_ADD_APARTMENT_NAME -> {
                    dialogTitle.text = getString(R.string.add_apartment)
                    nameEdt.hint = getString(R.string.apartment_name_edt)
                }

                ACTION_ADD_ROOM_NAME -> {
                    dialogTitle.text = getString(R.string.add_room)
                    nameEdt.hint = getString(R.string.room_name_edt)
                }
            }
        }
    }

    override fun registerLiveData() {
        with(viewModel) {
            addResult.observe(viewLifecycleOwner) {
                when (it) {
                    is DataResult.Success -> {
                        val (action, navOptions) = when (args.action) {
                            ACTION_ADD_FLOOR_NAME -> {
                                Pair(
                                    AddCommonDialogFragmentDirections.actionToEditBuilding(),
                                    navOptions {
                                        popUpTo(R.id.edit_building) { inclusive = true }
                                    }
                                )
                            }

                            ACTION_ADD_APARTMENT_NAME -> {
                                Pair(
                                    AddCommonDialogFragmentDirections.actionToEditFloor(args.floor!!),
                                    navOptions {
                                        popUpTo(R.id.edit_floor) { inclusive = true }
                                    }
                                )
                            }

                            ACTION_ADD_ROOM_NAME -> {
                                Pair(
                                    AddCommonDialogFragmentDirections.actionToEditApartment(args.apartment!!),
                                    navOptions {
                                        popUpTo(R.id.edit_apartment) { inclusive = true }
                                    }
                                )
                            }

                            else -> {
                                Pair(
                                    AddCommonDialogFragmentDirections.actionToAddCommonDialog(action = args.action),
                                    navOptions {
                                        popUpTo(R.id.add_common_dialog) { inclusive = true }
                                    }
                                )
                            }
                        }

                        navigate(action, navOptions)
//                        navigateBack()
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
                    ACTION_ADD_FLOOR_NAME -> {
                        viewModel.addFloor()
                    }

                    ACTION_ADD_APARTMENT_NAME -> {
                        viewModel.addApartment(args.floor)
                    }

                    ACTION_ADD_ROOM_NAME -> {
                        viewModel.addRoom(args.apartment)
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

                    if (!newName.isNullOrEmpty()) {
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
        const val ACTION_ADD_FLOOR_NAME = 0
        const val ACTION_ADD_APARTMENT_NAME = 1
        const val ACTION_ADD_ROOM_NAME = 2
    }
}