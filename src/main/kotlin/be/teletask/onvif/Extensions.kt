package be.teletask.onvif

import be.teletask.onvif.coroutines.*
import be.teletask.onvif.models.OnvifDevice
import be.teletask.onvif.models.OnvifDeviceInformation
import be.teletask.onvif.models.OnvifMediaProfile

val defaultOnvifManager by lazy { OnvifManager() }

suspend fun OnvifDevice.getInformation(om: OnvifManager = defaultOnvifManager) = awaitDeviceRequest<OnvifDeviceInformation> { om.getDeviceInformation(this, it) }

suspend fun OnvifDevice.getMediaProfiles(om: OnvifManager = defaultOnvifManager) = awaitDeviceRequest<List<OnvifMediaProfile>> { om.getMediaProfiles(this, it) }

suspend fun OnvifDevice.getMediaStreamUri(profile: OnvifMediaProfile, om: OnvifManager = defaultOnvifManager) = awaitDeviceRequest<String> { om.getMediaStreamURI(this, profile, it) }

suspend fun OnvifDevice.getMediaSnapshotUri(profile: OnvifMediaProfile, om: OnvifManager = defaultOnvifManager) = awaitDeviceRequest<String> { om.getMediaSnapshotURI(this, profile, it) }

suspend fun OnvifDevice.getAllMediaStreamUris(om: OnvifManager = defaultOnvifManager) = getMediaProfiles(om).map { getMediaStreamUri(it, om) }

suspend fun OnvifDevice.getAllMediaSnapshotUris(om: OnvifManager = defaultOnvifManager) = getMediaProfiles(om).map { getMediaSnapshotUri(it, om) }

suspend fun OnvifDevice.ptzContinuousMove(profileToken: String, velocityX: Double, velocityY: Double, velocityZ: Double?, timeout: Int?, om: OnvifManager = defaultOnvifManager) = awaitDeviceRequest<Void> { om.ptzContinuousMove(this, profileToken, velocityX, velocityY, velocityZ, timeout, it) }

suspend fun OnvifDevice.ptzRelativeMove(profileToken: String, translationX: Double?, translationY: Double?, zoom: Double?, om: OnvifManager = defaultOnvifManager) = awaitDeviceRequest<Void> { om.ptzRelativeMove(this, profileToken, translationX, translationY, zoom, it) }

suspend fun OnvifDevice.ptzAbsoluteMove(profileToken: String, positionX: Double?, positionY: Double?, zoom: Double?, om: OnvifManager = defaultOnvifManager) = awaitDeviceRequest<Void> { om.ptzAbsoluteMove(this, profileToken, positionX, positionY, zoom, it) }

suspend fun OnvifDevice.ptzStop(profileToken: String, panTilt: Boolean = true, zoom: Boolean = true, om: OnvifManager = defaultOnvifManager) = awaitDeviceRequest<Void> { om.ptzStop(this, profileToken, panTilt, zoom, it) }
