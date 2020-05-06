package be.teletask.onvif.coroutines

import be.teletask.onvif.DiscoveryManager
import be.teletask.onvif.listeners.DiscoveryListener
import be.teletask.onvif.models.Device
import be.teletask.onvif.models.OnvifDevice
import be.teletask.onvif.requests.OnvifRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun <T> awaitDeviceRequest(block: (OnvifRequest.Listener<T>) -> Unit): T =
        suspendCancellableCoroutine { cont ->
            block(object : OnvifRequest.Listener<T> {
                override fun onSuccess(device: OnvifDevice?, data: T) {
                    cont.resume(data)
                }

                override fun onError(onvifException: OnvifRequest.OnvifException) {
                    cont.cancel(onvifException)
                }
            })
        }

suspend fun awaitDeviceDiscovery(onDiscoveryStarted: () -> Unit = {}, block: (DiscoveryListener) -> Unit): List<Device> =
        suspendCancellableCoroutine { cont ->
            block(object : DiscoveryListener {
                override fun onDevicesFound(devices: List<Device>) {
                    cont.resume(devices)
                }

                override fun onDiscoveryStarted() = onDiscoveryStarted()
            })
        }

suspend fun discoverDevices(configure: DiscoveryManager.() -> Unit = {}) = DiscoveryManager().run {
    configure()
    awaitDeviceDiscovery {
        discover(it)
    }
}
