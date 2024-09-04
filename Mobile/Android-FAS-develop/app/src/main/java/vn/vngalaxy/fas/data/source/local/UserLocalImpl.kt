package vn.vngalaxy.fas.data.source.local

import android.content.Context
import vn.vngalaxy.fas.data.source.UserDatasource
import vn.vngalaxy.fas.model.Role
import vn.vngalaxy.fas.shared.constant.Constant.STRING_EMPTY
import vn.vngalaxy.fas.shared.extensions.isNotEmptyBlank

class UserLocalImpl(
    context: Context
) : UserDatasource.Local {
    override var id: String by Preferences(context).StringProp("userId", STRING_EMPTY)

    override var sessionId: String by Preferences(context).StringProp("sessionId", STRING_EMPTY)

    override var name: String by Preferences(context).StringProp("userName", STRING_EMPTY)

    override var phoneNumber: String by Preferences(context).StringProp("phoneNumber", STRING_EMPTY)

    override var deviceToken: String by Preferences(context).StringProp("deviceToken", STRING_EMPTY)

    override var role: String by Preferences(context).StringProp("role", Role.NONE.value)

    override var isLogin: Boolean by Preferences(context).BooleanProp("isLogin", false)

    override var isSeenOnboarding: Boolean by Preferences(context).BooleanProp("isSeenOnboarding", false)

    override suspend fun resetLogout() {
        role = Role.NONE.value
        phoneNumber = STRING_EMPTY
        name = STRING_EMPTY
        sessionId = STRING_EMPTY
        id = STRING_EMPTY
    }

    override suspend fun saveUserInfo(role: String, isLogin: Boolean, sessionId: String) {
        if (role.isNotEmptyBlank()) {
            this.role = role
        }
        this.sessionId = sessionId
        this.isLogin = isLogin
    }
}