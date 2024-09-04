package vn.vngalaxy.fas.data.repository

import vn.vngalaxy.fas.model.ChirpStackDevice
import vn.vngalaxy.fas.model.ChirpStackDeviceKeys
import vn.vngalaxy.fas.model.Sensor
import vn.vngalaxy.fas.shared.scheduler.DataResult

interface SensorRepository {

    suspend fun getSensorDetail(sensor: Sensor): DataResult<Sensor>

    suspend fun changeRoomOfSensor(sensor: Sensor): DataResult<Unit>

    suspend fun addSensor(sensor: Sensor, chirpStackDevice: ChirpStackDevice, chirpStackDeviceKeys: ChirpStackDeviceKeys): DataResult<Unit>
}