package be.teletask.onvif.models;

/**
 * Created by Tomas Verhelst on 04/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public enum DiscoveryType {
    DEVICE(0, "Device"),
    NETWORK_VIDEO_TRANSMITTER(1, "NetworkVideoTransmitter");

    public final int id;
    public final String type;

    DiscoveryType(int id, String type) {
        this.id = id;
        this.type = type;
    }

}
