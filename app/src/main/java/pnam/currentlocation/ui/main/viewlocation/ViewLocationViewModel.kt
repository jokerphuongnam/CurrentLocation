package pnam.currentlocation.ui.main.viewlocation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pnam.currentlocation.model.database.domain.Location

class ViewLocationViewModel @ViewModelInject constructor() :
    ViewModel() {
    private val _liveLocation: MutableLiveData<Location> by lazy { MutableLiveData<Location>() }
    var location: Location
        set(value) {
            _liveLocation.postValue(value)
        }
        get() = _liveLocation.value ?: throw NullPointerException()
    var liveLocation: MutableLiveData<Location>
        get() = _liveLocation
        private set(value) {
            TODO()
        }
}