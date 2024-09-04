package vn.vngalaxy.fas.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.appwrite.models.Document
import kotlinx.parcelize.Parcelize
import vn.vngalaxy.fas.model.base.BaseResponse
import vn.vngalaxy.fas.shared.constant.Constant.STRING_EMPTY

@Parcelize
data class Room(
    @SerializedName("\$id")
    val id: String,
    @SerializedName("name")
    val name: String? = STRING_EMPTY,
    @SerializedName("sensor")
    val sensors: List<Sensor> = listOf(),
    @SerializedName("user")
    val users: List<User?> = listOf(),
    @SerializedName("apartment")
    val apartment: Apartment?,
) : Parcelable {
    override fun toString(): String {
        return name.toString()
    }

    companion object : BaseResponse<Room, Map<String, Any>>() {
        override fun fromDocument(document: Document<Map<String, Any>>): Room {
            val data = document.data
            return fromMap(data)
        }

        override fun fromMap(data: Map<String, Any>): Room {
            return Room(
                id = data["\$id"]?.toString() ?: STRING_EMPTY,
                name = data["name"]?.toString() ?: STRING_EMPTY,
                sensors = (data["sensor"] as? List<Map<String, Any>>)?.map {
                    Sensor.fromMap(it)
                } ?: emptyList(),
                users = (data["user"] as? List<Map<String, Any>>)?.map {
                    User.fromMap(it)
                } ?: emptyList(),
                apartment = data["apartment"]?.let { Apartment.fromMap(it as Map<String, Any>) }
            )
        }
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "apartment" to apartment?.id,
        )
    }
}
