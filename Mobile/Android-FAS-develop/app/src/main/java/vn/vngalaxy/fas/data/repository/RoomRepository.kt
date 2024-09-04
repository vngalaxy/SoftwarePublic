package vn.vngalaxy.fas.data.repository

import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.shared.scheduler.DataResult

interface RoomRepository {
    suspend fun getRoomsWithFloor(floor: Floor): DataResult<List<Room>>

    suspend fun getRoomsWithApartment(apartment: Apartment): DataResult<List<Room>>

    suspend fun getRoomDetail(room: Room): DataResult<Room>

    suspend fun addRoom(room: Room): DataResult<Unit>

    suspend fun changeRoomName(room: Room): DataResult<Unit>

    suspend fun deleteRoom(room: Room): DataResult<Unit>
}