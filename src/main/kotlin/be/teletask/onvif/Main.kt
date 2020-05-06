package be.teletask.onvif

import be.teletask.onvif.coroutines.discoverDevices
import be.teletask.onvif.models.OnvifDevice
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val onvifDevices = discoverDevices { discoveryMode = DiscoveryMode.ONVIF }.filterIsInstance(OnvifDevice::class.java)

    val device = onvifDevices.first()
    val info = device.getInformation()
    val mediaProfiles = device.getMediaProfiles()
    device.ptzContinuousMove(mediaProfiles.first().token, -1.0, 1.0, null, 1)
    val snapshotUri = device.getMediaSnapshotUri(mediaProfiles.first())

    device.ptzRelativeMove(mediaProfiles.first().token, 0.1, 0.0, null)
    device.ptzAbsoluteMove(mediaProfiles.first().token, 0.1, 0.0, 1.0)
    device.ptzStop(mediaProfiles.first().token)

    val info2 = OnvifDevice("192.168.0.132:8888", "admin", "pass").getInformation()
}
