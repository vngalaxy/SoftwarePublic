package vn.vngalaxy.fas.data.repository

import android.util.Log
import vn.vngalaxy.fas.data.source.UserDatasource
import vn.vngalaxy.fas.model.User
import vn.vngalaxy.fas.shared.constant.Constant.STRING_EMPTY
import vn.vngalaxy.fas.shared.extensions.handleException
import vn.vngalaxy.fas.shared.scheduler.DataResult

class UserRepositoryImpl(
    private val local: UserDatasource.Local,
    private val remote: UserDatasource.Remote,
) : UserRepository {
    override var id: String
        get() = local.id
        set(value) {
            local.id = value
        }

    override var sessionId: String
        get() = local.sessionId
        set(value) {
            local.sessionId = value
        }

    override var name: String
        get() = local.name
        set(value) {
            local.name = value
        }

    override var phoneNumber: String
        get() = local.phoneNumber
        set(value) {
            local.phoneNumber = value
        }

    override var deviceToken: String
        get() = local.deviceToken
        set(value) {
            local.deviceToken = value
        }

    override var role: String
        get() = local.role
        set(value) {
            local.role = value
        }

    override var isLogin: Boolean
        get() = local.isLogin
        set(value) {
            local.isLogin = value
        }

    override var isSeenOnboarding: Boolean
        get() = local.isSeenOnboarding
        set(value) {
            local.isSeenOnboarding = value
        }

    override suspend fun checkUserExists(phoneNumber: String): DataResult<Boolean> {
        return try {
            val data = remote.checkUserExists(phoneNumber)
            DataResult.Success(data)
        } catch (e: Exception) {
            DataResult.Error(e.handleException())
        }
    }

    override suspend fun createPhoneToken(phoneNumber: String): DataResult<String> {
        return try {
            val data = remote.createPhoneToken(phoneNumber)
            DataResult.Success(data)
        } catch (e: Exception) {
            DataResult.Error(e.handleException())
        }
    }

    override suspend fun createSession(secret: String): DataResult<String> {
        return try {
            val data = remote.createPhoneVerification(id, secret).id
            DataResult.Success(data)
        } catch (e: Exception) {
            DataResult.Error(e.handleException())
        }
    }

    override suspend fun saveUserInfo(
        userId: String,
        role: String,
        isLogin: Boolean,
        sessionId: String,
        token: String
    ): DataResult<Unit> = try {
        val newToken = if (deviceToken != STRING_EMPTY) {
            deviceToken
        } else {
            token
        }

        local.saveUserInfo(role, isLogin, sessionId)
        remote.updateDeviceToken(newToken, userId)
        DataResult.Success(Unit)
    } catch (e: Exception) {
        DataResult.Error(e.handleException())
    }

    override suspend fun addUser(user: User): DataResult<Unit> {
        return try {
            val isUserExists = remote.checkUserExists(user.phoneNumber.toString())
            Log.d("Dataaaaa", "addUser repo ${user.room?.id}")
            if (isUserExists) {
                DataResult.Error(Exception("Số điện thoại này đã được đăng ký !"))
            } else {
                val response = remote.addUser(user)
                DataResult.Success(response)
            }

        } catch (e: Exception) {
            DataResult.Error(e.handleException())
        }
    }

    override suspend fun changeRoomOfUser(user: User): DataResult<Unit> = try {
        val response = remote.changeRoomOfUser(user)
        DataResult.Success(response)
    } catch (e: Exception) {
        DataResult.Error(e.handleException())
    }

    override suspend fun changeUserInfo(user: User): DataResult<Unit> = try {
        val response = remote.changeUserInfo(user)
        DataResult.Success(response)
    } catch (e: Exception) {
        DataResult.Error(e.handleException())
    }

    override suspend fun logout(sessionId: String): DataResult<Unit> = try {
        remote.logout(sessionId)
        local.resetLogout()
        DataResult.Success(Unit)
    } catch (e: Exception) {
        DataResult.Error(e.handleException())
    }

    override fun toString(): String {
        return "User(id='$id', sessionId='$sessionId', name='$name', phoneNumber='$phoneNumber', " +
                "deviceToken='$deviceToken', role='$role', isLogin=$isLogin, isSeenOnboarding=$isSeenOnboarding)"
    }
}
