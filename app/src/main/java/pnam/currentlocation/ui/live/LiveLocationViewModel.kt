package pnam.currentlocation.ui.live

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import pnam.currentlocation.model.database.domain.Location
import pnam.currentlocation.model.usecase.live.LiveLocationUseCase
import javax.inject.Inject

@HiltViewModel
class LiveLocationViewModel @Inject constructor(private val useCase: LiveLocationUseCase) :
    ViewModel() {

    private val _liveLocation: LiveData<Location> by lazy {
        useCase.getLiveLocation().asLiveData(Dispatchers.Main)
    }
    val liveLocation: LiveData<Location> get() = _liveLocation
}