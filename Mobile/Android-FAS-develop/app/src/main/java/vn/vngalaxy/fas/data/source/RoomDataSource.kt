package vn.vngalaxy.fas.data.source

import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.model.Room

interface RoomDataSource {
    interface Remote {

        suspend fun getRoomsWithFloor(floor: Floor): List<Room>

        suspend fun getRoomsWithApartment(apartment: Apartment): List<Room>

        suspend fun getRoomDetail(room: Room): Room

        suspend fun addRoom(room: Room)

        suspend fun changeRoomName(room: Room)

        suspend fun deleteRoom(room: Room)
    }
}