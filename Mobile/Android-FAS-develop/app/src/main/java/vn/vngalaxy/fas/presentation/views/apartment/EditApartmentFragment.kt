package vn.vngalaxy.fas.presentation.views.apartment

import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.vngalaxy.fas.databinding.FragmentEditApartmentBinding
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.presentation.base.BaseFragment
import vn.vngalaxy.fas.presentation.views.add_common.AddCommonDialogFragment
import vn.vngalaxy.fas.presentation.views.apartment.adapter.RoomAdapter
import vn.vngalaxy.fas.presentation.views.change_name.ChangeNameDialogFragment
import vn.vngalaxy.fas.shared.extensions.navigate
import vn.vngalaxy.fas.shared.extensions.navigateBack
import vn.vngalaxy.fas.shared.extensions.setOnClick
import vn.vngalaxy.fas.shared.scheduler.DataResult
import vn.vngalaxy.fas.shared.widget.showFailedSnackbar

class EditApartmentFragment : BaseFragment<FragmentEditApartmentBinding, EditApartmentViewModel>() {
    override val viewModel: EditApartmentViewModel by viewModel()
    private val args: EditApartmentFragmentArgs by navArgs()

    private var roomAdapter = RoomAdapter(::onItemClick)

    private fun onItemClick(room: Room) {
        navigate(EditApartmentFragmentDirections.actionToEditRoom(room))
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadApartmentDetail(args.apartment)
    }

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentEditApartmentBinding.inflate(inflater)

    override fun setUpView() {
        viewBinding.roomList.adapter = roomAdapter
    }

    override fun bindView() {}

    override fun registerLiveData() {
        super.registerLiveData()

        with(viewModel) {
            apartment.observe(viewLifecycleOwner) {
                viewBinding.apartmentTitleTxt.text = it.name
                viewBinding.apartmentNameTxt.text = it.name
                roomAdapter.submitList(it.rooms)
                viewBinding.btnDeleteApartment.visibility = if (it.rooms.isEmpty()) View.VISIBLE else View.GONE
            }

            deleteApartmentResult.observe(viewLifecycleOwner) {
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
            btnBack.setOnClick {
                navigateBack()
            }

            btnDeleteApartment.setOnClick {
                viewModel.apartment.value?.let { viewModel.deleteApartment(it) }
            }

            btnAddRoom.setOnClick {
                navigate(
                    EditApartmentFragmentDirections.actionToAddCommonDialog(
                        AddCommonDialogFragment.ACTION_ADD_ROOM_NAME,
                        apartment = viewModel.apartment.value
                    )
                )
            }

            changeNameLayout.setOnClick {
                navigate(
                    EditApartmentFragmentDirections.actionToChangeNameDialog(
                        ChangeNameDialogFragment.ACTION_CHANGE_APARTMENT_NAME,
                        apartment = viewModel.apartment.value
                    )
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadApartmentDetail(args.apartment)
    }
}