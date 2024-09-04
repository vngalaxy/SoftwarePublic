package vn.vngalaxy.fas.presentation.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.window.OnBackInvokedDispatcher
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import vn.vngalaxy.fas.shared.extensions.navigateBack
import vn.vngalaxy.fas.shared.widget.dialogManager.DialogManager
import vn.vngalaxy.fas.shared.widget.dialogManager.DialogManagerImpl

abstract class BaseDialogFragment<viewBinding : ViewBinding, viewModel : BaseViewModel>(@LayoutRes contentLayoutId: Int) :
    DialogFragment(contentLayoutId) {
    protected abstract val viewModel: viewModel

    private var _viewBinding: viewBinding? = null
    protected val viewBinding get() = _viewBinding!!
    private var dialogManager: DialogManager? = null

    init {
        isCancelable = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _viewBinding = inflateViewBinding(inflater)
        return viewBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dialogManager = DialogManagerImpl(getContext())
    }

    abstract fun inflateViewBinding(inflater: LayoutInflater): viewBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = object : Dialog(requireContext(), theme) {
            override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
                navigateBack()
                return super.getOnBackInvokedDispatcher()
            }
        }

        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
        return dialog
    }

    fun showLoading() {
        dialogManager?.showLoading()
    }

    fun hideLoading() {
        dialogManager?.hideLoading(0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        registerLiveData()
        handleEvent()
    }

    abstract fun bindView()

    abstract fun handleEvent()

    open fun registerLiveData() {
        viewModel.run {
            isLoading.observe(viewLifecycleOwner) {
                if (it) showLoading() else hideLoading()
            }
        }
    }
}
