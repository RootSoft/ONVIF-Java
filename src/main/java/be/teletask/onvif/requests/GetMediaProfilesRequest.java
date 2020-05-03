package be.teletask.onvif.requests;

import be.teletask.onvif.models.OnvifMediaProfile;
import be.teletask.onvif.models.OnvifType;

import java.util.List;


/**
 * Created by Tomas Verhelst on 04/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class GetMediaProfilesRequest implements OnvifRequest<List<OnvifMediaProfile>> {

    //Constants
    public static final String TAG = GetMediaProfilesRequest.class.getSimpleName();

    //Attributes
    private final Listener<List<OnvifMediaProfile>> listener;

    //Constructors
    public GetMediaProfilesRequest(Listener<List<OnvifMediaProfile>> listener) {
        super();
        this.listener = listener;
    }

    //Properties

    public Listener<List<OnvifMediaProfile>> getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return "<GetProfiles xmlns=\"http://www.onvif.org/ver10/media/wsdl\"/>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GET_MEDIA_PROFILES;
    }

}
