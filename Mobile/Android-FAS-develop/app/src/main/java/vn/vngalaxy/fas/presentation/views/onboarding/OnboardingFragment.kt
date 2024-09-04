package vn.vngalaxy.fas.presentation.views.onboarding

import android.view.LayoutInflater
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.vngalaxy.fas.databinding.FragmentOnboardingBinding
import vn.vngalaxy.fas.presentation.base.BaseFragment
import vn.vngalaxy.fas.shared.extensions.navigate


class OnboardingFragment : BaseFragment<FragmentOnboardingBinding, OnboardingViewModel>() {
    override val viewModel: OnboardingViewModel by viewModel()

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentOnboardingBinding.inflate(inflater)

    override fun setUpView() {}

    override fun bindView() {}

    override fun handleEvent() {
        viewBinding.button.setOnClickListener {
            navigate(OnboardingFragmentDirections.actionToLogin())
        }
    }
}