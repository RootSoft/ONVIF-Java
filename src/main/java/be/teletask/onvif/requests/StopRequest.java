package be.teletask.onvif.requests;

import be.teletask.onvif.models.OnvifType;

/**
 * Created by Gabor Szanto on 02/05/2020.
 */
public class StopRequest implements OnvifRequest<Void> {
    //Constants
    public static final String TAG = StopRequest.class.getSimpleName();

    //Attributes
    private final Listener<Void> listener;
    private String profileToken;
    private boolean panTilt;
    private boolean zoom;

    //Constructors

    public StopRequest(String profileToken, boolean panTilt, boolean zoom, Listener<Void> listener) {
        this.listener = listener;
        this.profileToken = profileToken;
        this.panTilt = panTilt;
        this.zoom = zoom;
    }

    //Properties

    public Listener<Void> getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return "<tptz:Stop xmlns:tptz=\"http://www.onvif.org/ver20/ptz/wsdl\" xmlns:tt=\"http://www.onvif.org/ver10/schema\">" +
                "<tptz:ProfileToken>" + profileToken + "</tptz:ProfileToken>" +
                "<tptz:PanTilt>" + panTilt + " </tptz:PanTilt>" +
                "<tptz:Zoom>" + zoom + " </tptz:Zoom>" +
                "</tptz:Stop>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.PTZ_STOP;
    }
}
