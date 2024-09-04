package vn.vngalaxy.fas.data.repository

import vn.vngalaxy.fas.data.source.MainBuildingDatasource

class MainBuildingRepositoryImpl(
    private val mainBuildingDatasource: MainBuildingDatasource.Local
) : MainBuildingRepository {
    override var projectId: String
        get() = mainBuildingDatasource.projectId
        set(value) {
            mainBuildingDatasource.projectId = value
        }

    override var name: String
        get() = mainBuildingDatasource.name
        set(value) {
            mainBuildingDatasource.name = value
        }

    override var endPointUrl: String
        get() = mainBuildingDatasource.endPointUrl
        set(value) {
            mainBuildingDatasource.endPointUrl = value
        }

    override var buildingDatabaseId: String
        get() = mainBuildingDatasource.buildingDatabaseId
        set(value) {
            mainBuildingDatasource.buildingDatabaseId = value
        }

    override var userCollectionId: String
        get() = mainBuildingDatasource.userCollectionId
        set(value) {
            mainBuildingDatasource.userCollectionId = value
        }

    override var floorCollectionId: String
        get() = mainBuildingDatasource.floorCollectionId
        set(value) {
            mainBuildingDatasource.floorCollectionId = value
        }

    override var apartmentCollectionId: String
        get() = mainBuildingDatasource.apartmentCollectionId
        set(value) {
            mainBuildingDatasource.apartmentCollectionId = value
        }

    override var roomCollectionId: String
        get() = mainBuildingDatasource.roomCollectionId
        set(value) {
            mainBuildingDatasource.roomCollectionId = value
        }

    override var sensorCollectionId: String
        get() = mainBuildingDatasource.sensorCollectionId
        set(value) {
            mainBuildingDatasource.sensorCollectionId = value
        }

    override var userNotificationCollectionId: String
        get() = mainBuildingDatasource.userNotificationCollectionId
        set(value) {
            mainBuildingDatasource.userNotificationCollectionId = value
        }

    override var sensorNotificationCollectionId: String
        get() = mainBuildingDatasource.sensorNotificationCollectionId
        set(value) {
            mainBuildingDatasource.sensorNotificationCollectionId = value
        }

    override var notificationCollectionId: String
        get() = mainBuildingDatasource.notificationCollectionId
        set(value) {
            mainBuildingDatasource.notificationCollectionId = value
        }

    override var caseFireLocationCollectionId: String
        get() = mainBuildingDatasource.caseFireLocationCollectionId
        set(value) {
            mainBuildingDatasource.caseFireLocationCollectionId = value
        }

    override var cornerLocationBuilding: List<String>
        get() = mainBuildingDatasource.cornerLocationBuilding
        set(value) {
            mainBuildingDatasource.cornerLocationBuilding = value
        }
}
