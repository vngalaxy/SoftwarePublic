package vn.vngalaxy.fas.data.repository

import vn.vngalaxy.fas.data.source.RoomDataSource
import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.shared.extensions.handleException
import vn.vngalaxy.fas.shared.scheduler.DataResult

class RoomRepositoryImpl(
    private val remote: RoomDataSource.Remote,
) : RoomRepository {
    override suspend fun getRoomsWithFloor(floor: Floor): DataResult<List<Room>> {
        return try {
            val response = remote.getRoomsWithFloor(floor)
            DataResult.Success(response)
        } catch (e: Exception) {
            DataResult.Error(e.handleException())
        }
    }

    override suspend fun getRoomsWithApartment(apartment: Apartment): DataResult<List<Room>> {
        return try {
            val response = remote.getRoomsWithApartment(apartment)
            DataResult.Success(response)
        } catch (e: Exception) {
            DataResult.Error(e.handleException())
        }
    }

    override suspend fun getRoomDetail(room: Room): DataResult<Room> {
        return try {
            val response = remote.getRoomDetail(room)
            DataResult.Success(response)
        } catch (e: Exception) {
            DataResult.Error(e.handleException())
        }
    }

    override suspend fun addRoom(room: Room): DataResult<Unit> {
        return try {
            val response = remote.addRoom(room)
            DataResult.Success(response)
        } catch (e: Exception) {
            DataResult.Error(e.handleException())
        }
    }

    override suspend fun changeRoomName(room: Room): DataResult<Unit> {
        return try {
            val response = remote.changeRoomName(room)
            DataResult.Success(response)
        } catch (e: Exception) {
            DataResult.Error(e.handleException())
        }
    }

    override suspend fun deleteRoom(room: Room): DataResult<Unit> {
        return try {
            val response = remote.deleteRoom(room)
            DataResult.Success(response)
        } catch (e: Exception) {
            DataResult.Error(e.handleException())
        }
    }
}