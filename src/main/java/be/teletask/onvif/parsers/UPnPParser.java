package be.teletask.onvif.parsers;

import be.teletask.onvif.responses.OnvifResponse;
import be.teletask.onvif.upnp.UPnPDeviceInformation;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Tomas Verhelst on 06/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class UPnPParser extends OnvifParser<UPnPDeviceInformation> {

    //Constants
    public static final String TAG = UPnPParser.class.getSimpleName();
    private static final String KEY_DEVICE_TYPE = "deviceType";
    private static final String KEY_FRIENDLY_NAME = "friendlyName";
    private static final String KEY_MANUFACTURER = "manufacturer";
    private static final String KEY_MANUFACTURER_URL = "manufacturerURL";
    private static final String KEY_MODEL_DESCRIPTION = "modelDescription";
    private static final String KEY_MODEL_NAME = "modelName";
    private static final String KEY_MODEL_NUMBER = "modelNumber";
    private static final String KEY_MODEL_URL = "modelURL";
    private static final String KEY_SERIAL_NUMBER = "serialNumber";
    private static final String KEY_UDN = "UDN";
    private static final String KEY_PRESENTATION_URL = "presentationURL";
    private static final String KEY_URL_BASE = "URLBase";

    //Attributes

    @Override
    public UPnPDeviceInformation parse(OnvifResponse response) {
        UPnPDeviceInformation deviceInformation = new UPnPDeviceInformation();

        try {
            getXpp().setInput(new StringReader(response.getXml()));
            eventType = getXpp().getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG && getXpp().getName().equals(KEY_DEVICE_TYPE)) {
                    getXpp().next();
                    deviceInformation.setDeviceType(getXpp().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXpp().getName().equals(KEY_FRIENDLY_NAME)) {
                    getXpp().next();
                    deviceInformation.setFriendlyName(getXpp().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXpp().getName().equals(KEY_MANUFACTURER)) {
                    getXpp().next();
                    deviceInformation.setManufacturer(getXpp().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXpp().getName().equals(KEY_MANUFACTURER_URL)) {
                    getXpp().next();
                    deviceInformation.setManufacturerURL(getXpp().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXpp().getName().equals(KEY_MODEL_DESCRIPTION)) {
                    getXpp().next();
                    deviceInformation.setModelDescription(getXpp().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXpp().getName().equals(KEY_MODEL_NAME)) {
                    getXpp().next();
                    deviceInformation.setModelName(getXpp().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXpp().getName().equals(KEY_MODEL_NUMBER)) {
                    getXpp().next();
                    deviceInformation.setModelNumber(getXpp().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXpp().getName().equals(KEY_MODEL_URL)) {
                    getXpp().next();
                    deviceInformation.setModelURL(getXpp().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXpp().getName().equals(KEY_SERIAL_NUMBER)) {
                    getXpp().next();
                    deviceInformation.setSerialNumber(getXpp().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXpp().getName().equals(KEY_UDN)) {
                    getXpp().next();
                    deviceInformation.setUDN(getXpp().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXpp().getName().equals(KEY_PRESENTATION_URL)) {
                    getXpp().next();
                    deviceInformation.setPresentationURL(getXpp().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXpp().getName().equals(KEY_URL_BASE)) {
                    getXpp().next();
                    deviceInformation.setUrlBase(getXpp().getText());
                }

                eventType = getXpp().next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return deviceInformation;
    }
}
