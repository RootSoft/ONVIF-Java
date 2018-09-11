package be.teletask.onvif.upnp;

import be.teletask.onvif.models.UPnPDevice;


/**
 * Created by Tomas Verhelst on 03/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public interface UPnPDeviceInformationListener {

    void onDeviceInformationReceived(UPnPDevice device, UPnPDeviceInformation deviceInformation);

    void onError(UPnPDevice onvifDevice, int errorCode, String errorMessage);

}
