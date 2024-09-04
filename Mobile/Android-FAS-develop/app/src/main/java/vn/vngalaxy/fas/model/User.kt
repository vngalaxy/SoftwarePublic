package vn.vngalaxy.fas.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.appwrite.models.Document
import kotlinx.parcelize.Parcelize
import vn.vngalaxy.fas.model.base.BaseResponse
import vn.vngalaxy.fas.shared.constant.Constant.STRING_EMPTY

@Parcelize
data class User(
    @SerializedName("\$id")
    val id: String,

    @SerializedName("name")
    val name: String? = STRING_EMPTY,

    @SerializedName("phone")
    val phoneNumber: String? = STRING_EMPTY,

    @SerializedName("deviceToken")
    val deviceToken: String? = STRING_EMPTY,

    @SerializedName("room")
    val room: Room?,
) : Parcelable {
    companion object : BaseResponse<User, Map<String, Any>>() {
        override fun fromDocument(document: Document<Map<String, Any>>): User {
            val data = document.data
            return fromMap(data)
        }

        override fun fromMap(data: Map<String, Any>): User {
            return User(
                id = data["\$id"]?.toString() ?: STRING_EMPTY,
                name = data["name"]?.toString() ?: STRING_EMPTY,
                phoneNumber = data["phone"]?.toString() ?: STRING_EMPTY,
                deviceToken = data["deviceToken"]?.toString() ?: STRING_EMPTY,
                room = data["room"]?.let { Room.fromMap(it as Map<String, Any>) }
            )
        }
    }
}