package be.teletask.onvif.parsers;

import be.teletask.onvif.models.OnvifMediaProfile;
import be.teletask.onvif.responses.OnvifResponse;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomas Verhelst on 04/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class GetMediaProfilesParser extends OnvifParser<List<OnvifMediaProfile>> {

    //Constants
    public static final String TAG = GetMediaProfilesParser.class.getSimpleName();
    private static final String KEY_PROFILES = "Profiles";
    private static final String ATTR_TOKEN = "token";
    private static final String ATTR_NAME = "Name";

    @Override
    public List<OnvifMediaProfile> parse(OnvifResponse response) {
        List<OnvifMediaProfile> profiles = new ArrayList<>();

        try {
            getXpp().setInput(new StringReader(response.getXml()));
            eventType = getXpp().getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG && getXpp().getName().equals(KEY_PROFILES)) {

                    String token = getXpp().getAttributeValue(null, ATTR_TOKEN);
                    getXpp().nextTag();
                    if (getXpp().getName().equals(ATTR_NAME)) {
                        getXpp().next();
                        String name = getXpp().getText();
                        profiles.add(new OnvifMediaProfile(name, token));
                    }
                }
                eventType = getXpp().next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return profiles;
    }

}
