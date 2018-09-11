package be.teletask.onvif.models;



import java.util.Locale;

/**
 * Created by Tomas Verhelst on 06/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public abstract class Device implements Comparable<Device> {

    //Constants
    public static final String TAG = Device.class.getSimpleName();
    private static final String FORMAT_HTTP = "http://%s";

    //Attributes
    private String host;
    private String username;
    private String password;
    private boolean connected;

    //Constructors
    public Device(String host) {
        this(host, "", "");
    }

    public Device(String host, String username, String password) {
        this.host = buildUrl(host);
        this.username = username;
        this.password = password;
    }

    //Properties

    public String getHostName() {
        return host;
    }

    public void setHostName(String url) {
        host = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    @Override
    public int compareTo(Device device) {
        return getHostName().compareTo(device.getHostName());
    }

    private String buildUrl(String url) {
        if (url.startsWith("http://") || url.startsWith("https://"))
            return url;

        return String.format(Locale.getDefault(), FORMAT_HTTP, url);
    }

    public abstract DeviceType getType();
}
