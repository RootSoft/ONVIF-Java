package be.teletask.onvif.parsers;

import be.teletask.onvif.responses.OnvifResponse;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Tomas Verhelst on 04/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class GetMediaStreamParser extends OnvifParser<String> {

    //Constants
    public static final String TAG = GetMediaStreamParser.class.getSimpleName();
    private static final String KEY_URI = "Uri";

    @Override
    public String parse(OnvifResponse response) {
        String uri = "";
        try {
            getXpp().setInput(new StringReader(response.getXml()));
            eventType = getXpp().getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG && getXpp().getName().equals(KEY_URI)) {

                    getXpp().next();
                    uri = getXpp().getText();
                    break;
                }
                eventType = getXpp().next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return uri;
    }

}
