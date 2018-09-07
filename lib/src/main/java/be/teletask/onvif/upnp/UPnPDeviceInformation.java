package be.teletask.onvif.upnp;

/**
 * Created by Tomas Verhelst on 03/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class UPnPDeviceInformation {

    //Constants
    public static final String TAG = UPnPDeviceInformation.class.getSimpleName();

    //Attributes

    //Constructors
    private String deviceType;
    private String friendlyName;
    private String serialNumber;
    private String modelDescription;
    private String modelName;
    private String modelNumber;
    private String modelURL;
    private String manufacturer;
    private String manufacturerURL;
    private String UDN;
    private String presentationURL;
    private String urlBase;

    //Constructors
    public UPnPDeviceInformation() {
    }

    //Properties

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getPresentationURL() {
        return presentationURL;
    }

    public void setPresentationURL(String presentationURL) {
        this.presentationURL = presentationURL;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getModelURL() {
        return modelURL;
    }

    public void setModelURL(String modelURL) {
        this.modelURL = modelURL;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufacturerURL() {
        return manufacturerURL;
    }

    public void setManufacturerURL(String manufacturerURL) {
        this.manufacturerURL = manufacturerURL;
    }

    public String getUDN() {
        return UDN;
    }

    public void setUDN(String UDN) {
        this.UDN = UDN;
    }

    public String getUrlBase() {
        return urlBase;
    }

    public void setUrlBase(String urlBase) {
        this.urlBase = urlBase;
    }

    @Override
    public String toString() {
        return "UPnPDeviceInformation{" +
                "deviceType='" + deviceType + '\'' +
                ", friendlyName='" + friendlyName + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", modelDescription='" + modelDescription + '\'' +
                ", modelName='" + modelName + '\'' +
                ", modelNumber='" + modelNumber + '\'' +
                ", modelURL='" + modelURL + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", manufacturerURL='" + manufacturerURL + '\'' +
                ", UDN='" + UDN + '\'' +
                ", presentationURL='" + presentationURL + '\'' +
                ", urlBase='" + urlBase + '\'' +
                '}';
    }

}
