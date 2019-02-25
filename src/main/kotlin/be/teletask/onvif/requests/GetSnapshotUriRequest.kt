package be.teletask.onvif.requests

import be.teletask.onvif.listeners.OnvifMediaSnapshotURIListener
import be.teletask.onvif.models.OnvifMediaProfile
import be.teletask.onvif.models.OnvifType

class GetSnapshotUriRequest(val profile: OnvifMediaProfile, val listener: OnvifMediaSnapshotURIListener) : OnvifRequest {

    //Constants
    val TAG = GetSnapshotUriRequest::class.java.simpleName

    override fun getXml()= """
        <GetSnapshotUri xmlns="http://www.onvif.org/ver10/media/wsdl">
            <ProfileToken>${profile.token}</ProfileToken>
        </GetSnapshotUri>
    """.trimIndent()

    override fun getType() = OnvifType.GET_SNAPSHOT_URI
}