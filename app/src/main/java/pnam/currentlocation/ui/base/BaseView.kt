package pnam.currentlocation.ui.base

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.viewbinding.ViewBinding
import pnam.currentlocation.R
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

//base for activity or fragment
interface BaseView<VB : ViewBinding, VM : ViewModel> {
    var binding: VB
    fun createBinding(): VB
    fun setLifecycleOwner()
    val viewModel: VM

    val navController: NavController?

    var layoutRes: Int
    fun action()

    @Suppress("UNCHECKED_CAST")
    val viewModelKClass: KClass<VM>
        get() = ((javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>).kotlin

    fun showToast(context: Context, text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context.applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    fun showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT)

    fun <T> LiveData<T>.observer(observer: Observer<T>?)

    fun checkPermission(
        permission: String,
        permissionRequestCode: Int,
        action: () -> Unit
    )

    @Suppress("SameParameterValue")
    fun checkPermission(
        activity: Activity,
        permission: String,
        permissionRequestCode: Int,
        action: () -> Unit
    ) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            showToast(activity.resources.getString(R.string.require_sdk_m_or_higher))
            return
        }
        when {
            ActivityCompat.checkSelfPermission(
                activity.applicationContext,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                action()
            }
            shouldShowRequestPermissionRationale(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) -> {
                baseRequestPermissions(
                    arrayOf(permission),
                    permissionRequestCode
                )
            }
            else -> {
                baseRequestPermissions(
                    arrayOf(permission),
                    permissionRequestCode
                )
            }
        }
    }

    fun baseRequestPermissions(permissions: Array<String>, requestCode: Int)

    fun <T> LiveData<T>.observe(observer: Observer<T>)
}