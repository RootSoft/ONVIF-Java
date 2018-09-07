package be.teletask.onvif.models;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomas Verhelst on 03/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class OnvifDevice extends Device {

    //Constants
    public static final String TAG = OnvifDevice.class.getSimpleName();

    //Attributes
    private OnvifServices path;
    private List<String> addresses;

    //Constructors
    public OnvifDevice(@NotNull String hostName) {
        this(hostName, "", "");
    }

    private OnvifDevice(@NotNull String hostName, @NotNull String username, @NotNull String password) {
        super(hostName, username, password);
        path = new OnvifServices();
        addresses = new ArrayList<>();
    }

    //Methods
    public void addAddress(String address) {
        addresses.add(address);
    }

    //Properties
    public OnvifServices getPath() {
        return path;
    }

    public void setPath(OnvifServices path) {
        this.path = path;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    @Override
    public DeviceType getType() {
        return DeviceType.ONVIF;
    }

}
