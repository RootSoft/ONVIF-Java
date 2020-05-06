package be.teletask.onvif.requests

import be.teletask.onvif.models.OnvifMediaProfile
import be.teletask.onvif.models.OnvifType

class GetSnapshotUriRequest(val profile: OnvifMediaProfile, val _listener: OnvifRequest.Listener<String>) : OnvifRequest<String> {

    //Constants
    val TAG = GetSnapshotUriRequest::class.java.simpleName

    override fun getXml() = """
        <GetSnapshotUri xmlns="http://www.onvif.org/ver10/media/wsdl">
            <ProfileToken>${profile.token}</ProfileToken>
        </GetSnapshotUri>
    """.trimIndent()

    override fun getType() = OnvifType.GET_SNAPSHOT_URI
    override fun getListener(): OnvifRequest.Listener<String> = _listener
}
