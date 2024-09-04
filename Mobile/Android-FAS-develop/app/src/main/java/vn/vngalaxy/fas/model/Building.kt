package vn.vngalaxy.fas.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import io.appwrite.models.Document
import kotlinx.parcelize.Parcelize
import vn.vngalaxy.fas.model.base.BaseResponse
import vn.vngalaxy.fas.shared.constant.Constant.STRING_EMPTY
import vn.vngalaxy.fas.shared.utils.ConverterUntil

@Entity(tableName = "buildings")
@Parcelize
data class Building(
    @SerializedName("\$id")
    @PrimaryKey
    val id: String,

    @SerializedName("projectID")
    val projectId: String? = STRING_EMPTY,

    @SerializedName("name")
    val name: String? = STRING_EMPTY,

    @SerializedName("endPointUrl")
    val endPointUrl: String? = STRING_EMPTY,

    @SerializedName("buildingDatabaseID")
    val buildingDatabaseId: String? = STRING_EMPTY,

    @SerializedName("userCollectionID")
    val userCollectionId: String? = STRING_EMPTY,

    @SerializedName("floorCollectionID")
    val floorCollectionId: String? = STRING_EMPTY,

    @SerializedName("apartmentCollectionID")
    val apartmentCollectionId: String? = STRING_EMPTY,

    @SerializedName("roomCollectionID")
    val roomCollectionId: String? = STRING_EMPTY,

    @SerializedName("sensorCollectionID")
    val sensorCollectionId: String? = STRING_EMPTY,

    @SerializedName("userNotificationCollectionID")
    val userNotificationCollectionId: String? = STRING_EMPTY,

    @SerializedName("sensorNotificationCollectionID")
    val sensorNotificationCollectionId: String? = STRING_EMPTY,

    @SerializedName("notificationCollectionID")
    val notificationCollectionId: String? = STRING_EMPTY,

    @SerializedName("caseFireLocationCollectionID")
    val caseFireLocationCollectionId: String? = STRING_EMPTY,

    @SerializedName("cornerLocationBuilding")
    @TypeConverters(ConverterUntil::class)
    val cornerLocationBuilding: List<String>?,

    @SerializedName("numberPhoneManager")
    val numberPhoneManager: String? = STRING_EMPTY,

    @SerializedName("address")
    val address: String? = STRING_EMPTY,

    @SerializedName("\$createdAt")
    val createdAt: String? = STRING_EMPTY,

    @SerializedName("\$updatedAt")
    val updatedAt: String? = STRING_EMPTY,
) : Parcelable {
    companion object : BaseResponse<Building, Map<String, Any>>() {
        override fun fromDocument(document: Document<Map<String, Any>>): Building {
            val data = document.data
            return fromMap(data)
        }

        override fun fromMap(data: Map<String, Any>): Building {
            return Building(
                id = data["\$id"]?.toString() ?: STRING_EMPTY,
                projectId = data["projectID"]?.toString() ?: STRING_EMPTY,
                name = data["name"]?.toString() ?: STRING_EMPTY,
                endPointUrl = data["endPointUrl"]?.toString() ?: STRING_EMPTY,
                buildingDatabaseId = data["buildingDatabaseID"]?.toString() ?: STRING_EMPTY,
                userCollectionId = data["userCollectionID"]?.toString() ?: STRING_EMPTY,
                floorCollectionId = data["floorCollectionID"]?.toString() ?: STRING_EMPTY,
                apartmentCollectionId = data["apartmentCollectionID"]?.toString() ?: STRING_EMPTY,
                roomCollectionId = data["roomCollectionID"]?.toString() ?: STRING_EMPTY,
                sensorCollectionId = data["sensorCollectionID"]?.toString() ?: STRING_EMPTY,
                userNotificationCollectionId = data["userNotificationCollectionID"]?.toString() ?: STRING_EMPTY,
                sensorNotificationCollectionId = data["sensorNotificationCollectionID"]?.toString() ?: STRING_EMPTY,
                notificationCollectionId = data["notificationCollectionID"]?.toString() ?: STRING_EMPTY,
                caseFireLocationCollectionId = data["caseFireLocationCollectionID"]?.toString() ?: STRING_EMPTY,
                cornerLocationBuilding = data["cornerLocationBuilding"] as? List<String> ?: emptyList(),
                numberPhoneManager = data["numberPhoneManager"]?.toString() ?: STRING_EMPTY,
                address = data["address"]?.toString() ?: STRING_EMPTY,
                createdAt = data["\$createdAt"]?.toString() ?: STRING_EMPTY,
                updatedAt = data["\$updatedAt"]?.toString() ?: STRING_EMPTY
            )
        }
    }
}
