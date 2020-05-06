package be.teletask.onvif.requests;

import be.teletask.onvif.models.OnvifType;

/**
 * Created by Gabor Szanto on 02/05/2020.
 */
public class RelativeMoveRequest implements OnvifRequest<Void> {
    //Constants
    public static final String TAG = RelativeMoveRequest.class.getSimpleName();

    //Attributes
    private String profileToken;
    private final Listener<Void> listener;
    private Double translationX; // pan X
    private Double translationY; // pan Y
    private Double zoom; // zoom

    //Constructors
    public RelativeMoveRequest(String profileToken, Double translationX, Double translationY, Double zoom, Listener<Void> listener) {
        this.profileToken = profileToken;
        this.listener = listener;
        this.translationX = translationX;
        this.translationY = translationY;
        this.zoom = zoom;
    }

    //Properties

    public Listener<Void> getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return "<tptz:RelativeMove xmlns:tptz=\"http://www.onvif.org/ver20/ptz/wsdl\" xmlns:tt=\"http://www.onvif.org/ver10/schema\">" +
                "<tptz:ProfileToken>" + profileToken + "</tptz:ProfileToken><tptz:Translation>" +
                (translationX != null || translationY != null ? "<tt:PanTilt x=\"" + translationX + "\" y=\"" + translationY + "\"/>" : "") +
                (zoom != null ? "<tt:Zoom x=" + zoom + "/>" : "") +
                "</tptz:Translation></tptz:RelativeMove>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.RELATIVE_MOVE;
    }

}
/*
'ProfileToken': this.current_profile['token'],
        'Velocity': { 'x': x, 'y': y, 'z': z },
        'Timeout': timeout*/
