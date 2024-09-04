package vn.vngalaxy.fas.shared.widget

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import vn.vngalaxy.fas.R

fun View.showSuccessSnackbar(message: String) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.primary))
    snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.text_primary))
    snackbar.show()
}

fun View.showFailedSnackbar(message: String) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.red))
    snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.white))
    snackbar.show()
}

fun View.showAlertSnackbar(message: String) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.text_hint))
    snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.white))
    snackbar.show()
}
