package be.teletask.onvif.listeners;

import be.teletask.onvif.models.OnvifDevice;
import be.teletask.onvif.models.OnvifServices;


/**
 * Created by Tomas Verhelst on 03/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public interface OnvifServicesListener {

    void onServicesReceived(OnvifDevice onvifDevice, OnvifServices paths);

}
