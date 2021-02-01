package pnam.currentlocation.foregroundtask

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import pnam.currentlocation.R
import pnam.currentlocation.model.database.domain.Location
import pnam.currentlocation.model.repository.location.LocationRepository
import pnam.currentlocation.utils.Constants
import pnam.currentlocation.utils.Constants.LOCATION_HIGH_CHANNEL_ID
import pnam.currentlocation.utils.Constants.LOCATION_SERVICE_ID
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class LiveLocationService : BaseCoroutinesService() {
    @Inject
    @Named("Service")
    lateinit var locationRepo: LocationRepository

    private var isNotificationRunning: Boolean = false

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @InternalCoroutinesApi
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Constants.ACTION_START_LOCATION_SERVICE -> {
                startGetLocation()
            }
            Constants.ACTION_STOP_LOCATION_SERVICE -> {
                stopGetLocation()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        sendBroadCast(false)
        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.cancel(LOCATION_SERVICE_ID)
        isNotificationRunning = false
    }

    @InternalCoroutinesApi
    @SuppressLint("MissingPermission")
    private fun startGetLocation() {
        serviceScope.launch {
            locationRepo.getLiveLocation().collect { location ->
                observeGetLocation(location)
            }
        }
        sendBroadCast(true)
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = LOCATION_HIGH_CHANNEL_ID
            // Register the channel with the system
            val notificationManager: NotificationManager? =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
            notificationManager?.getNotificationChannel(name).let {
                if (it == null) {
                    notificationManager?.createNotificationChannel(NotificationChannel(
                        LOCATION_HIGH_CHANNEL_ID,
                        name,
                        NotificationManager.IMPORTANCE_LOW).apply {
                        description = "This channel is used by location service"
                    })
                }
            }
        }
    }

    private val notificationBuilder by lazy {
        val contentIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            Intent(applicationContext, OpenLiveActivityBroadCast::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val cancelIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            Intent(applicationContext, CancelNotificationLocationBroadCast::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        NotificationCompat.Builder(applicationContext, LOCATION_HIGH_CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_location)
            setContentTitle(Constants.CHANNEL_NAME)
            setDefaults(NotificationCompat.DEFAULT_ALL)
            setContentIntent(contentIntent)
            setDeleteIntent(cancelIntent)
            setAutoCancel(false)
            priority = NotificationCompat.PRIORITY_MAX
        }
    }

    private fun observeGetLocation(location: Location) {
        notificationBuilder.setContentText("${location.latitude} - ${location.longitude}")
        with(NotificationManagerCompat.from(applicationContext)) {
            notify(LOCATION_SERVICE_ID, notificationBuilder.build())
        }
    }

    private fun stopGetLocation() {
        stopSelf()
    }

    private fun sendBroadCast(isRunning: Boolean) {
        val intent = Intent()
        intent.action = Constants.RECEIVE_ACTION
        intent.putExtra(Constants.IS_RUNNING_EXTRA, isRunning)
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
    }
}