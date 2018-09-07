package be.teletask.onvif.listeners;

import be.teletask.onvif.models.OnvifDevice;
import be.teletask.onvif.models.OnvifMediaProfile;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Tomas Verhelst on 03/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public interface OnvifMediaStreamURIListener {

    void onMediaStreamURIReceived(@NotNull OnvifDevice device, @NotNull OnvifMediaProfile profile, @NotNull String uri);

}
