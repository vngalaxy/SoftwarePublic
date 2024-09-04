package vn.vngalaxy.fas.presentation.views.sensor

import android.Manifest
import android.content.pm.PackageManager
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.navOptions
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.vngalaxy.fas.R
import vn.vngalaxy.fas.databinding.FragmentQrScanDialogBinding
import vn.vngalaxy.fas.presentation.base.BaseFragment
import vn.vngalaxy.fas.shared.extensions.navigate
import vn.vngalaxy.fas.shared.extensions.navigateBack
import vn.vngalaxy.fas.shared.extensions.setOnClick
import vn.vngalaxy.fas.shared.scheduler.DataResult
import vn.vngalaxy.fas.shared.widget.showFailedSnackbar

class QrScanFragment : BaseFragment<FragmentQrScanDialogBinding, QrScanViewModel>() {
    override val viewModel: QrScanViewModel by viewModel()
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (!isGranted) {
            requireView().showFailedSnackbar("Cần có quyền truy cập máy ảnh để quét mã QR")
        } else {
            codeScanner?.startPreview()
        }
    }

    private var codeScanner: CodeScanner? = null

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentQrScanDialogBinding.inflate(inflater)

    override fun setUpView() {
        codeScanner = CodeScanner(requireContext(), viewBinding.codeScannerView)

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            codeScanner?.startPreview()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    override fun bindView() {
        setupCodeScanner()
    }

    private fun setupCodeScanner() {
        codeScanner?.camera = CodeScanner.CAMERA_BACK
        codeScanner?.formats = CodeScanner.ALL_FORMATS
        codeScanner?.autoFocusMode = AutoFocusMode.SAFE
        codeScanner?.scanMode = ScanMode.SINGLE
    }

    override fun registerLiveData() {
        with(viewModel) {
            qrCodeResult.observe(viewLifecycleOwner) {
                when (it) {
                    is DataResult.Success -> {
                        val action = QrScanFragmentDirections.actionToAddSensor(qrDevice = it.data)
                        navigate(action, navOptions {
                            popUpTo(R.id.add_sensor) { inclusive = true }
                        })
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

//            enterManuallyAction.setOnClick {
//                val action = QrScanFragmentDirections.actionToAddSensor()
//                navigate(action, navOptions {
//                    popUpTo(R.id.qr_scan) { inclusive = true }
//                })
//            }

            codeScanner?.decodeCallback = DecodeCallback {
                requireActivity().runOnUiThread {
                    viewModel.getQrData(it.text.trim())
                }
            }

            codeScanner?.errorCallback = ErrorCallback {
                requireActivity().runOnUiThread {
                    requireView().showFailedSnackbar("Camera initialization error: ${it.message}")
                }
            }
        }
    }

    override fun onPause() {
        codeScanner?.releaseResources()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        codeScanner?.releaseResources()
    }
}