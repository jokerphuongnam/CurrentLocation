package pnam.currentlocation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

abstract class BaseFragment<VB : ViewDataBinding, VM : ViewModel>(@LayoutRes override var layoutRes: Int) :
    Fragment(), BaseView<VB, VM> {

    override var navController: NavController? = null

    override lateinit var binding: VB

    override fun createBinding(): VB = DataBindingUtil.inflate(
        layoutInflater,
        layoutRes, container, false
    )

    override fun setLifecycleOwner() {
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private var container: ViewGroup? = null

    override fun showToast(text: CharSequence, duration: Int) =
        showToast(requireContext(), text, duration)

    override fun checkPermission(
        permission: String,
        permissionRequestCode: Int,
        action: () -> Unit
    ) = checkPermission(requireActivity(), permission, permissionRequestCode, action)

    override fun baseRequestPermissions(permissions: Array<String>, requestCode: Int) =
        requestPermissions(permissions, requestCode)

    override fun <T> LiveData<T>.observer(observer: Observer<T>?) {
        if (observer != null) {
            observe(viewLifecycleOwner, observer)
        }
    }

    override fun <T> LiveData<T>.observe(observer: Observer<T>) =
        observe(viewLifecycleOwner, observer)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.container = container
        binding = createBinding()
        navController = findNavController()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        navController = findNavController()
    }

    override fun onPause() {
        super.onPause()
        navController = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        action()
        setLifecycleOwner()
    }
}