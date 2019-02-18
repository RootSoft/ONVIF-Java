package be.teletask.onvif.listeners;

import java.util.List;

import be.teletask.onvif.models.Device;

/**
 * Created by Tomas Verhelst on 04/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public interface DiscoveryListener {

    void onDiscoveryStarted();

    void onDevicesFound(List<Device> devices);

}
