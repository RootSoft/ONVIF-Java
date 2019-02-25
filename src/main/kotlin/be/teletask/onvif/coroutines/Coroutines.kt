package be.teletask.onvif.coroutines

import be.teletask.onvif.DiscoveryManager
import be.teletask.onvif.listeners.*
import be.teletask.onvif.models.Device
import be.teletask.onvif.models.OnvifDevice
import be.teletask.onvif.models.OnvifDeviceInformation
import be.teletask.onvif.models.OnvifMediaProfile
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun awaitDeviceInformation(block: (OnvifDeviceInformationListener) -> Unit): OnvifDeviceInformation =
        suspendCancellableCoroutine { cont ->
            block(OnvifDeviceInformationListener { _, deviceInformation ->
                cont.resume(deviceInformation)
            })
        }

suspend fun awaitDeviceDiscovery(onDiscoveryStarted: ()->Unit = {}, block: (DiscoveryListener) -> Unit): List<Device> =
        suspendCancellableCoroutine { cont ->
            block(object : DiscoveryListener {
                override fun onDevicesFound(devices: List<Device>) {
                    cont.resume(devices)
                }

                override fun onDiscoveryStarted() = onDiscoveryStarted()
            })
        }

suspend fun discoverDevices(configure: DiscoveryManager.()->Unit = {}) = DiscoveryManager().run {
    configure()
    awaitDeviceDiscovery {
        discover(it)
    }
}

suspend fun awaitDeviceMediaStramUri(block: (OnvifMediaStreamURIListener) -> Unit): String =
        suspendCancellableCoroutine { cont ->
            block(OnvifMediaStreamURIListener { _, _, uri ->
                cont.resume(uri)
            })
        }

suspend fun awaitDeviceMediaSnapshotUri(block: (OnvifMediaSnapshotURIListener) -> Unit): String =
        suspendCancellableCoroutine { cont ->
            block(object : OnvifMediaSnapshotURIListener {
                override fun onMediaSnapshotURIReceived(device: OnvifDevice, profile: OnvifMediaProfile, url: String)
                    = cont.resume(url)
            })
        }

suspend fun awaitDeviceMediaProfiles(block: (OnvifMediaProfilesListener) -> Unit): List<OnvifMediaProfile> =
        suspendCancellableCoroutine { cont ->
            block(OnvifMediaProfilesListener { _, profiles ->
                cont.resume(profiles)
            })
        }

suspend fun awaitDeviceInformations(block: (OnvifDeviceInformationListener) -> Unit): OnvifDeviceInformation =
        suspendCancellableCoroutine { cont ->
            block(OnvifDeviceInformationListener { _, information ->
                cont.resume(information)
            })
        }