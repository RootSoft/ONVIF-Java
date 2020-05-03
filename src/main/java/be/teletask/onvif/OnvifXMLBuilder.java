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

    public static String getDiscoverySoapHeader(String uuid) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:a=\"http://schemas.xmlsoap.org/ws/2004/08/addressing\">\n" +
                "  <soap:Header>\n" +
                "    <a:Action soap:mustUnderstand=\"1\">http://schemas.xmlsoap.org/ws/2005/04/discovery/Probe</a:Action>\n" +
                "    <a:MessageID>uuid:" + uuid + "</a:MessageID>\n" +
                "    <a:ReplyTo>\n" +
                "      <a:Address>http://schemas.xmlsoap.org/ws/2004/08/addressing/role/anonymous</a:Address>\n" +
                "    </a:ReplyTo>\n" +
                "    <a:To soap:mustUnderstand=\"1\">urn:schemas-xmlsoap-org:ws:2005:04:discovery</a:To>\n" +
                "  </soap:Header>\n" +
                "  <soap:Body>\n";
    }

    public static String getDiscoverySoapBody(String type) {
        return "<Probe xmlns=\"http://schemas.xmlsoap.org/ws/2005/04/discovery\">" +
                "<d:Types xmlns:d=\"http://schemas.xmlsoap.org/ws/2005/04/discovery\" xmlns:dp0=\"http://www.onvif.org/ver10/network/wsdl\">dp0:" + type + "</d:Types>\n" +
                "</Probe>";
    }

}
