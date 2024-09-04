package vn.vngalaxy.fas.model

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class QRDevice(
    val sensorName: String,
    val devEUI: String,
    val applicationID: String,
    val deviceProfileID: String,
    val nwkKey: String,
) : Parcelable {
    companion object {
        fun fromJson(json: String): QRDevice {
            val gson = Gson()
            return gson.fromJson(json, QRDevice::class.java)
        }
    }
}
