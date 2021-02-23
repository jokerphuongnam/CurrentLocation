package pnam.currentlocation.ui.main.viewlocation

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import dagger.hilt.android.AndroidEntryPoint
import pnam.currentlocation.R
import pnam.currentlocation.databinding.FragmentViewLocationBinding
import pnam.currentlocation.model.database.domain.Location
import pnam.currentlocation.ui.base.BaseFragment

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