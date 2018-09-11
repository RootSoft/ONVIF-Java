package be.teletask.onvif;

import be.teletask.onvif.listeners.OnvifResponseListener;
import be.teletask.onvif.models.OnvifDevice;
import be.teletask.onvif.models.OnvifServices;
import be.teletask.onvif.parsers.GetDeviceInformationParser;
import be.teletask.onvif.parsers.GetMediaProfilesParser;
import be.teletask.onvif.parsers.GetMediaStreamParser;
import be.teletask.onvif.parsers.GetServicesParser;
import be.teletask.onvif.requests.*;
import be.teletask.onvif.responses.OnvifResponse;
import com.burgstaller.okhttp.AuthenticationCacheInterceptor;
import com.burgstaller.okhttp.CachingAuthenticatorDecorator;
import com.burgstaller.okhttp.digest.CachingAuthenticator;
import com.burgstaller.okhttp.digest.Credentials;
import com.burgstaller.okhttp.digest.DigestAuthenticator;
import okhttp3.*;
import okio.Buffer;


import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tomas Verhelst on 03/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class OnvifExecutor {

    //Constants
    public static final String TAG = OnvifExecutor.class.getSimpleName();

    //Attributes
    private OkHttpClient client;
    private MediaType reqBodyType;
    private RequestBody reqBody;

    private Credentials credentials;
    private OnvifResponseListener onvifResponseListener;

    //Constructors

    OnvifExecutor(OnvifResponseListener onvifResponseListener) {
        this.onvifResponseListener = onvifResponseListener;
        credentials = new Credentials("username", "password");
        DigestAuthenticator authenticator = new DigestAuthenticator(credentials);
        Map<String, CachingAuthenticator> authCache = new ConcurrentHashMap<>();

        client = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(10000, TimeUnit.SECONDS)
                .authenticator(new CachingAuthenticatorDecorator(authenticator, authCache))
                .addInterceptor(new AuthenticationCacheInterceptor(authCache))
                .build();

        reqBodyType = MediaType.parse("application/soap+xml; charset=utf-8;");
    }

    //Methods

    /**
     * Sends a request to the Onvif-compatible device.
     *
     * @param device
     * @param request
     */
    void sendRequest(OnvifDevice device, OnvifRequest request) {
        credentials.setUserName(device.getUsername());
        credentials.setPassword(device.getPassword());
        reqBody = RequestBody.create(reqBodyType, OnvifXMLBuilder.getSoapHeader() + request.getXml() + OnvifXMLBuilder.getEnvelopeEnd());
        performXmlRequest(device, request, buildOnvifRequest(device, request));
    }

    /**
     * Clears up the resources.
     */
    void clear() {
        onvifResponseListener = null;
    }

    //Properties

    public void setOnvifResponseListener(OnvifResponseListener onvifResponseListener) {
        this.onvifResponseListener = onvifResponseListener;
    }

    private void performXmlRequest(OnvifDevice device, OnvifRequest request, Request xmlRequest) {
        if (xmlRequest == null)
            return;

        client.newCall(xmlRequest)
                .enqueue(new Callback() {

                    @Override
                    public void onResponse(Call call, Response xmlResponse) throws IOException {

                        OnvifResponse response = new OnvifResponse(request);
                        ResponseBody xmlBody = xmlResponse.body();

                        if (xmlResponse.code() == 200 && xmlBody != null) {
                            response.setSuccess(true);
                            response.setXml(xmlBody.string());
                            parseResponse(device, response);
                            return;
                        }

                        String errorMessage = "";
                        if (xmlBody != null)
                            errorMessage = xmlBody.string();

                        onvifResponseListener.onError(device, xmlResponse.code(), errorMessage);
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        onvifResponseListener.onError(device, -1, e.getMessage());
                    }

                });
    }

    private void parseResponse(OnvifDevice device, OnvifResponse response) {
        switch (response.request().getType()) {
            case GET_SERVICES:
                OnvifServices path = new GetServicesParser().parse(response);
                device.setPath(path);
                ((GetServicesRequest) response.request()).getListener().onServicesReceived(device, path);
                break;
            case GET_DEVICE_INFORMATION:
                ((GetDeviceInformationRequest) response.request()).getListener().onDeviceInformationReceived(device,
                        new GetDeviceInformationParser().parse(response));
                break;
            case GET_MEDIA_PROFILES:
                ((GetMediaProfilesRequest) response.request()).getListener().onMediaProfilesReceived(device,
                        new GetMediaProfilesParser().parse(response));
                break;
            case GET_STREAM_URI:
                GetMediaStreamRequest streamRequest = (GetMediaStreamRequest) response.request();
                streamRequest.getListener().onMediaStreamURIReceived(device, streamRequest.getMediaProfile(),
                        new GetMediaStreamParser().parse(response));
                break;
            default:
                onvifResponseListener.onResponse(device, response);
                break;
        }
    }

    private Request buildOnvifRequest(OnvifDevice device, OnvifRequest request) {
        return new Request.Builder()
                .url(getUrlForRequest(device, request))
                .addHeader("Content-Type", "text/xml; charset=utf-8")
                .post(reqBody)
                .build();
    }

    private String getUrlForRequest(OnvifDevice device, OnvifRequest request) {
        return device.getHostName() + getPathForRequest(device, request);
    }

    private String getPathForRequest(OnvifDevice device, OnvifRequest request) {
        switch (request.getType()) {
            case GET_SERVICES:
                return device.getPath().getServicesPath();
            case GET_DEVICE_INFORMATION:
                return device.getPath().getDeviceInformationPath();
            case GET_MEDIA_PROFILES:
                return device.getPath().getProfilesPath();
            case GET_STREAM_URI:
                return device.getPath().getStreamURIPath();
        }

        return device.getPath().getServicesPath();
    }

    private String bodyToString(Request request) {

        try {
            Request copy = request.newBuilder().build();
            Buffer buffer = new Buffer();
            if (copy.body() != null)
                copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
