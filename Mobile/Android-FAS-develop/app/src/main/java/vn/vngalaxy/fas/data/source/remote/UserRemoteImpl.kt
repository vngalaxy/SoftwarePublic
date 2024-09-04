package vn.vngalaxy.fas.data.source.remote

import io.appwrite.ID
import io.appwrite.Query
import io.appwrite.models.Session
import vn.vngalaxy.fas.data.repository.MainBuildingRepository
import vn.vngalaxy.fas.data.source.UserDatasource
import vn.vngalaxy.fas.data.source.remote.appwrite.AppwriteManager
import vn.vngalaxy.fas.model.User
import vn.vngalaxy.fas.shared.constant.Constant.DEVICE_TOKEN_ATTRIBUTE
import vn.vngalaxy.fas.shared.constant.Constant.GENERAL_POINT_URL
import vn.vngalaxy.fas.shared.constant.Constant.GENERAL_PROJECT_ID
import vn.vngalaxy.fas.shared.constant.Constant.NAME_ATTRIBUTE
import vn.vngalaxy.fas.shared.constant.Constant.PHONE_NUMBER_ATTRIBUTE
import vn.vngalaxy.fas.shared.constant.Constant.ROOM_ATTRIBUTE

class UserRemoteImpl(
    private val appwriteManager: AppwriteManager,
    private val buildingRepository: MainBuildingRepository,
) : UserDatasource.Remote {
    override suspend fun checkUserExists(phoneNumber: String): Boolean = appwriteManager.getDatabase().listDocuments(
        databaseId = buildingRepository.buildingDatabaseId,
        collectionId = buildingRepository.userCollectionId,
        queries = listOf(
            Query.equal(PHONE_NUMBER_ATTRIBUTE, phoneNumber),
        )
    ).total > 0

    override suspend fun createPhoneToken(phoneNumber: String): String =
        appwriteManager.getAccount().createPhoneToken(
            ID.unique(), phoneNumber
        ).userId

    override suspend fun createPhoneVerification(userId: String, secret: String): Session =
        appwriteManager.getAccount().createSession(userId, secret)

    override suspend fun updateDeviceToken(token: String, userId: String) {
        appwriteManager.getDatabase().updateDocument(
            databaseId = buildingRepository.buildingDatabaseId,
            collectionId = buildingRepository.userCollectionId,
            documentId = userId,
            data = mapOf(DEVICE_TOKEN_ATTRIBUTE to token),
        )
    }

    override suspend fun addUser(user: User) {
        appwriteManager.getDatabase().createDocument(
            databaseId = buildingRepository.buildingDatabaseId,
            collectionId = buildingRepository.userCollectionId,
            documentId = user.id,
            data = mapOf(
                NAME_ATTRIBUTE to user.name,
                PHONE_NUMBER_ATTRIBUTE to user.phoneNumber,
                ROOM_ATTRIBUTE to arrayOf(user.room?.id)
            )
        )
    }

    override suspend fun changeRoomOfUser(user: User) {
        appwriteManager.getDatabase().updateDocument(
            databaseId = buildingRepository.buildingDatabaseId,
            collectionId = buildingRepository.userCollectionId,
            documentId = user.id,
            data = mapOf(
                ROOM_ATTRIBUTE to arrayOf(user.room?.id)
            )
        )
    }

    override suspend fun changeUserInfo(user: User) {
        appwriteManager.getDatabase().updateDocument(
            databaseId = buildingRepository.buildingDatabaseId,
            collectionId = buildingRepository.userCollectionId,
            documentId = user.id,
            data = mapOf(
                NAME_ATTRIBUTE to user.name
            )
        )
    }

    override suspend fun logout(sessionId: String) {
        appwriteManager.getAccount().deleteSession(sessionId)
        appwriteManager.initClient(GENERAL_POINT_URL, GENERAL_PROJECT_ID)
    }
}