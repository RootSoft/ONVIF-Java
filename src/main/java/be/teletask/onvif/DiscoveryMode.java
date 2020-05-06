package be.teletask.onvif;

/**
 * Created by Tomas Verhelst on 05/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public enum DiscoveryMode {
    ONVIF(3702),
    UPNP(1900);

    public final int port;

    DiscoveryMode(int port) {
        this.port = port;
    }

}
