package pnam.currentlocation.model.repository.service

import android.app.Service
import kotlin.reflect.KClass

interface ServiceRepository {

    fun <T: Service> isServiceRunning(service : KClass<T>): Boolean
}