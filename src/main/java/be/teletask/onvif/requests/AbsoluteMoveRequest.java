package be.teletask.onvif.requests;

import be.teletask.onvif.models.OnvifType;

/**
 * Created by Gabor Szanto on 02/05/2020.
 */
public class AbsoluteMoveRequest implements OnvifRequest<Void> {
    //Constants
    public static final String TAG = AbsoluteMoveRequest.class.getSimpleName();

    //Attributes
    private final Listener<Void> listener;
    private String profileToken;
    private Double positionX;
    private Double positionY;
    private Double zoom;

    //Constructors

    public AbsoluteMoveRequest(String profileToken, Double positionX, Double positionY, Double zoom, Listener<Void> listener) {
        this.listener = listener;
        this.profileToken = profileToken;
        this.positionX = positionX;
        this.positionY = positionY;
        this.zoom = zoom;
    }

    //Properties

    public Listener<Void> getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return "<tptz:AbsoluteMove xmlns:tptz=\"http://www.onvif.org/ver20/ptz/wsdl\" xmlns:tt=\"http://www.onvif.org/ver10/schema\">" +
                "<tptz:ProfileToken>" + profileToken + "</tptz:ProfileToken>" +
                "<tptz:Position>" +
                (positionX != null || positionY != null ? "<tt:PanTilt x=\"" + positionX + "\" y=\"" + positionY + "\"/>" :"") +
                (zoom != null ? "<tt:Zoom x=" + zoom + "/>" : "") +
                "</tptz:Position></tptz:AbsoluteMove>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.ABSOLUTE_MOVE;
    }
}
