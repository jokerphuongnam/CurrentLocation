package pnam.currentlocation.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import pnam.currentlocation.model.repository.location.LocationRepository
import pnam.currentlocation.model.repository.location.DefaultLocationRepositoryImpl
import javax.inject.Named

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @Provides
    @ServiceScoped
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)

    @Provides
    @ServiceScoped
    @Named("Service")
    fun provideLocationRepository(@ApplicationContext context: Context): LocationRepository =
        DefaultLocationRepositoryImpl(context)
}