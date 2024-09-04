package vn.vngalaxy.fas.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.appwrite.models.Document
import kotlinx.parcelize.Parcelize
import vn.vngalaxy.fas.model.base.BaseResponse
import vn.vngalaxy.fas.shared.constant.Constant.STRING_EMPTY

@Parcelize
data class Apartment(
    @SerializedName("\$id")
    val id: String,
    @SerializedName("name")
    val name: String? = STRING_EMPTY,
    @SerializedName("room")
    val rooms: List<Room> = listOf(),
    @SerializedName("floor")
    val floor: Floor? = null,
) : Parcelable {
    override fun toString(): String {
        return name.toString()
    }

    companion object : BaseResponse<Apartment, Map<String, Any>>() {
        override fun fromDocument(document: Document<Map<String, Any>>): Apartment {
            val data = document.data
            return fromMap(data)
        }

        override fun fromMap(data: Map<String, Any>): Apartment {
            return Apartment(
                id = data["\$id"]?.toString() ?: STRING_EMPTY,
                name = data["name"]?.toString() ?: STRING_EMPTY,
                rooms = (data["room"] as? List<Map<String, Any>>)?.map {
                    Room.fromMap(it)
                } ?: emptyList(),
                floor = data["floor"]?.let { Floor.fromMap(it as Map<String, Any>) }
            )
        }
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "floor" to floor?.id,
        )
    }
}
