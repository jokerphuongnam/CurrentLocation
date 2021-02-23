package pnam.currentlocation.model.usecase.get

import android.app.Service
import pnam.currentlocation.model.database.domain.Location
import pnam.currentlocation.model.repository.location.LocationRepository
import pnam.currentlocation.model.repository.service.ServiceRepository
import pnam.currentlocation.utils.Constants.NAMED_APP
import javax.inject.Inject
import javax.inject.Named
import kotlin.reflect.KClass

class MainLocationUseCaseImpl @Inject constructor(
    @Named(NAMED_APP) override val repository: LocationRepository,
    override val serviceRepository: ServiceRepository
) :
    MainLocationUseCase {
    override suspend fun getLastLocation(): Location? =
        repository.getLastLocation()

    override fun <T : Service> isServiceRunning(service: KClass<T>): Boolean =
        serviceRepository.isServiceRunning(service)
}