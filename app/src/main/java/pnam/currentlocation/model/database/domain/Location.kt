package pnam.currentlocation.model.database.domain

import android.location.Address
import androidx.annotation.Keep
import java.io.Serializable
import javax.inject.Inject

@Keep
class Location @Inject constructor(
    private var _latitude: Double?,
    private var _longitude: Double?
) : Serializable {
    var latitude: Double
        get() = _latitude ?: 0.0
        set(value) {
            _latitude = value
        }

    var longitude: Double
        get() = _longitude ?: 0.0
        set(value) {
            _longitude = value
        }

    private var _countryName: String? = null
    var countryName: String
        get() = _countryName ?: ""
        set(value) {
            _countryName = value
        }

    private var _locality: String? = null
    var locality: String
        get() = _locality ?: ""
        set(value) {
            _locality = value
        }
    private var _address: String? = null
    var address: String
        get() = _address ?: ""
        set(value) {
            _address = value
        }

    private fun locationByPhone(location: Address): Location {
        this._latitude = location.latitude
        this._longitude = location.longitude
        this._countryName = location.countryName
        this._locality = location.locality
        this._address = location.getAddressLine(0)
        return this
    }

    constructor(location: Address) : this(null, null) {
        locationByPhone(location)
    }

}