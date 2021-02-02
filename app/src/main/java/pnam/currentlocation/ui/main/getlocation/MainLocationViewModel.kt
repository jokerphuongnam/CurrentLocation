package pnam.currentlocation.ui.main.getlocation

import android.app.Service
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pnam.currentlocation.model.database.domain.Location
import pnam.currentlocation.model.usecase.get.MainLocationUseCase
import kotlin.reflect.KClass

class MainLocationViewModel @ViewModelInject constructor(private val useCase: MainLocationUseCase) :
    ViewModel() {

    fun<T: Service> isLocationRunning(service: KClass<T>) = useCase.isServiceRunning(service)

    private val _liveLocation: MutableLiveData<Location?> by lazy {
        MutableLiveData<Location?>()
    }

    val liveLocation: MutableLiveData<Location?> get() = _liveLocation

    fun getLastLocation() {
        viewModelScope.launch {
            _liveLocation.postValue(useCase.getLastLocation())
        }
    }
}