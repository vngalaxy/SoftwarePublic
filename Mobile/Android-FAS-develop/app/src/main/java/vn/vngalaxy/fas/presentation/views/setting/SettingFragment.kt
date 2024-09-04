package vn.vngalaxy.fas.presentation.views.setting

import android.view.LayoutInflater
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.vngalaxy.fas.databinding.FragmentSettingBinding
import vn.vngalaxy.fas.presentation.base.BaseFragment
import vn.vngalaxy.fas.shared.extensions.navigateAndClearStack
import vn.vngalaxy.fas.shared.extensions.setOnClick
import vn.vngalaxy.fas.shared.scheduler.DataResult
import vn.vngalaxy.fas.shared.widget.showFailedSnackbar

class SettingFragment : BaseFragment<FragmentSettingBinding, SettingViewModel>() {
    override val viewModel: SettingViewModel by viewModel()

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentSettingBinding.inflate(inflater)

    override fun setUpView() {}

    override fun bindView() {}

    override fun registerLiveData() {
        super.registerLiveData()

        with(viewModel) {
            logoutResult.observe(viewLifecycleOwner) {
                when (it) {
                    is DataResult.Success -> {
                        val action = SettingFragmentDirections.actionToLogin()
                        navigateAndClearStack(action)
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
            logoutLayout.setOnClick {
                viewModel.logout()
            }
        }
    }
}