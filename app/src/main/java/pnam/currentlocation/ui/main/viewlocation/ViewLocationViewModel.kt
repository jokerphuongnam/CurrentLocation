package pnam.currentlocation.ui.main.viewlocation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pnam.currentlocation.model.database.domain.Location
import javax.inject.Inject

@HiltViewModel
class ViewLocationViewModel @Inject constructor() : ViewModel() {
    private val _liveLocation: MutableLiveData<Location> by lazy { MutableLiveData<Location>() }
    var location: Location
        set(value) {
            _liveLocation.postValue(value)
        }
        get() = _liveLocation.value ?: throw NullPointerException()
    val liveLocation: MutableLiveData<Location>
        get() = _liveLocation
}