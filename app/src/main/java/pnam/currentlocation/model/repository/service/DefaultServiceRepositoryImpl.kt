package pnam.currentlocation.model.repository.service

import android.app.ActivityManager
import android.app.Service
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.reflect.KClass

class DefaultServiceRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ServiceRepository {

    override fun <T : Service> isServiceRunning(service: KClass<T>): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
            ?: return false
         @Suppress("DEPRECATION") val runningServices: List<ActivityManager.RunningServiceInfo> =
            activityManager.getRunningServices(Integer.MAX_VALUE)
        for (serviceRunning in runningServices) {
            if (service.qualifiedName.equals(serviceRunning.service.className)) {
                return true
            }
        }
        return false
    }
}