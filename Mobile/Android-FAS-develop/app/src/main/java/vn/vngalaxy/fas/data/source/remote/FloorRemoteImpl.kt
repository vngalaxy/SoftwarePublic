package vn.vngalaxy.fas.data.source.remote

import vn.vngalaxy.fas.data.repository.MainBuildingRepository
import vn.vngalaxy.fas.data.source.FloorDataSource
import vn.vngalaxy.fas.data.source.remote.appwrite.AppwriteManager
import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.shared.constant.Constant.NAME_ATTRIBUTE

class FloorRemoteImpl(
    private val appwriteManager: AppwriteManager,
    private val building: MainBuildingRepository,
) : FloorDataSource.Remote {
    override suspend fun getAllFloors(): List<Floor> =
        Floor.fromDocumentList(
            appwriteManager.getDatabase().listDocuments(
                databaseId = building.buildingDatabaseId,
                collectionId = building.floorCollectionId,
            ).documents
        )

    override suspend fun getFloorDetail(floor: Floor): Floor =
        Floor.fromDocument(
            appwriteManager.getDatabase().getDocument(
                databaseId = building.buildingDatabaseId,
                collectionId = building.floorCollectionId,
                documentId = floor.id
            )
        )

    override suspend fun getApartmentDetail(apartment: Apartment): Apartment =
        Apartment.fromDocument(
            appwriteManager.getDatabase().getDocument(
                databaseId = building.buildingDatabaseId,
                collectionId = building.apartmentCollectionId,
                documentId = apartment.id
            )
        )

    override suspend fun addFloor(floor: Floor) {
        appwriteManager.getDatabase().createDocument(
            databaseId = building.buildingDatabaseId,
            collectionId = building.floorCollectionId,
            documentId = floor.id,
            data = floor.toMap()
        )
    }

    override suspend fun addApartment(apartment: Apartment) {
        appwriteManager.getDatabase().createDocument(
            databaseId = building.buildingDatabaseId,
            collectionId = building.apartmentCollectionId,
            documentId = apartment.id,
            data = apartment.toMap()
        )
    }

    override suspend fun changeFloorName(floor: Floor) {
        appwriteManager.getDatabase().updateDocument(
            databaseId = building.buildingDatabaseId,
            collectionId = building.floorCollectionId,
            documentId = floor.id,
            data = mapOf(
                NAME_ATTRIBUTE to floor.name,
            )
        )
    }

    override suspend fun changeApartmentName(apartment: Apartment) {
        appwriteManager.getDatabase().updateDocument(
            databaseId = building.buildingDatabaseId,
            collectionId = building.apartmentCollectionId,
            documentId = apartment.id,
            data = mapOf(
                NAME_ATTRIBUTE to apartment.name,
            )
        )
    }

    override suspend fun deleteFloor(floor: Floor) {
        appwriteManager.getDatabase().deleteDocument(
            databaseId = building.buildingDatabaseId,
            collectionId = building.floorCollectionId,
            documentId = floor.id,
        )
    }

    override suspend fun deleteApartment(apartment: Apartment) {
        appwriteManager.getDatabase().deleteDocument(
            databaseId = building.buildingDatabaseId,
            collectionId = building.apartmentCollectionId,
            documentId = apartment.id,
        )
    }
}