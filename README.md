# ONVIF-Java

[![](https://jitpack.io/v/lamba92/ONVIF-Java.svg)](https://jitpack.io/#lamba92/ONVIF-Java)
[![Build Status](https://travis-ci.org/lamba92/ONVIF-Java.svg?branch=master)](https://travis-ci.org/lamba92/ONVIF-Java)


<p align="center"> 
<img src="https://botw-pd.s3.amazonaws.com/styles/logo-thumbnail/s3/112012/onvif-converted.png?itok=yqR6_a6G">
</p>

ONVIF is an open industry forum that provides and promotes standardized interfaces for effective interoperability of IP-based physical security products. ONVIF was created to make a standard way of how IP products within CCTV and other security areas can communicate with each other.


## Features

  - **ONVIF & UPnP discovery**
  - ONVIF device management (Services, device information, media profiles, raw media stream uri)
  - UPnP device information
  - Easily extendable with your own requests
  - **Android supported!**
  - [**Kotlin Coroutines support**](KOTLIN.md)

## Discovery
---
The OnvifDiscovery class uses the **Web Services Dynamic Discovery (WS-Discovery)**. This is a technical specification that defines a multicast discovery protocol to locate services on a local network. It operates over TCP and UDP port ```3702``` and uses IP multicast address ```239.255.255.250```. As the name suggests, the actual communication between nodes is done using web services standards, notably **SOAP-over-UDP**.

With WS-Discovery, the discovery tool puts SSDP queries on the network from its unicast address to ```239.255.255.250``` multicast address, sending them to the well-known UDP port 3702. The device receives the query, and answers to the discovery tool's unicast IP address from its unicast IP address. The reply contains information about the Web Services (WS) available on the device.

**UPnP** works in a very similar way, but on a different UDP port (```1900```).
Compared to the WS-Discovery, the UPnP is intended for a general use (data sharing, communication, entertainment).

```java
DiscoveryManager manager = new DiscoveryManager();
manager.setDiscoveryTimeout(10000);
manager.discover(new DiscoveryListener() {
    @Override
    public void onDiscoveryStarted() {
        System.out.println("Discovery started");
    }

    @Override
    public void onDevicesFound(List<Device> devices) {
        for (Device device : devices)
            System.out.println("Devices found: " + device.getHostName());
    }
});
```

## ONVIF
---

With the ```OnvifManager``` class it is possible to send requests to an ONVIF-supported device. All requests are sent asynchronously and you can use the ```OnvifResponseListener``` for errors and custom response handling. It is possible to create your own ```OnvifDevice``` or retrieve a list from the ```discover``` method in the ```DiscoveryManager```

```java
onvifManager = new OnvifManager();
onvifManager.setOnvifResponseListener(this);
OnvifDevice device = new OnvifDevice("192.168.0.131", "username", "password");
```

### Services
Returns information about services on the device.

```java
onvifManager.getServices(device, new OnvifServicesListener() {
    @Override
    public void onServicesReceived(@Nonnull OnvifDevice onvifDevice, OnvifServices services) {
        
    }
});
```

### Device information
Returns basic device information from the device. This includes the manufacturer, serial number, hardwareId, ...

```java
onvifManager.getDeviceInformation(device, new OnvifDeviceInformationListener() {
    @Override
    public void onDeviceInformationReceived(@Nonnull OnvifDevice device, 
                                            @Nonnull OnvifDeviceInformation deviceInformation) {
        
    }
});
```

### Media Profiles
Returns pre-configured or dynamically configured profiles. This command lists all configured profiles in a device. The client does not need to know the media profile in order to use the command.

```java
onvifManager.getMediaProfiles(device, new OnvifMediaProfilesListener() {
    @Override
    public void onMediaProfilesReceived(@Nonnull OnvifDevice device, 
                                        @Nonnull List<OnvifMediaProfile> mediaProfiles) {
        
    }
});
```

### Media Stream URI
Returns a raw media stream URI that remains valid indefinitely even if the profile is changed.

```java
onvifManager.getMediaStreamURI(device, mediaProfiles.get(0), new OnvifMediaStreamURIListener() {
    @Override
    public void onMediaStreamURIReceived(@Nonnull OnvifDevice device, 
                                        @Nonnull OnvifMediaProfile profile, @Nonnull String uri) {
        
    }
});
```

### Media Snapshot URI
Returns a raw media snapshot URI that remains valid indefinitely even if the profile is changed.

```java
onvifManager.getMediaSnapshotURI(device, mediaProfiles.get(0), new OnvifMediaStreamURIListener() {
    @Override
    public void onMediaSnapshotURIReceived(@Nonnull OnvifDevice device, 
                                        @Nonnull OnvifMediaProfile profile, @Nonnull String uri) {
        
    }
});
```

## UPnP
---

With the ```UPnPManager``` it is possible to retrieve device information from a locally connected UPnP device. A ```UPnPDevice``` can be created manually or discovered from the ```DiscoveryManager``` using ```discovery.discover(DiscoveryMode.UPNP)```

```java
UPnPDevice device = new UPnPDevice("192.168.0.160");
device.setLocation("http://192.168.0.160:49152/rootdesc1.xml");
UPnPManager uPnPManager = new UPnPManager();
uPnPManager.getDeviceInformation(device, new UPnPDeviceInformationListener() {
    @Override
    public void onDeviceInformationReceived(@Nonnull UPnPDevice device, 
                                            @Nonnull UPnPDeviceInformation information) {
        Log.i(TAG, device.getHostName() + ": " + information.getFriendlyName());
    }
    @Override
    public void onError(@Nonnull UPnPDevice onvifDevice, int errorCode, String errorMessage) {
        Log.e(TAG, "Error: " + errorMessage);
    }
});
```

## Custom requests
---

It is possible to implement your custom ONVIF request by creating a new class and implementing the ```OnvifRequest``` interface and overriding the ```getXml()``` and ```getType()``` methods.

```java
public class PTZRequest implements OnvifRequest {
    @Override
    public String getXml() {
        return "<GetServices xmlns=\"http://www.onvif.org/ver10/device/wsdl\">" +
                "<IncludeCapability>false</IncludeCapability>" +
                "</GetServices>";
    }
    @Override
    public OnvifType getType() {
        return OnvifType.CUSTOM;
    }
}
```

and send it to the appropriate ```OnvifDevice```:

```java
onvifManager.sendOnvifRequest(device, new PTZRequest());
```

Use the ```OnvifResponseListener``` to receive responses from your custom requests.

## Android
---
In order to receive multicasts packets on your Android device, you'll have to acquire a lock on your WifiManager before making a discovery. Make sure to release the lock once the discovery is completed. More information can be found here: https://developer.android.com/reference/android/net/wifi/WifiManager.MulticastLock

```java
private void lockMulticast() {
    WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    if (wifi == null)
        return;

    WifiManager.MulticastLock lock = wifi.createMulticastLock("ONVIF");
    lock.acquire();
}
```

## Download [![](https://jitpack.io/v/lamba92/ONVIF-Java.svg)](https://jitpack.io/#lamba92/ONVIF-Java)

```kotlin
repositories {
    maven("https://dl.bintray.com/lamba92/com.github.lamba92")
}

dependencies {
    implementation("com.github.lamba92:ONVIF-Java:{LATEST_TAG}")
}
```

## Todos

 - Implementation ONVIF version management

## Pull Requests
---
Feel free to send pull requests. 

License
=======

    Copyright 2018 TELETASK BVBA.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    
[2]: https://bintray.com/tomasverhelst/ONVIF-Java/ONVIF-Java/1.0.0#files/be/teletask/onvif/onvif/1.0.0

