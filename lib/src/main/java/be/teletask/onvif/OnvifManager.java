package be.teletask.onvif;

import be.teletask.onvif.listeners.*;
import be.teletask.onvif.models.OnvifDevice;
import be.teletask.onvif.models.OnvifMediaProfile;
import be.teletask.onvif.requests.*;
import be.teletask.onvif.responses.OnvifResponse;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Tomas Verhelst on 03/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class OnvifManager implements OnvifResponseListener {

    //Constants
    public final static String TAG = OnvifManager.class.getSimpleName();

    //Attributes
    private OnvifExecutor executor;
    private OnvifResponseListener onvifResponseListener;

    //Constructors
    public OnvifManager() {
        this(null);
    }

    private OnvifManager(OnvifResponseListener onvifResponseListener) {
        this.onvifResponseListener = onvifResponseListener;
        executor = new OnvifExecutor(this);
    }

    //Methods
    public void getServices(@NotNull OnvifDevice device, @NotNull OnvifServicesListener listener) {
        OnvifRequest request = new GetServicesRequest(listener);
        executor.sendRequest(device, request);
    }

    public void getDeviceInformation(@NotNull OnvifDevice device, @NotNull OnvifDeviceInformationListener listener) {
        OnvifRequest request = new GetDeviceInformationRequest(listener);
        executor.sendRequest(device, request);
    }

    public void getMediaProfiles(@NotNull OnvifDevice device, @NotNull OnvifMediaProfilesListener listener) {
        OnvifRequest request = new GetMediaProfilesRequest(listener);
        executor.sendRequest(device, request);
    }

    public void getMediaStreams(@NotNull OnvifDevice device, @NotNull OnvifMediaProfile profile, @NotNull OnvifMediaStreamURIListener listener) {
        OnvifRequest request = new GetMediaStreamRequest(profile, listener);
        executor.sendRequest(device, request);
    }

    public void sendOnvifRequest(@NotNull OnvifDevice device, @NotNull OnvifRequest request) {
        executor.sendRequest(device, request);
    }

    public void setOnvifResponseListener(OnvifResponseListener onvifResponseListener) {
        this.onvifResponseListener = onvifResponseListener;
    }

    /**
     * Clear up the resources.
     */
    public void destroy() {
        onvifResponseListener = null;
        executor.clear();
    }

    @Override
    public void onResponse(@NotNull OnvifDevice onvifDevice, @NotNull OnvifResponse response) {
        if (onvifResponseListener != null)
            onvifResponseListener.onResponse(onvifDevice, response);
    }

    @Override
    public void onError(@NotNull OnvifDevice onvifDevice, int errorCode, String errorMessage) {
        if (onvifResponseListener != null)
            onvifResponseListener.onError(onvifDevice, errorCode, errorMessage);
    }

}
