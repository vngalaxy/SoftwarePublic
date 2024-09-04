package vn.vngalaxy.fas.data.repository

interface MainBuildingRepository {
    var projectId: String

    var name: String

    var endPointUrl: String

    var buildingDatabaseId: String

    var userCollectionId: String

    var floorCollectionId: String

    var apartmentCollectionId: String

    var roomCollectionId: String

    var sensorCollectionId: String

    var userNotificationCollectionId: String

    var sensorNotificationCollectionId: String

    var notificationCollectionId: String

    var caseFireLocationCollectionId: String

    var cornerLocationBuilding: List<String>
}