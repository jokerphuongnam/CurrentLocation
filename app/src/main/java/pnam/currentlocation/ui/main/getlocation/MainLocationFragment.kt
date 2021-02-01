package pnam.currentlocation.ui.main.getlocation

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.util.Log
import android.view.View.OnClickListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import dagger.hilt.android.AndroidEntryPoint
import pnam.currentlocation.R
import pnam.currentlocation.databinding.FragmentMainLocationBinding
import pnam.currentlocation.foregroundtask.LiveLocationService
import pnam.currentlocation.model.database.domain.Location
import pnam.currentlocation.ui.base.BaseFragment
import pnam.currentlocation.utils.Constants

@AndroidEntryPoint
class MainLocationFragment :
    BaseFragment<FragmentMainLocationBinding, MainLocationViewModel>(R.layout.fragment_main_location) {
    private val runningReceiver: BroadcastReceiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent ?: return
                if (intent.action.equals(Constants.RECEIVE_ACTION)) {
                    binding.getLiveLocation.isChecked =
                        intent.getBooleanExtra(Constants.IS_RUNNING_EXTRA, false)
                }
            }
        }
    }

    override fun action() {
        binding.btnGetLocation.setOnClickListener(getLocationClick)
        binding.getLiveLocation.setOnClickListener(getLiveLocationClick)
        binding.getLiveLocation.isChecked = viewModel.isLocationRunning(LiveLocationService::class)
    }

    private var isLocationReceiveRunning: Boolean = false

    private fun startObserveLocationRunningBroadCastReceiver() {
        if (!isLocationReceiveRunning) {
            val filter = IntentFilter().apply {
                addAction(Constants.RECEIVE_ACTION)
            }
            LocalBroadcastManager.getInstance(requireContext())
                .registerReceiver(runningReceiver, filter)
            isLocationReceiveRunning = !isLocationReceiveRunning
        }
    }

    override val viewModel: MainLocationViewModel by viewModels()

    private val getLocationClick: OnClickListener = OnClickListener {
        checkPermission(
            Manifest.permission.ACCESS_FINE_LOCATION,
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            getLocation()
        }
    }

    private fun getLocation() {
        viewModel.getLastLocation()
    }

    private val getLiveLocationClick: OnClickListener = OnClickListener {
        checkPermission(
            Manifest.permission.ACCESS_FINE_LOCATION,
            PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION) {
            getLiveLocationService()
        }
    }

    private fun getLiveLocationService() {
        Intent(requireContext(), LiveLocationService::class.java).apply {
            viewModel.isLocationRunning(LiveLocationService::class)
                .also { notLocationRunning ->
                    val notLocationRunning = !notLocationRunning
                    binding.getLiveLocation.isChecked = notLocationRunning
                    action = if (notLocationRunning) {
                        Constants.ACTION_START_LOCATION_SERVICE.also {
                            startObserveLocationRunningBroadCastReceiver()
                        }
                    } else {
                        Constants.ACTION_STOP_LOCATION_SERVICE
                    }
                    requireActivity().startService(this)
                }
            viewModel.isLocationRunning(LiveLocationService::class)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            viewModel.actionRequestPermission = {
                when (requestCode) {
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                        getLocation()
                    }
                    PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION -> {
                        getLiveLocationService()
                    }
                    else -> {
                        showToast("Denied")
                        println()
                    }
                }
            }
        } else {
            showToast("Denied")
        }
    }

    private var locationObserve: Observer<Location?>? = null

    override fun onResume() {
        super.onResume()
        locationObserve = Observer { location ->
            if (location == null) {
                showToast("Need on location")
            }
            location ?: return@Observer
            navController?.navigate(
                MainLocationFragmentDirections.actionGetLocationToViewLocation(
                    location
                )
            )
        }
        viewModel.liveLocation.observe(locationObserve!!)
        binding.getLiveLocation.isChecked = viewModel.isLocationRunning(LiveLocationService::class)
    }

    override fun onPause() {
        super.onPause()
        locationObserve?.let {
            viewModel.liveLocation.removeObserver(it)
            locationObserve = null
        }
    }

    private fun stopObserveLocationRunningBroadCastReceiver() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(runningReceiver)
    }

    override fun onStop() {
        super.onStop()
        stopObserveLocationRunningBroadCastReceiver()
    }

    companion object {
        const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        const val PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 2
    }
}