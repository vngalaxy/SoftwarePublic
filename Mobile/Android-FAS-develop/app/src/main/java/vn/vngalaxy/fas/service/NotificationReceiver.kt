package vn.vngalaxy.fas.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import vn.vngalaxy.fas.model.Sensor
import vn.vngalaxy.fas.presentation.views.main.MainActivity

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val sensor = intent.getParcelableExtra<Sensor>("sensor")
        val intentToOpenApp = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("sensor", sensor)
        }

        context.startActivity(intentToOpenApp)
    }
}