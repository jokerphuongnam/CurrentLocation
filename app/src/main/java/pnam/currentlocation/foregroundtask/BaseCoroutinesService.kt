package pnam.currentlocation.foregroundtask

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

open class BaseCoroutinesService : Service() {

    @Inject
    protected lateinit var serviceScope: CoroutineScope

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        serviceScope.cancel()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        serviceScope.cancel()
    }
}