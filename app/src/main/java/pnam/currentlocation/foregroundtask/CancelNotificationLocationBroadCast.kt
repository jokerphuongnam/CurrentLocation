package pnam.currentlocation.foregroundtask

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class CancelNotificationLocationBroadCast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.apply {
            stopService(
                Intent().apply {
                    setClassName(
                        applicationContext.packageName,
                        LiveLocationService::class.qualifiedName!!
                    )
                }
            )
        }
    }
}