package pnam.currentlocation.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pnam.currentlocation.model.repository.location.DefaultLocationRepositoryImpl
import pnam.currentlocation.model.repository.location.LocationRepository
import pnam.currentlocation.utils.Constants.NAMED_APP
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppProvidesModule {

    @Provides
    @Singleton
    @Named(NAMED_APP)
    fun provideLocationRepository(@ApplicationContext context: Context): LocationRepository =
        DefaultLocationRepositoryImpl(context)
}