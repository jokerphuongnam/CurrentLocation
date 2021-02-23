package pnam.currentlocation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pnam.currentlocation.model.repository.service.DefaultServiceRepositoryImpl
import pnam.currentlocation.model.repository.service.ServiceRepository
import pnam.currentlocation.model.usecase.get.MainLocationUseCase
import pnam.currentlocation.model.usecase.get.MainLocationUseCaseImpl
import pnam.currentlocation.model.usecase.live.LiveLocationUseCase
import pnam.currentlocation.model.usecase.live.LiveLocationUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindModule {

    @Binds
    abstract fun getMainUseCase(useCase: MainLocationUseCaseImpl): MainLocationUseCase

    @Binds
    abstract fun getLiveUseCase(useCase: LiveLocationUseCaseImpl): LiveLocationUseCase

    @Binds
    abstract fun getServiceRepository(repository: DefaultServiceRepositoryImpl): ServiceRepository
}