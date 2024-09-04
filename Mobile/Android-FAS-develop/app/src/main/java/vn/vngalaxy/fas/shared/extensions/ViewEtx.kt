package vn.vngalaxy.fas.shared.extensions

import android.view.View

fun View.setOnClick(delayTime: Long = 400, method: () -> Unit) {
    this.setOnClickListener {
        this.isClickable = false
        method.invoke()
        this.postDelayed(
            {
                this.isClickable = true
            },
            delayTime,
        )
    }
}

fun View.updateAppearance(isEnable: Boolean) {
    this.isEnabled = isEnable
}