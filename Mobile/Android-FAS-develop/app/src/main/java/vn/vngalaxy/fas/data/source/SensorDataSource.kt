package vn.vngalaxy.fas.data.source

import okhttp3.ResponseBody
import retrofit2.Response
import vn.vngalaxy.fas.model.ChirpStackDevice
import vn.vngalaxy.fas.model.ChirpStackDeviceKeys
import vn.vngalaxy.fas.model.Sensor

interface SensorDataSource {
    interface Remote {
        suspend fun getSensorDetail(sensor: Sensor): Sensor

        suspend fun changeRoomOfSensor(sensor: Sensor)

        suspend fun addSensor(sensor: Sensor)

        suspend fun createChirpStackDevice(chirpStackDevice: ChirpStackDevice): Response<ResponseBody>

        suspend fun activeChirpStackDevice(devEUI: String, chirpStackDeviceKeys: ChirpStackDeviceKeys): Response<ResponseBody>
    }
}