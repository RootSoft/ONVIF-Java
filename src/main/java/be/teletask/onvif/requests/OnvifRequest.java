package be.teletask.onvif.requests;

import be.teletask.onvif.models.OnvifDevice;
import be.teletask.onvif.models.OnvifType;

/**
 * Created by Tomas Verhelst on 03/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public interface OnvifRequest<T> {

    String getXml();

    OnvifType getType();

    Listener<T> getListener();

    interface Listener<T> {
        void onSuccess(OnvifDevice device, T data);

        void onError(OnvifException exception);
    }

    class OnvifException extends Exception {
        OnvifDevice onvifDevice;
        int errorCode;

        public OnvifException(OnvifDevice onvifDevice, int errorCode, String message) {
            super(message);
            this.onvifDevice = onvifDevice;
            this.errorCode = errorCode;
        }
    }
}
