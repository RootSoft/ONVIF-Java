package be.teletask.onvif.models;

/**
 * Created by Tomas Verhelst on 04/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public enum OnvifType {
    GET_SERVICES(0, "http://www.onvif.org/ver10/device/wsdl"),
    GET_DEVICE_INFORMATION(1, "http://www.onvif.org/ver10/device/wsdl"),
    GET_MEDIA_PROFILES(2, "http://www.onvif.org/ver10/media/wsdl"),
    GET_STREAM_URI(3, "http://www.onvif.org/ver10/media/wsdl");

    public final int id;
    public final String namespace;

    OnvifType(int id, String namespace) {
        this.id = id;
        this.namespace = namespace;
    }

}
