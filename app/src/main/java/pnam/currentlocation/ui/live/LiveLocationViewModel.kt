package pnam.currentlocation.ui.live

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import pnam.currentlocation.model.database.domain.Location
import pnam.currentlocation.model.usecase.live.LiveLocationUseCase

class LiveLocationViewModel @ViewModelInject constructor(private val useCase: LiveLocationUseCase) :
    ViewModel() {

    private val _liveLocation: MutableLiveData<Location> by lazy { MutableLiveData<Location>() }
    public val liveLocation: MutableLiveData<Location> get() = _liveLocation

    init {
        viewModelScope.launch {
            useCase.getLiveLocation().flowOn(Dispatchers.IO).collect { location ->
                _liveLocation.postValue(location)
            }
        }
    }
}