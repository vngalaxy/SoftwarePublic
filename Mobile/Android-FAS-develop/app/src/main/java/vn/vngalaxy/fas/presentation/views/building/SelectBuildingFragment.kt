package vn.vngalaxy.fas.presentation.views.building

import android.view.LayoutInflater
import androidx.navigation.NavOptions
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.vngalaxy.fas.R
import vn.vngalaxy.fas.databinding.FragmentSelectBuildingBinding
import vn.vngalaxy.fas.model.Building
import vn.vngalaxy.fas.presentation.base.BaseFragment
import vn.vngalaxy.fas.presentation.views.building.adapter.BuildingAdapter
import vn.vngalaxy.fas.shared.extensions.navigate
import vn.vngalaxy.fas.shared.extensions.navigateBack
import vn.vngalaxy.fas.shared.extensions.setOnClick

class SelectBuildingFragment : BaseFragment<FragmentSelectBuildingBinding, SelectBuildingViewModel>() {
    override val viewModel: SelectBuildingViewModel by viewModel()
    private val args: SelectBuildingFragmentArgs by navArgs()

    private var buildingAdapter: BuildingAdapter = BuildingAdapter(::onClickItem)

    private fun onClickItem(item: Any) {
        when (item) {
            is Building -> {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.login, true)
                    .build()

                navigate(
                    SelectBuildingFragmentDirections.actionToLogin(
                        building = item,
                        phoneNumber = args.phoneNumber
                    ), navOptions
                )
            }

        }
    }

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentSelectBuildingBinding.inflate(inflater)

    override fun setUpView() {
        viewBinding.lifecycleOwner = this.viewLifecycleOwner
        viewBinding.viewModel = viewModel
    }

    override fun bindView() {
        viewBinding.buildingList.adapter = buildingAdapter
    }

    override fun registerLiveData() {
        super.registerLiveData()
        with(viewModel) {
            buildings.observe(viewLifecycleOwner) {
                buildingAdapter.submitList(it)
            }
        }
    }

    override fun handleEvent() {
        with(viewBinding) {
            btnBack.setOnClick {
                navigateBack()
            }
        }
    }
}