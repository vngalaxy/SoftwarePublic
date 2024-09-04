package vn.vngalaxy.fas.data.source.remote

import okhttp3.ResponseBody
import retrofit2.Response
import vn.vngalaxy.fas.data.repository.MainBuildingRepository
import vn.vngalaxy.fas.data.source.SensorDataSource
import vn.vngalaxy.fas.data.source.remote.api.ChirpStackApi
import vn.vngalaxy.fas.data.source.remote.appwrite.AppwriteManager
import vn.vngalaxy.fas.model.ChirpStackDevice
import vn.vngalaxy.fas.model.ChirpStackDeviceBody
import vn.vngalaxy.fas.model.ChirpStackDeviceKeys
import vn.vngalaxy.fas.model.ChirpStackDeviceKeysBody
import vn.vngalaxy.fas.model.Sensor
import vn.vngalaxy.fas.shared.constant.Constant.ROOM_ATTRIBUTE

class SensorRemoteImpl(
    private val appwriteManager: AppwriteManager,
    private val building: MainBuildingRepository,
    private val chirpStackApi: ChirpStackApi,
) : SensorDataSource.Remote {
    override suspend fun getSensorDetail(sensor: Sensor): Sensor =
        Sensor.fromDocument(
            appwriteManager.getDatabase().getDocument(
                databaseId = building.buildingDatabaseId,
                collectionId = building.sensorCollectionId,
                documentId = sensor.id
            )
        )

    override suspend fun changeRoomOfSensor(sensor: Sensor) {
        appwriteManager.getDatabase().updateDocument(
            databaseId = building.buildingDatabaseId,
            collectionId = building.sensorCollectionId,
            documentId = sensor.id,
            data = mapOf(
                ROOM_ATTRIBUTE to sensor.room!!.id
            )
        )
    }

    override suspend fun addSensor(sensor: Sensor) {
        appwriteManager.getDatabase().createDocument(
            databaseId = building.buildingDatabaseId,
            collectionId = building.sensorCollectionId,
            documentId = sensor.id,
            data = sensor.toMap()
        )
    }

    override suspend fun createChirpStackDevice(chirpStackDevice: ChirpStackDevice): Response<ResponseBody> =
        chirpStackApi.createDevice(ChirpStackDeviceBody(chirpStackDevice))

    override suspend fun activeChirpStackDevice(devEUI: String, chirpStackDeviceKeys: ChirpStackDeviceKeys): Response<ResponseBody> =
        chirpStackApi.activeDevice(devEUI, ChirpStackDeviceKeysBody(chirpStackDeviceKeys))

}