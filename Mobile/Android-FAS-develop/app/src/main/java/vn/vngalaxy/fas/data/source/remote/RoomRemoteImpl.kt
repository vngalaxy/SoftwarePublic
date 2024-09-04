package vn.vngalaxy.fas.data.source.remote

import io.appwrite.Query
import vn.vngalaxy.fas.data.repository.MainBuildingRepository
import vn.vngalaxy.fas.data.source.RoomDataSource
import vn.vngalaxy.fas.data.source.remote.appwrite.AppwriteManager
import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.shared.constant.Constant
import vn.vngalaxy.fas.shared.constant.Constant.FLOOR_ATTRIBUTE

class RoomRemoteImpl(
    private val appwriteManager: AppwriteManager,
    private val building: MainBuildingRepository,
) : RoomDataSource.Remote {
    override suspend fun getRoomsWithFloor(floor: Floor): List<Room> =
        Apartment.fromDocumentList(
            appwriteManager.getDatabase().listDocuments(
                databaseId = building.buildingDatabaseId,
                collectionId = building.apartmentCollectionId,
                queries = listOf(Query.equal(FLOOR_ATTRIBUTE, floor.id))
            ).documents
        ).flatMap { it.rooms }

    override suspend fun getRoomsWithApartment(apartment: Apartment): List<Room> =
        Apartment.fromDocument(
            appwriteManager.getDatabase().getDocument(
                databaseId = building.buildingDatabaseId,
                collectionId = building.apartmentCollectionId,
                documentId = apartment.id
            )
        ).rooms

    override suspend fun getRoomDetail(room: Room): Room =
        Room.fromDocument(
            appwriteManager.getDatabase().getDocument(
                databaseId = building.buildingDatabaseId,
                collectionId = building.roomCollectionId,
                documentId = room.id
            )
        )

    override suspend fun addRoom(room: Room) {
        appwriteManager.getDatabase().createDocument(
            databaseId = building.buildingDatabaseId,
            collectionId = building.roomCollectionId,
            documentId = room.id,
            data = room.toMap()
        )
    }

    override suspend fun changeRoomName(room: Room) {
        appwriteManager.getDatabase().updateDocument(
            databaseId = building.buildingDatabaseId,
            collectionId = building.roomCollectionId,
            documentId = room.id,
            data = mapOf(
                Constant.NAME_ATTRIBUTE to room.name,
            )
        )
    }

    override suspend fun deleteRoom(room: Room) {
        appwriteManager.getDatabase().deleteDocument(
            databaseId = building.buildingDatabaseId,
            collectionId = building.roomCollectionId,
            documentId = room.id,
        )
    }
}