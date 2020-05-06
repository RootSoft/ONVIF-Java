package be.teletask.onvif;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tomas Verhelst on 03/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class OnvifUtils {

    /**
     * Util method to retrieve a path from an URL (without IP address and port)
     *
     * @param uri example input: `http://192.168.1.0:8791/cam/realmonitor?audio=1`
     * @example:
     * @result example output: `cam/realmonitor?audio=1`
     */
    public static String getPathFromURL(String uri) {
        URL url;
        try {
            url = new URL(uri);
        } catch (MalformedURLException e) {
            return "";
        }

        String result = url.getPath();

        if (url.getQuery() != null) {
            result += url.getQuery();
        }

        return result;
    }

    /**
     * Util method for parsing. Retrieve the XAddr from the XmlPullParser given.
     */
    public static String retrieveXAddr(XmlPullParser xpp) throws IOException, XmlPullParserException {
        String result = "";
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT || (eventType == XmlPullParser.END_TAG && xpp.getName().equals("Service"))) {

            if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("XAddr")) {
                xpp.next();
                result = xpp.getText();
                break;
            }
            eventType = xpp.next();
        }

        return result;
    }

    /**
     * Util method for parsing.
     * Retrieve the XAddrs from the XmlPullParser given.
     */
    public static UriAndScopes retrieveXAddrsAndScopes(XmlPullParser xpp) throws IOException, XmlPullParserException {
        String[] scopes = new String[]{};
        String uri = "";
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {

            if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("Scopes")) {
                xpp.next();
                scopes = xpp.getText().split("\\s+");
                uri = OnvifUtils.retrieveXaddr(xpp);
                break;
            }
            eventType = xpp.next();
        }

        return new UriAndScopes(uri, Arrays.asList(scopes));
    }

    private static String retrieveXaddr(XmlPullParser xpp) throws IOException, XmlPullParserException {
        String result = "";
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {

            if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("XAddrs")) {
                xpp.next();
                result = xpp.getText();
            }
            eventType = xpp.next();
        }

        return result;
    }

    public static class UriAndScopes {
        private String uri;
        private List<String> scopes;

        public UriAndScopes(String uri, List<String> scopes) {
            this.uri = uri;
            this.scopes = scopes;
        }

        public String getUri() {
            return uri;
        }

        public List<String> getScopes() {
            return scopes;
        }
    }
}

