package pnam.currentlocation.utils

object Constants {
    const val LOCATION_SERVICE_ID = 231
    val ACTION_START_LOCATION_SERVICE: String = this::ACTION_START_LOCATION_SERVICE.name
    val ACTION_STOP_LOCATION_SERVICE: String = this::ACTION_STOP_LOCATION_SERVICE.name
    const val CHANNEL_NAME: String = "Location Service"
    val LOCATION_HIGH_CHANNEL_ID: String = Constants::CHANNEL_NAME.name
    val RECEIVE_ACTION: String = this::RECEIVE_ACTION.name
    val IS_RUNNING_EXTRA: String = this::IS_RUNNING_EXTRA.name
    const val NAMED_APP = "App"
    const val NAMED_SERVICE = "Service"
}