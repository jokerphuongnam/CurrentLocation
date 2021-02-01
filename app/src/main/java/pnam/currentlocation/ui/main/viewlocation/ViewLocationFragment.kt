package pnam.currentlocation.ui.main.viewlocation

import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Looper
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import pnam.currentlocation.R
import pnam.currentlocation.databinding.FragmentViewLocationBinding
import pnam.currentlocation.model.database.domain.Location
import pnam.currentlocation.ui.base.BaseFragment
import java.util.*

@AndroidEntryPoint
class ViewLocationFragment :
    BaseFragment<FragmentViewLocationBinding, ViewLocationViewModel>(R.layout.fragment_view_location) {
    private val args: ViewLocationFragmentArgs by navArgs()

    override fun action() {

    }

    override val viewModel: ViewLocationViewModel by viewModels()

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationObserver: Observer<Location>? = null
    private var locationCallback: LocationCallback? = null

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                val lastLocation: android.location.Location = locationResult.lastLocation
                val fromLocation =
                    geocoder.getFromLocation(lastLocation.latitude, lastLocation.longitude, 1)
                viewModel.location = Location(fromLocation[0])
            }
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient?.requestLocationUpdates(
            LocationRequest.create(),
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun onResume() {
        super.onResume()
        locationObserver = Observer { location ->
            binding.location = location
        }
        viewModel.liveLocation.observer(locationObserver!!)
        viewModel.location = args.location
        binding.backBtn.setOnClickListener {
            navController?.navigateUp()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.liveLocation.observer(null)
        viewModel.liveLocation.removeObserver(locationObserver!!)
        locationObserver = null
        binding.backBtn.setOnClickListener(null)
    }
}