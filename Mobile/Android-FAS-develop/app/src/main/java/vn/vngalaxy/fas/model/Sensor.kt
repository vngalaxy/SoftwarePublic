package vn.vngalaxy.fas.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.appwrite.models.Document
import kotlinx.parcelize.Parcelize
import vn.vngalaxy.fas.model.base.BaseResponse
import vn.vngalaxy.fas.shared.constant.Constant.STRING_EMPTY
import vn.vngalaxy.fas.shared.extensions.toDoubleOrDefault

@Parcelize
data class Sensor(
    @SerializedName("\$id", alternate = ["id"])
    val id: String,
    @SerializedName("name")
    val name: String? = STRING_EMPTY,
    @SerializedName("time")
    val time: String? = STRING_EMPTY,
    @SerializedName("timeTurnOn")
    val timeTurnOn: String? = STRING_EMPTY,
    @SerializedName("battery")
    val battery: Double? = 0.0,
    @SerializedName("type")
    val type: String? = SensorType.NONE.value,
    @SerializedName("value")
    val value: Double = 0.0,
    @SerializedName("status")
    val status: String? = SensorStatus.NULL.value,
    @SerializedName("room")
    val room: Room?,
) : Parcelable {
    companion object : BaseResponse<Sensor, Map<String, Any>>() {
        override fun fromDocument(document: Document<Map<String, Any>>): Sensor {
            val data = document.data
            return fromMap(data)
        }

        override fun fromMap(data: Map<String, Any>): Sensor {
            return Sensor(
                id = data["\$id"]?.toString() ?: STRING_EMPTY,
                name = data["name"]?.toString() ?: STRING_EMPTY,
                time = data["time"]?.toString() ?: STRING_EMPTY,
                timeTurnOn = data["timeTurnOn"]?.toString() ?: STRING_EMPTY,
                battery = data["battery"].toDoubleOrDefault(),
                type = data["type"]?.toString() ?: SensorType.NONE.value,
                value = data["value"].toDoubleOrDefault(),
                status = data["status"]?.toString() ?: SensorStatus.NULL.value,
                room = data["room"]?.let { Room.fromMap(it as Map<String, Any>) }
            )
        }
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "time" to time,
            "timeTurnOn" to timeTurnOn,
            "battery" to battery,
            "type" to type,
            "value" to value,
            "status" to status,
            "room" to room?.id
        )
    }
}