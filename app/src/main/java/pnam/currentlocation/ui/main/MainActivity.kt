package pnam.currentlocation.ui.main

import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pnam.currentlocation.R
import pnam.currentlocation.R.id
import pnam.currentlocation.databinding.ActivityMainBinding
import pnam.currentlocation.ui.base.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    override fun action() {

    }

    override val viewModel: MainViewModel by viewModels()

    override val idNavController: Int = id.nav_host_fragment
}