package vn.vngalaxy.fas.presentation.views.login

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.vngalaxy.fas.R
import vn.vngalaxy.fas.databinding.FragmentLoginBinding
import vn.vngalaxy.fas.model.Building
import vn.vngalaxy.fas.presentation.base.BaseFragment
import vn.vngalaxy.fas.shared.extensions.isValidPhoneNumber
import vn.vngalaxy.fas.shared.extensions.navigate
import vn.vngalaxy.fas.shared.extensions.setOnClick
import vn.vngalaxy.fas.shared.extensions.updateAppearance
import vn.vngalaxy.fas.shared.scheduler.DataResult
import vn.vngalaxy.fas.shared.widget.showFailedSnackbar

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    override val viewModel: LoginViewModel by viewModel()
    private val args: LoginFragmentArgs by navArgs()

    private var selectedBuilding: Building? = null
    private var phoneNumber = ""

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentLoginBinding.inflate(inflater)

    override fun setUpView() {
        viewBinding.lifecycleOwner = this.viewLifecycleOwner
        viewBinding.loginViewModel = viewModel
    }

    override fun bindView() {
        if (args.building != null) {
            selectedBuilding = args.building
            viewBinding.building.setText(args.building!!.name)
            viewBinding.btnLogin.updateAppearance(phoneNumber.isValidPhoneNumber())
        }

        if (args.phoneNumber != null) {
            phoneNumber = args.phoneNumber!!
            viewBinding.phoneNumberEdt.setText(args.phoneNumber)
            viewBinding.btnLogin.updateAppearance(phoneNumber.isValidPhoneNumber() && selectedBuilding != null)
        }
    }

    override fun registerLiveData() {
        super.registerLiveData()
        with(viewModel) {
            isUserExists.observe(viewLifecycleOwner) {
                when (it) {
                    is DataResult.Success -> {
                        if (!it.data) {
                            view?.showFailedSnackbar(getString(R.string.user_not_exist, selectedBuilding?.name.toString()))
                        } else {
                            viewModel.createPhoneToken(phoneNumber)
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

            createPhoneTokenResult.observe(viewLifecycleOwner) {
                when (it) {
                    is DataResult.Success -> {
                        if (it.data) {
                            navigate(LoginFragmentDirections.actionLoginToOtp(phoneNumber))
                        } else {
                            view?.showFailedSnackbar(getString(R.string.an_error_occurred))
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
            building.setOnClick {
                navigate(LoginFragmentDirections.actionToSelectBuilding(phoneNumber = phoneNumberEdt.text.toString()))
            }

            phoneNumberEdt.addTextChangedListener(textWatcher)

            btnLogin.setOnClick {
                selectedBuilding?.let { viewModel.saveMainBuilding(it, phoneNumber) }
            }
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            phoneNumber = viewBinding.phoneNumberEdt.text.toString().trim()
            viewBinding.btnLogin.updateAppearance(phoneNumber.isValidPhoneNumber() && selectedBuilding != null)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }
}