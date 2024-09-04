package vn.vngalaxy.fas.presentation.views.building

import android.view.LayoutInflater
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.vngalaxy.fas.databinding.FragmentEditBuildingBinding
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.presentation.base.BaseFragment
import vn.vngalaxy.fas.presentation.views.add_common.AddCommonDialogFragment
import vn.vngalaxy.fas.presentation.views.building.adapter.FloorAdapter
import vn.vngalaxy.fas.shared.extensions.navigate
import vn.vngalaxy.fas.shared.extensions.navigateBack
import vn.vngalaxy.fas.shared.extensions.setOnClick

class EditBuildingFragment : BaseFragment<FragmentEditBuildingBinding, EditBuildingViewModel>() {
    override val viewModel: EditBuildingViewModel by viewModel()
    private var floorAdapter = FloorAdapter(::onClickItem)

    private fun onClickItem(floor: Floor) {
        navigate(EditBuildingFragmentDirections.actionToEditFloor(floor))
    }

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentEditBuildingBinding.inflate(inflater)

    override fun setUpView() {
        viewBinding.floorList.adapter = floorAdapter
    }

    override fun bindView() {
        with(viewBinding) {
            buildingNameTxt.text = viewModel.buildingName
        }
    }

    override fun registerLiveData() {
        super.registerLiveData()

        with(viewModel) {
            floors.observe(viewLifecycleOwner) {
                floorAdapter.submitList(it)
            }
        }
    }

    override fun handleEvent() {
        with(viewBinding) {
            btnBack.setOnClick {
                navigateBack()
            }

            btnAddFloor.setOnClick {
                navigate(EditBuildingFragmentDirections.actionToAddCommonDialog(AddCommonDialogFragment.ACTION_ADD_FLOOR_NAME))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadAllFloors()
    }
}