package pnam.currentlocation.ui.base

import android.os.Build
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.findNavController

abstract class BaseActivity<VB : ViewDataBinding, VM : ViewModel>(@LayoutRes override var layoutRes: Int) :
    AppCompatActivity(), BaseView<VB, VM> {
    override lateinit var binding: VB
    final override fun createBinding(): VB = DataBindingUtil.setContentView(this, layoutRes)

    protected abstract val idNavController: Int?
    override val navController: NavController? by lazy {
        idNavController?.let { findNavController(it) }
    }

    override fun showToast(text: CharSequence, duration: Int) =
        showToast(this, text, duration)

    override fun checkPermission(
        permission: String,
        permissionRequestCode: Int,
        action: () -> Unit
    ) = checkPermission(this, permission, permissionRequestCode, action)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun baseRequestPermissions(permissions: Array<String>, requestCode: Int) =
        requestPermissions(permissions, requestCode)

    override fun <T> LiveData<T>.observer(observer: Observer<T>?) {
        if (observer != null) {
            observe(this@BaseActivity, observer)
        }
    }

    override fun <T> LiveData<T>.observe(observer: Observer<T>) =
        observe(this@BaseActivity, observer)

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.executePendingBindings()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = createBinding()
        action()
    }

    override fun onBackPressed() {
        when (navController?.navigateUp()) {
            null, false -> super.onBackPressed()
        }
    }
}