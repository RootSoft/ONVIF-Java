package be.teletask.onvif.requests;

import be.teletask.onvif.models.OnvifServices;
import be.teletask.onvif.models.OnvifType;


/**
 * Created by Tomas Verhelst on 04/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class GetServicesRequest implements OnvifRequest<OnvifServices> {

    //Constants
    public static final String TAG = GetServicesRequest.class.getSimpleName();

    //Attributes
    private final Listener<OnvifServices> listener;

    //Constructors
    public GetServicesRequest(Listener<OnvifServices> listener) {
        super();
        this.listener = listener;
    }

    //Properties

    public Listener<OnvifServices> getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return "<GetServices xmlns=\"http://www.onvif.org/ver10/device/wsdl\">" +
                "<IncludeCapability>false</IncludeCapability>" +
                "</GetServices>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GET_SERVICES;
    }

}
