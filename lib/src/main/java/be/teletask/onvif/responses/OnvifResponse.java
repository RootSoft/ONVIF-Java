package be.teletask.onvif.responses;

import be.teletask.onvif.requests.OnvifRequest;

/**
 * Created by Tomas Verhelst on 03/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class OnvifResponse<T> {

    //Constants
    public static final String TAG = OnvifResponse.class.getSimpleName();

    //Attributes
    private boolean success;
    private int errorCode;
    private String errorMessage;
    private String xml;

    private OnvifRequest onvifRequest;

    //Constructors
    public OnvifResponse(String xml) {
        this.xml = xml;
    }

    public OnvifResponse(OnvifRequest onvifRequest) {
        this.onvifRequest = onvifRequest;
    }

    //Properties

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public OnvifRequest request() {
        return onvifRequest;
    }
}
