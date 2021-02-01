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
    public var latitude: Double
        get() = _latitude ?: 0.0
        set(value) {
            _latitude = value
        }

    public var longitude: Double
        get() = _longitude ?: 0.0
        set(value) {
            _longitude = value
        }

    private var _countryName: String? = null
    public var countryName: String
        get() = _countryName ?: ""
        set(value) {
            _countryName = value
        }

    private var _locality: String? = null
    public var locality: String
        get() = _locality ?: ""
        set(value) {
            _locality = value
        }
    private var _address: String? = null
    public var address: String
        get() = _address ?: ""
        set(value) {
            _address = value
        }

    fun setLocation(
        latitude: Double,
        longitude: Double,
        countryName: String,
        locality: String,
        address: String
    ) {
        this._latitude = latitude
        this._longitude = longitude
        this._countryName = countryName
        this._locality = locality
        this._address = address
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

    fun setLocation(latitude: Double, longitude: Double) {
        this._latitude = latitude
        this._longitude = longitude
    }
}