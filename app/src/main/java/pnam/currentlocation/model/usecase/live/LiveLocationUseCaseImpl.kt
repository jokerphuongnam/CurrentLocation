package pnam.currentlocation.model.usecase.live

import kotlinx.coroutines.flow.Flow
import pnam.currentlocation.model.database.domain.Location
import pnam.currentlocation.model.repository.location.LocationRepository
import pnam.currentlocation.utils.Constants.NAMED_APP
import javax.inject.Inject
import javax.inject.Named

class LiveLocationUseCaseImpl @Inject constructor(
    @Named(NAMED_APP) override val locationRepository: LocationRepository
) :
    LiveLocationUseCase {
    override fun getLiveLocation(): Flow<Location> = locationRepository.getLiveLocation()
}