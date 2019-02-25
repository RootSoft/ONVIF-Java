# ONVIF-Java
---
[ ![Download](https://api.bintray.com/packages/tomasverhelst/ONVIF-Java/ONVIF-Java/images/download.svg) ](https://bintray.com/tomasverhelst/ONVIF-Java/ONVIF-Java/_latestVersion)

<p align="center"> 
<img src="https://botw-pd.s3.amazonaws.com/styles/logo-thumbnail/s3/112012/onvif-converted.png?itok=yqR6_a6G">
</p>

ONVIF is an open industry forum that provides and promotes standardized interfaces for effective interoperability of IP-based physical security products. ONVIF was created to make a standard way of how IP products within CCTV and other security areas can communicate with each other.

## Kotlin coroutines

All the Kotlin functions provided are `suspend` functions. All the examples below assumes that you are inside a coroutine context. If not wrap your code inside a `suspend` function or `runBlocking{}`. 
I highly recommend to run those functions inside the [`IO`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-i-o.html) dispatcher since these calls are thread blocking.

### Discovery
Straight forward solution:

```kotlin
val devices: List<Devices> = discoverDevices()
```

If you need to create and customize a a one-shot `DiscoveryManager`:
```kotlin
val devices = discoverDevices {
    discoveryTimeout = 10000
}
```

If using a custom `DiscoveryManager`:
```kotlin
    val myDM = DiscoveryManager().apply { discoveryTimeout = 10000 }
    val devices = awaitDeviceDiscovery { myDM.discover(it) } 
```

### Information, Profiles and URIs

Once you obtained the device host and you know username and password, create the `OnvifDevice` and get its data async using:

```kotlin
val om = OnvifManager()
val device = OnvifDevice(host, user, pswd)

val infos = awaitDeviceInformations { om.getDeviceInformation(device, it) }
val profiles = awaitDeviceMediaProfiles { om.getMediaProfiles(device, it) }
val streamUri = awaitDeviceMediaStreamUri { om.getMediaStreamURI(device, profiles.first(), it) }
val snapshotUri = awaitDeviceMediaSnapshotUri { om.getMediaSnapshotURI(device, profiles.first(), it) }
```