package be.teletask.onvif.listeners;

import be.teletask.onvif.models.OnvifDevice;
import be.teletask.onvif.responses.OnvifResponse;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Tomas Verhelst on 03/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public interface OnvifResponseListener {

    void onResponse(@NotNull OnvifDevice onvifDevice, @NotNull OnvifResponse response);

    void onError(@NotNull OnvifDevice onvifDevice, int errorCode, String errorMessage);
}
