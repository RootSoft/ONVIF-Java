package be.teletask.onvif.listeners;

import be.teletask.onvif.models.OnvifDevice;
import be.teletask.onvif.models.OnvifMediaProfile;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Tomas Verhelst on 03/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public interface OnvifMediaProfilesListener {

    void onMediaProfilesReceived(@NotNull OnvifDevice device, @NotNull List<OnvifMediaProfile> mediaProfiles);

}
