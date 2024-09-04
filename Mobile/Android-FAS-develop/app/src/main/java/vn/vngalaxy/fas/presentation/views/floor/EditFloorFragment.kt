package vn.vngalaxy.fas.presentation.views.floor

import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.vngalaxy.fas.databinding.FragmentEditFloorBinding
import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.presentation.base.BaseFragment
import vn.vngalaxy.fas.presentation.views.add_common.AddCommonDialogFragment
import vn.vngalaxy.fas.presentation.views.change_name.ChangeNameDialogFragment
import vn.vngalaxy.fas.presentation.views.floor.adapter.ApartmentAdapter
import vn.vngalaxy.fas.shared.extensions.navigate
import vn.vngalaxy.fas.shared.extensions.navigateBack
import vn.vngalaxy.fas.shared.extensions.setOnClick
import vn.vngalaxy.fas.shared.scheduler.DataResult
import vn.vngalaxy.fas.shared.widget.showFailedSnackbar

class EditFloorFragment : BaseFragment<FragmentEditFloorBinding, EditFloorViewModel>() {
    override val viewModel: EditFloorViewModel by viewModel()
    private val args: EditFloorFragmentArgs by navArgs()

    private var apartmentAdapter = ApartmentAdapter(::onItemClick)

    private fun onItemClick(apartment: Apartment) {
        navigate(EditFloorFragmentDirections.actionToEditApartment(apartment))
    }

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentEditFloorBinding.inflate(inflater)

    override fun setUpView() {
        viewBinding.apartmentList.adapter = apartmentAdapter
    }

    override fun bindView() {}

    override fun registerLiveData() {
        super.registerLiveData()

        with(viewModel) {
            floor.observe(viewLifecycleOwner) {
                viewBinding.floorTitleTxt.text = it.name
                viewBinding.floorNameTxt.text = it.name
                apartmentAdapter.submitList(it.apartments)
                viewBinding.btnDeleteFloor.visibility = if (it.apartments.isEmpty()) View.VISIBLE else View.GONE
            }

            deleteFloorResult.observe(viewLifecycleOwner) {
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

            btnDeleteFloor.setOnClick {
                viewModel.floor.value?.let { viewModel.deleteFloor(it) }
            }

            btnAddApartment.setOnClick {
                navigate(
                    EditFloorFragmentDirections.actionToAddCommonDialog(
                        AddCommonDialogFragment.ACTION_ADD_APARTMENT_NAME,
                        floor = viewModel.floor.value
                    )
                )
            }

            changeNameLayout.setOnClick {
                navigate(
                    EditFloorFragmentDirections.actionToChangeNameDialog(
                        ChangeNameDialogFragment.ACTION_CHANGE_FLOOR_NAME,
                        floor = viewModel.floor.value
                    )
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFloorDetail(args.floor)
    }
}


