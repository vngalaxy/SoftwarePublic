package vn.vngalaxy.fas.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.appwrite.models.Document
import kotlinx.parcelize.Parcelize
import vn.vngalaxy.fas.model.base.BaseResponse
import vn.vngalaxy.fas.shared.constant.Constant.STRING_EMPTY

@Parcelize
data class Floor(
    @SerializedName("\$id")
    val id: String,
    @SerializedName("name")
    val name: String? = STRING_EMPTY,
    @SerializedName("apartment")
    val apartments: List<Apartment> = listOf(),
) : Parcelable {
    override fun toString(): String {
        return name.toString()
    }

    companion object : BaseResponse<Floor, Map<String, Any>>() {
        override fun fromDocument(document: Document<Map<String, Any>>): Floor {
            val data = document.data
            return fromMap(data)
        }

        override fun fromMap(data: Map<String, Any>): Floor {
            return Floor(
                id = data["\$id"]?.toString() ?: STRING_EMPTY,
                name = data["name"]?.toString() ?: STRING_EMPTY,
                apartments = (data["apartment"] as? List<Map<String, Any>>)?.map {
                    Apartment.fromMap(it)
                } ?: emptyList()
            )
        }
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
        )
    }
}

@Parcelize
data class ListFloor(
    val floors: List<Floor> = listOf(),
) : Parcelable

