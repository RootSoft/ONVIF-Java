package be.teletask.onvif.parsers;

import be.teletask.onvif.models.OnvifDeviceInformation;
import be.teletask.onvif.responses.OnvifResponse;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Tomas Verhelst on 04/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class GetDeviceInformationParser extends OnvifParser<OnvifDeviceInformation> {

    //Constants
    public static final String TAG = GetDeviceInformationParser.class.getSimpleName();
    private static final String KEY_MANUFACTURER = "Manufacturer";
    private static final String KEY_MODEL = "Model";
    private static final String KEY_FIRMWARE_VERSION = "FirmwareVersion";
    private static final String KEY_SERIAL_NUMBER = "SerialNumber";
    private static final String KEY_HARDWARE_ID = "HardwareId";

    @Override
    public OnvifDeviceInformation parse(OnvifResponse response) {
        OnvifDeviceInformation deviceInformation = new OnvifDeviceInformation();

        try {
            getXpp().setInput(new StringReader(response.getXml()));
            eventType = getXpp().getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG && getXpp().getName().equals(KEY_MANUFACTURER)) {
                    getXpp().next();
                    deviceInformation.setManufacturer(getXpp().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXpp().getName().equals(KEY_MODEL)) {
                    getXpp().next();
                    deviceInformation.setModel(getXpp().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXpp().getName().equals(KEY_FIRMWARE_VERSION)) {
                    getXpp().next();
                    deviceInformation.setFirmwareVersion(getXpp().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXpp().getName().equals(KEY_SERIAL_NUMBER)) {
                    getXpp().next();
                    deviceInformation.setSerialNumber(getXpp().getText());
                } else if (eventType == XmlPullParser.START_TAG && getXpp().getName().equals(KEY_HARDWARE_ID)) {
                    getXpp().next();
                    deviceInformation.setHardwareId(getXpp().getText());
                }
                eventType = getXpp().next();

            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return deviceInformation;
    }

}
