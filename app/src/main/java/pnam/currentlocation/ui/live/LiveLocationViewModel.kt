package pnam.currentlocation.ui.live

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import pnam.currentlocation.model.database.domain.Location
import pnam.currentlocation.model.usecase.live.LiveLocationUseCase

class LiveLocationViewModel @ViewModelInject constructor(private val useCase: LiveLocationUseCase) :
    ViewModel() {

    private val _liveLocation: LiveData<Location> by lazy {
        useCase.getLiveLocation().asLiveData(Dispatchers.Main)
    }
    val liveLocation: LiveData<Location> get() = _liveLocation
}