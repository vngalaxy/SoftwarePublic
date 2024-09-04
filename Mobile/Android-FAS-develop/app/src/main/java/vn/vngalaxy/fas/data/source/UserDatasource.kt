package vn.vngalaxy.fas.data.source

import io.appwrite.models.Session
import vn.vngalaxy.fas.model.User

interface UserDatasource {
    interface Local {
        var id: String

        var sessionId: String

        var name: String

        var phoneNumber: String

        var deviceToken: String

        var role: String

        var isLogin: Boolean

        var isSeenOnboarding: Boolean

        suspend fun resetLogout()

        suspend fun saveUserInfo(role: String, isLogin: Boolean, sessionId: String)
    }

    interface Remote {
        suspend fun checkUserExists(phoneNumber: String): Boolean

        suspend fun createPhoneToken(phoneNumber: String): String

        suspend fun createPhoneVerification(userId: String, secret: String): Session

        suspend fun updateDeviceToken(token: String, userId: String)

        suspend fun addUser(user: User)

        suspend fun changeRoomOfUser(user: User)

        suspend fun changeUserInfo(user: User)

        suspend fun logout(sessionId: String)

    }
}