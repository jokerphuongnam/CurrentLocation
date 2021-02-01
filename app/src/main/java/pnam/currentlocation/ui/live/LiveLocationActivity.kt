package pnam.currentlocation.ui.live

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import pnam.currentlocation.R
import pnam.currentlocation.databinding.ActivityLiveLocationBinding
import pnam.currentlocation.ui.base.BaseActivity
import pnam.currentlocation.ui.main.MainActivity


@AndroidEntryPoint
class LiveLocationActivity :
    BaseActivity<ActivityLiveLocationBinding, LiveLocationViewModel>(R.layout.activity_live_location) {
    override val idNavController: Int? = null

    override val viewModel: LiveLocationViewModel by viewModels()

    override fun action() {
        binding.backBtn.setOnClickListener(onBackClick)
        viewModel.liveLocation.observe(Observer { location ->
            binding.location = location
        })
    }

    private val onBackClick: View.OnClickListener by lazy {
        View.OnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        if (isTaskRoot) {
            startActivity(Intent(this, MainActivity::class.java))
            // using finish() is optional, use it if you do not want to keep currentActivity in stack
            finish()
        } else {
            super.onBackPressed()
        }
    }
}