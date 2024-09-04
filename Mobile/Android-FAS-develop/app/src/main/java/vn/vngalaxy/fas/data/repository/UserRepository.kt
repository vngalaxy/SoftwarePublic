package vn.vngalaxy.fas.data.repository

import vn.vngalaxy.fas.model.User
import vn.vngalaxy.fas.shared.scheduler.DataResult

interface UserRepository {
    var id: String

    var sessionId: String

    var name: String

    var phoneNumber: String

    var deviceToken: String

    var role: String

    var isLogin: Boolean

    var isSeenOnboarding: Boolean

    suspend fun checkUserExists(phoneNumber: String): DataResult<Boolean>

    suspend fun createPhoneToken(phoneNumber: String): DataResult<String>

    suspend fun createSession(secret: String): DataResult<String>

    suspend fun saveUserInfo(userId: String, role: String, isLogin: Boolean, sessionId: String, token: String): DataResult<Unit>

    suspend fun addUser(user: User): DataResult<Unit>

    suspend fun changeRoomOfUser(user: User): DataResult<Unit>

    suspend fun changeUserInfo(user: User): DataResult<Unit>

    suspend fun logout(sessionId: String): DataResult<Unit>
}