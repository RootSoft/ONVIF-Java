package be.teletask.onvif.requests;

import be.teletask.onvif.models.OnvifType;

/**
 * Created by Gabor Szanto on 02/05/2020.
 */
public class ContinuousMoveRequest implements OnvifRequest<Void> {
    //Constants
    public static final String TAG = ContinuousMoveRequest.class.getSimpleName();

    //Attributes
    private final Listener<Void> listener;
    private String profileToken;
    private Integer timeout;
    private Double velocityX;
    private Double velocityY;
    private Double velocityZ;

    //Constructors

    public ContinuousMoveRequest(String profileToken, Integer timeout, Double velocityX, Double velocityY, Double velocityZ, Listener<Void> listener) {
        this.listener = listener;
        this.profileToken = profileToken;
        this.timeout = timeout;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
    }

    //Properties

    public Listener<Void> getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return "<tptz:ContinuousMove xmlns:tptz=\"http://www.onvif.org/ver20/ptz/wsdl\" xmlns:tt=\"http://www.onvif.org/ver10/schema\">" +
                "<tptz:ProfileToken>" + profileToken + "</tptz:ProfileToken>" +
                "<tptz:Velocity><tt:PanTilt x=\"" + velocityX + "\" y=\"" + velocityY + "\"></tt:PanTilt></tptz:Velocity>" +
                (timeout != null ? "<tptz:Timeout>PT" + timeout + "S</tptz:Timeout>" : "") +
                "</tptz:ContinuousMove>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.CONTINUOUS_MOVE;
    }
}
