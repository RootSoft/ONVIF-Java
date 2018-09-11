package be.teletask.onvif.requests;

import be.teletask.onvif.listeners.OnvifMediaStreamURIListener;
import be.teletask.onvif.models.OnvifMediaProfile;
import be.teletask.onvif.models.OnvifType;


/**
 * Created by Tomas Verhelst on 04/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class GetMediaStreamRequest implements OnvifRequest {

    //Constants
    public static final String TAG = GetMediaStreamRequest.class.getSimpleName();

    //Attributes
    private final OnvifMediaProfile mediaProfile;
    private final OnvifMediaStreamURIListener listener;

    //Constructors
    public GetMediaStreamRequest(OnvifMediaProfile mediaProfile, OnvifMediaStreamURIListener listener) {
        super();
        this.mediaProfile = mediaProfile;
        this.listener = listener;
    }

    //Properties

    public OnvifMediaProfile getMediaProfile() {
        return mediaProfile;
    }

    public OnvifMediaStreamURIListener getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return "<GetStreamUri xmlns=\"http://www.onvif.org/ver10/media/wsdl\">"
                + "<StreamSetup>"
                + "<Stream xmlns=\"http://www.onvif.org/ver10/schema\">RTP-Unicast</Stream>"
                + "<Transport xmlns=\"http://www.onvif.org/ver10/schema\"><Protocol>RTSP</Protocol></Transport>"
                + "</StreamSetup>"
                + "<ProfileToken>" + mediaProfile.getToken() + "</ProfileToken>"
                + "</GetStreamUri>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GET_STREAM_URI;
    }

}
