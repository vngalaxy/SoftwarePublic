package vn.vngalaxy.fas.data.repository

import vn.vngalaxy.fas.data.source.SensorDataSource
import vn.vngalaxy.fas.model.ChirpStackDevice
import vn.vngalaxy.fas.model.ChirpStackDeviceKeys
import vn.vngalaxy.fas.model.Sensor
import vn.vngalaxy.fas.shared.extensions.handleException
import vn.vngalaxy.fas.shared.scheduler.DataResult

class SensorRepositoryImpl(
    private val remote: SensorDataSource.Remote,
) : SensorRepository {
    override suspend fun getSensorDetail(sensor: Sensor): DataResult<Sensor> = try {
        val response = remote.getSensorDetail(sensor)
        DataResult.Success(response)
    } catch (e: Exception) {
        DataResult.Error(e.handleException())
    }

    override suspend fun changeRoomOfSensor(sensor: Sensor): DataResult<Unit> = try {
        val response = remote.changeRoomOfSensor(sensor)
        DataResult.Success(response)
    } catch (e: Exception) {
        DataResult.Error(e.handleException())
    }

    override suspend fun addSensor(
        sensor: Sensor,
        chirpStackDevice: ChirpStackDevice,
        chirpStackDeviceKeys: ChirpStackDeviceKeys
    ): DataResult<Unit> {
        try {
            val createDeviceResponse = remote.createChirpStackDevice(chirpStackDevice)
            if (!createDeviceResponse.isSuccessful) {
                return DataResult.Error(Exception("Failed to create ChirpStack device"))
            }

            val activateDeviceResponse = remote.activeChirpStackDevice(chirpStackDevice.devEUI, chirpStackDeviceKeys)
            if (!activateDeviceResponse.isSuccessful) {
                return DataResult.Error(Exception("Failed to activate ChirpStack device"))
            }

            val response = remote.addSensor(sensor)
            return DataResult.Success(response)
        } catch (e: Exception) {
            return DataResult.Error(e.handleException())
        }
    }
}