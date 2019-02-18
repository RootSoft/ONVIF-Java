package be.teletask.onvif;

/**
 * Created by Tomas Verhelst on 03/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class OnvifXMLBuilder {

    //Constants
    public static final String TAG = OnvifXMLBuilder.class.getSimpleName();

    //Attributes

    public static String getSoapHeader() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope " +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                "xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" >" +
                "<soap:Body>";
    }

    public static String getEnvelopeEnd() {
        return "</soap:Body></soap:Envelope>";
    }

    public static String getDiscoverySoapHeader() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope " +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                "xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" " +
                "xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2004/08/addressing\" " +
                "xmlns:tns=\"http://schemas.xmlsoap.org/ws/2005/04/discovery\">" +
                "<soap:Header>" +
                "<wsa:Action>http://schemas.xmlsoap.org/ws/2005/04/discovery/Probe</wsa:Action>" +
                "<wsa:MessageID>urn:uuid:%s</wsa:MessageID>\n" +
                "<wsa:To>urn:schemas-xmlsoap-org:ws:2005:04:discovery</wsa:To>\n" +
                "</soap:Header>" +
                "<soap:Body>";
    }

    public static String getDiscoverySoapBody() {
        return "<tns:Probe/>";
    }

}
