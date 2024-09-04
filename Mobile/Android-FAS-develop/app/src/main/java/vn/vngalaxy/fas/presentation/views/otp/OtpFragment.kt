package vn.vngalaxy.fas.presentation.views.otp

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.vngalaxy.fas.R
import vn.vngalaxy.fas.databinding.FragmentOtpBinding
import vn.vngalaxy.fas.model.Role
import vn.vngalaxy.fas.presentation.base.BaseFragment
import vn.vngalaxy.fas.presentation.views.main.MainActivity
import vn.vngalaxy.fas.shared.extensions.navigateAndClearStack
import vn.vngalaxy.fas.shared.extensions.navigateBack
import vn.vngalaxy.fas.shared.extensions.setOnClick
import vn.vngalaxy.fas.shared.extensions.updateAppearance
import vn.vngalaxy.fas.shared.scheduler.DataResult
import vn.vngalaxy.fas.shared.widget.showFailedSnackbar

class OtpFragment : BaseFragment<FragmentOtpBinding, OtpViewModel>() {
    override val viewModel: OtpViewModel by viewModel()
    private val args: OtpFragmentArgs by navArgs()

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentOtpBinding.inflate(inflater)

    override fun setUpView() {
        viewBinding.lifecycleOwner = this.viewLifecycleOwner
        viewBinding.otpViewModel = viewModel
    }

    override fun bindView() {
        viewBinding.otpNote.text = getString(R.string.opt_note, args.phoneNumber)
    }

    override fun registerLiveData() {
        super.registerLiveData()
        with(viewModel) {
            createSessionResult.observe(viewLifecycleOwner) {
                when (it) {
                    is DataResult.Success -> {
                        MainActivity.setupBottomNavigationView(user.role)

                        if (user.role == Role.ADMIN.value) {
                            navigateAndClearStack(OtpFragmentDirections.actionToSensor())
                        } else {
                            //TODO
                        }
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

            btnConfirmOtp.setOnClick {
                val otp = viewBinding.optEditText.text.toString()
                viewModel.createSession(otp)
            }

            optEditText.addTextChangedListener(textWatcher)
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val otp = viewBinding.optEditText.text.toString()
            viewBinding.btnConfirmOtp.updateAppearance(otp.length == 6)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }
}