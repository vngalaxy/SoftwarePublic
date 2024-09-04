package vn.vngalaxy.fas.shared.widget.dialogManager

interface DialogManager {
    fun showLoading()

    fun hideLoading(delay: Long)

    fun onRelease()
}

