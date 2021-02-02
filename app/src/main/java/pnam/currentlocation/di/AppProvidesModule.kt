package pnam.currentlocation.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import pnam.currentlocation.model.repository.location.DefaultLocationRepositoryImpl
import pnam.currentlocation.model.repository.location.LocationRepository
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppProvidesModule {

    @Provides
    @Singleton
    @Named("App")
    fun provideLocationRepository(@ApplicationContext context: Context): LocationRepository =
        DefaultLocationRepositoryImpl(context)
}