package pnam.currentlocation.model.repository.location

import kotlinx.coroutines.flow.Flow
import pnam.currentlocation.model.database.domain.Location
import javax.inject.Singleton

@Singleton
interface LocationRepository {
    suspend fun getLastLocation(): Location?
    fun getLiveLocation(): Flow<Location>
}