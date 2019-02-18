package be.teletask.onvif.requests;

import be.teletask.onvif.listeners.OnvifDeviceInformationListener;
import be.teletask.onvif.models.OnvifType;


/**
 * Created by Tomas Verhelst on 04/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class GetDeviceInformationRequest implements OnvifRequest {

    //Constants
    public static final String TAG = GetDeviceInformationRequest.class.getSimpleName();

    //Attributes
    private final OnvifDeviceInformationListener listener;

    //Constructors
    public GetDeviceInformationRequest(OnvifDeviceInformationListener listener) {
        super();
        this.listener = listener;
    }

    //Properties

    public OnvifDeviceInformationListener getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return "<GetDeviceInformation xmlns=\"http://www.onvif.org/ver10/device/wsdl\">" + "</GetDeviceInformation>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GET_DEVICE_INFORMATION;
    }

}
