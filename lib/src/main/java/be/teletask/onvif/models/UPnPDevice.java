package be.teletask.onvif.models;

import be.teletask.onvif.upnp.UPnPDeviceInformation;


/**
 * Created by Tomas Verhelst on 06/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class UPnPDevice extends Device {

    //Constants
    public static final String TAG = UPnPDevice.class.getSimpleName();

    //Attributes
    private String header;
    private String location;
    private String server;
    private String usn;
    private String st;
    private UPnPDeviceInformation deviceInformation;

    //Constructors

    public UPnPDevice(String hostName) {
        this(hostName, "", "", "", "", "");
    }

    public UPnPDevice(String hostName, String header, String location,
                      String server, String usn, String st) {
        super(hostName);
        deviceInformation = new UPnPDeviceInformation();
        initHeaders(header, location, server, usn, st);
    }

    //Methods
    private void initHeaders(String header, String location,
                             String server, String usn, String st) {
        this.header = header;
        this.location = location;
        this.server = server;
        this.usn = usn;
        this.st = st;
    }

    //Properties

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getUSN() {
        return usn;
    }

    public void setUSN(String usn) {
        this.usn = usn;
    }

    public String getST() {
        return st;
    }

    public void setST(String st) {
        this.st = st;
    }

    public UPnPDeviceInformation getDeviceInformation() {
        return deviceInformation;
    }

    public void setDeviceInformation(UPnPDeviceInformation deviceInformation) {
        this.deviceInformation = deviceInformation;
    }

    @Override
    public DeviceType getType() {
        return DeviceType.UPNP;
    }

}
