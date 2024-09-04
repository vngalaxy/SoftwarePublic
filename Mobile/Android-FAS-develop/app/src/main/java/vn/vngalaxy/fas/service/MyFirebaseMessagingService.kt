package vn.vngalaxy.fas.service

import android.app.ActivityManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.preference.PreferenceManager
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import vn.vngalaxy.fas.R
import vn.vngalaxy.fas.model.Sensor
import vn.vngalaxy.fas.presentation.views.main.MainActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var soundManager: SoundManager
    private val DEFAULT_VIBRATE_PATTERN = longArrayOf(250, 250, 250, 250)
    private var SOUND_URI: Uri? = null

    override fun onCreate() {
        super.onCreate()
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        soundManager = SoundManager(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        sendNotification(remoteMessage.data)

        if (!isAppInForeground()) {
            val intent = Intent(applicationContext, NotificationReceiver::class.java).apply {
                putExtra("sensor", Sensor.fromMap(remoteMessage.data))
            }
            sendBroadcast(intent)
        }
    }

    override fun onNewToken(token: String) {
        // Handle token refresh if needed
        sharedPreferences.edit().putString("deviceToken", token).apply()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendNotification(data: Map<String, String>) {
        val soundResId = if (data["sound"].isNullOrBlank()) {
            R.raw.default_sound
        } else {
            R.raw.noti_sound
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("sensor", Sensor.fromMap(data))
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        soundManager.play(soundResId)
//        if (isAppInForeground(this)) {
//            // Play music once if the app is in foreground
//            soundManager.play(R.raw.noti_sound)
//        } else {
//            // Start playing looping music if the app is in background
//            soundManager.play(R.raw.noti_sound)
//        }


        val channelId = getString(R.string.app_name)
        createNotificationChannel(channelId)

        val notification = buildNotification(channelId, data, pendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String) {
        val channel = NotificationChannel(
            channelId,
            "Fire warning",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Channel for fire warning"
            enableLights(true)
            enableVibration(true)
            lightColor = Color.WHITE
            setVibrationPattern(DEFAULT_VIBRATE_PATTERN)
            setSound(SOUND_URI, audioAttributes)
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
        soundManager.stop()
    }

    private fun buildNotification(
        channelId: String,
        data: Map<String, String>,
        pendingIntent: PendingIntent,
    ): Notification {
        val largeIcon = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.img_big_notification)

        return NotificationCompat.Builder(this, channelId).apply {
            setSmallIcon(R.drawable.ic_stat_name)
            setLargeIcon(largeIcon)
            setColor(getColor(R.color.primary))
            setContentTitle(data["title"])
            setContentText(data["body"])
            setAutoCancel(true)
            setContentIntent(pendingIntent)
            setStyle(NotificationCompat.BigPictureStyle().bigPicture(largeIcon))
        }.build()
    }

    private fun isAppInForeground(): Boolean {
        val activityManager = applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningAppProcesses = activityManager.runningAppProcesses
        if (runningAppProcesses != null) {
            val packageName = applicationContext.packageName
            for (appProcess in runningAppProcesses) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName == packageName) {
                    return true
                }
            }
        }
        return false
    }
}
