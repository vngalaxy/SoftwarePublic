package vn.vngalaxy.fas.shared.extensions

import android.util.Log
import io.appwrite.exceptions.AppwriteException

fun Exception.handleException(): Exception {
    Log.d("Daataaaaaaaa", "$this")
    return if (this is AppwriteException) {
        when (this.type) {
            "user_session_already_exists" -> {
                Exception("Phiên hoạt động của bạn đã tồn tại, vui lòng thử lại sau!")
            }

            "general_rate_limit_exceeded" -> {
                Exception("Đã đến giới hạn truy cập, vui lòng thử lại sau ít phút!")
            }

            "user_invalid_token" -> {
                Exception("Mã xác thực không hợp lệ, vui lòng thử lại sau!")
            }

            else -> {
                Exception("Đã xảy ra lỗi ${this.message}")
            }
        }
    } else {
        Exception("Đã xảy ra lỗi không xác định, vui lòng thử lại sau!")
    }
}