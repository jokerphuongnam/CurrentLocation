package pnam.currentlocation.model.usecase.get

import android.app.Service
import pnam.currentlocation.model.database.domain.Location
import pnam.currentlocation.model.repository.location.LocationRepository
import pnam.currentlocation.model.repository.service.ServiceRepository
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
interface MainLocationUseCase {
    val repository: LocationRepository
    val serviceRepository: ServiceRepository
    suspend fun getLastLocation(): Location?
    fun <T: Service> isServiceRunning(service : KClass<T>): Boolean
}