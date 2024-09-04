package vn.vngalaxy.fas.data.source.remote

import vn.vngalaxy.fas.data.source.BuildingDataSource
import vn.vngalaxy.fas.data.source.remote.appwrite.AppwriteManager
import vn.vngalaxy.fas.model.Building
import vn.vngalaxy.fas.shared.constant.Constant.GENERAL_BUILDING_COLLECTION_ID
import vn.vngalaxy.fas.shared.constant.Constant.GENERAL_DATABASE_ID

class BuildingRemoteImpl(
    private val appwriteManager: AppwriteManager,
) : BuildingDataSource.Remote {
    override suspend fun getAllBuildings(): List<Building> =
        Building.fromDocumentList(
            appwriteManager.getDatabase().listDocuments(
                databaseId = GENERAL_DATABASE_ID,
                collectionId = GENERAL_BUILDING_COLLECTION_ID,
            ).documents
        )

    override suspend fun getBuildingById(id: String): Building? = null

    override suspend fun insertBuilding(building: Building) = Unit

    override suspend fun deleteBuilding(building: Building) = Unit

    override suspend fun deleteAllBuildings() = Unit
}