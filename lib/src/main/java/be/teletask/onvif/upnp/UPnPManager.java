package be.teletask.onvif.upnp;

import be.teletask.onvif.models.UPnPDevice;

import org.jetbrains.annotations.Nullable;

/**
 * Created by Tomas Verhelst on 06/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class UPnPManager implements UPnPResponseListener {

    //Constants
    public static final String TAG = UPnPManager.class.getSimpleName();

    //Attributes
    private UPnPExecutor executor;
    private UPnPResponseListener responseListener;

    //Constructors
    public UPnPManager() {
        this(null);
    }

    private UPnPManager(@Nullable UPnPResponseListener responseListener) {
        this.responseListener = responseListener;
        executor = new UPnPExecutor(this);
    }

    /**
     * Gets the device information for a given UPnP device.
     * The location attribute of the device should exist.
     * This could be filled in manually or from a discovery
     *
     * @param device
     */
    public void getDeviceInformation(UPnPDevice device, UPnPDeviceInformationListener listener) {
        executor.getDeviceInformation(device, listener);
    }

    public void setResponseListener(UPnPResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    @Override
    public void onResponse(UPnPDevice onvifDevice) {

    }

    @Override
    public void onError(UPnPDevice onvifDevice, int errorCode, String errorMessage) {

    }

}
