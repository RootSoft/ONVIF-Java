package be.teletask.onvif;

import be.teletask.onvif.listeners.*;
import be.teletask.onvif.models.OnvifDevice;
import be.teletask.onvif.models.OnvifDeviceInformation;
import be.teletask.onvif.models.OnvifMediaProfile;
import be.teletask.onvif.models.OnvifServices;
import be.teletask.onvif.requests.*;
import be.teletask.onvif.responses.OnvifResponse;

import java.util.List;


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
    public void getServices(OnvifDevice device, OnvifRequest.Listener<OnvifServices> listener) {
        final GetServicesRequest request = new GetServicesRequest(listener);
        executor.sendRequest(device, request);
    }

    public void getDeviceInformation(OnvifDevice device, OnvifRequest.Listener<OnvifDeviceInformation> listener) {
        final GetDeviceInformationRequest request = new GetDeviceInformationRequest(listener);
        executor.sendRequest(device, request);
    }

    public void getMediaProfiles(OnvifDevice device, OnvifRequest.Listener<List<OnvifMediaProfile>> listener) {
        final GetMediaProfilesRequest request = new GetMediaProfilesRequest(listener);
        executor.sendRequest(device, request);
    }

    public void getMediaStreamURI(OnvifDevice device, OnvifMediaProfile profile, OnvifRequest.Listener<String> listener) {
        final GetMediaStreamRequest request = new GetMediaStreamRequest(profile, listener);
        executor.sendRequest(device, request);
    }

    public void getMediaSnapshotURI(OnvifDevice device, OnvifMediaProfile profile, OnvifRequest.Listener<String> listener) {
        final GetSnapshotUriRequest request = new GetSnapshotUriRequest(profile, listener);
        executor.sendRequest(device, request);
    }

    public void ptzContinuousMove(OnvifDevice device, String profileToken, Double velocityX, Double velocityY, Double velocityZ, Integer timeout, OnvifRequest.Listener<Void> listener) {
        final ContinuousMoveRequest request = new ContinuousMoveRequest(profileToken, timeout, velocityX, velocityY, velocityZ, listener);
        executor.sendRequest(device, request);
    }

    public void ptzRelativeMove(OnvifDevice device, String profileToken, Double translationX, Double translationY, Double zoom, OnvifRequest.Listener<Void> listener) {
        final RelativeMoveRequest request = new RelativeMoveRequest(profileToken, translationX, translationY, zoom, listener);
        executor.sendRequest(device, request);
    }

    public void ptzAbsoluteMove(OnvifDevice device, String profileToken, Double positionX, Double positionY, Double zoom, OnvifRequest.Listener<Void> listener) {
        final AbsoluteMoveRequest request = new AbsoluteMoveRequest(profileToken, positionX, positionY, zoom, listener);
        executor.sendRequest(device, request);
    }

    public void ptzStop(OnvifDevice device, String profileToken, boolean panTilt, boolean zoom, OnvifRequest.Listener<Void> listener) {
        final StopRequest request = new StopRequest(profileToken, panTilt, zoom, listener);
        executor.sendRequest(device, request);
    }

    public void sendOnvifRequest(OnvifDevice device, OnvifRequest<?> request) {
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
    public void onResponse(OnvifDevice onvifDevice, OnvifResponse response) {
        if (onvifResponseListener != null)
            onvifResponseListener.onResponse(onvifDevice, response);
    }

    @Override
    public void onError(OnvifRequest.OnvifException exception) {
        if (onvifResponseListener != null)
            onvifResponseListener.onError(exception);
    }

}
