package pnam.currentlocation.model.repository.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import pnam.currentlocation.model.database.domain.Location
import java.util.*
import kotlin.coroutines.resume

@Suppress("BlockingMethodInNonBlockingContext")
class DefaultLocationRepositoryImpl(private val context: Context) : LocationRepository {
    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    @SuppressLint("MissingPermission")
    override suspend fun getLastLocation(): Location? = suspendCancellableCoroutine { coroutines ->
        fusedLocationClient.lastLocation.addOnCompleteListener { task ->
            val result = task.result
            if (result == null) {
                coroutines.resume(null)
            } else {
                coroutines.resume(
                    Location(
                        Geocoder(
                            context,
                            Locale.getDefault()
                        ).getFromLocation(
                            result.latitude,
                            result.longitude,
                            1
                        )[0]
                    )
                )
            }
        }
    }

    private var isGetLocationRunning: Boolean = false

    private val locationCallback: LocationCallback by lazy {
        object : LocationCallback() {

            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                scope.launch {
                    getLiveLocationSuspend?.invoke(locationResult)
                    scope.cancel()
                }
            }
        }

    }
    private val scope: CoroutineScope by lazy {
        CoroutineScope(Dispatchers.IO)
    }

    private val looper: Looper by lazy {
        Looper.getMainLooper()
    }

    private var getLiveLocationSuspend: (suspend (LocationResult) -> Unit)? = null

    @ExperimentalCoroutinesApi
    @SuppressLint("MissingPermission")
    override fun getLiveLocation(): Flow<Location> = channelFlow {
        withContext(Dispatchers.IO) {
            if(getLiveLocationSuspend == null){
                getLiveLocationSuspend = { locationResult ->
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val lastLocation: android.location.Location = locationResult.lastLocation
                    val fromLocation =
                        geocoder.getFromLocation(
                            lastLocation.latitude,
                            lastLocation.longitude,
                            1
                        )
                    offer(Location(fromLocation[0]))
                }
            }
            fusedLocationClient.requestLocationUpdates(
                LocationRequest().apply {
                    interval = 4000
                    fastestInterval = 2000
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                },
                locationCallback,
                looper
            )
        }
        isGetLocationRunning = true
        awaitClose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
            scope.cancel()
        }
    }
}