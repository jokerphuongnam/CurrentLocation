package pnam.currentlocation.foregroundtask

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import pnam.currentlocation.ui.live.LiveLocationActivity

class OpenLiveActivityBroadCast : BroadcastReceiver() {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.apply {
            startActivity(
                Intent().apply {
                    setClassName(
                        applicationContext.packageName,
                        LiveLocationActivity::class.qualifiedName!!
                    )
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
            )
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