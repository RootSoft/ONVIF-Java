package be.teletask.onvif.upnp;

import be.teletask.onvif.models.UPnPDevice;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Tomas Verhelst on 03/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public interface UPnPResponseListener {

    void onResponse(@NotNull UPnPDevice onvifDevice);

    void onError(@NotNull UPnPDevice onvifDevice, int errorCode, String errorMessage);
}
