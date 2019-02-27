package be.teletask.onvif

import be.teletask.onvif.coroutines.*
import be.teletask.onvif.models.OnvifDevice
import be.teletask.onvif.models.OnvifMediaProfile

val defaultOnvifManager by lazy { OnvifManager() }

suspend fun OnvifDevice.getInformation(om: OnvifManager = defaultOnvifManager)
        = awaitDeviceInformation { om.getDeviceInformation(this, it) }

suspend fun OnvifDevice.getMediaProfiles(om: OnvifManager = defaultOnvifManager)
        = awaitDeviceMediaProfiles { om.getMediaProfiles(this, it) }

suspend fun OnvifDevice.getMediaStreamUri(profile: OnvifMediaProfile, om: OnvifManager = defaultOnvifManager)
        = awaitDeviceMediaStreamUri { om.getMediaStreamURI(this, profile, it) }

suspend fun OnvifDevice.getMediaSnapshotUri(profile: OnvifMediaProfile, om: OnvifManager = defaultOnvifManager)
        = awaitDeviceMediaSnapshotUri { om.getMediaSnapshotURI(this, profile, it) }

suspend fun OnvifDevice.getAllMediaStreamUris(om: OnvifManager = defaultOnvifManager) = 
        getMediaProfiles(om).map { getMediaStreamUri(it, om) }

suspend fun OnvifDevice.getAllMediaSsnapshotUris(om: OnvifManager = defaultOnvifManager) =
        getMediaProfiles(om).map { getMediaSnapshotUri(it, om) }