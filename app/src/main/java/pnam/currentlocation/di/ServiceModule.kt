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
import pnam.currentlocation.utils.Constants.NAMED_SERVICE
import javax.inject.Named

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @Provides
    @ServiceScoped
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)

    @Provides
    @ServiceScoped
    @Named(NAMED_SERVICE)
    fun provideLocationRepository(@ApplicationContext context: Context): LocationRepository =
        DefaultLocationRepositoryImpl(context)
}