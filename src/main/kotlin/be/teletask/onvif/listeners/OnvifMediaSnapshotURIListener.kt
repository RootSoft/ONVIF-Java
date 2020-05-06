package be.teletask.onvif.listeners

import be.teletask.onvif.models.OnvifDevice
import be.teletask.onvif.models.OnvifMediaProfile

interface OnvifMediaSnapshotURIListener {

    fun onMediaSnapshotURIReceived(device: OnvifDevice, profile: OnvifMediaProfile, url: String)

}
