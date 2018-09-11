package be.teletask.onvif;

import be.teletask.onvif.listeners.DiscoveryListener;


import java.net.InetAddress;
import java.util.List;

/**
 * Created by Tomas Verhelst on 06/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class DiscoveryManager {

    //Constants
    public static final String TAG = DiscoveryManager.class.getSimpleName();

    //Attributes
    private OnvifDiscovery discovery;

    //Constructors

    public DiscoveryManager() {
        discovery = new OnvifDiscovery();
    }

    //Methods

    /**
     * Discovers a list of ONVIF-compatible device on the LAN.
     * <p>
     * An ONVIF device in discoverable mode sends multicast Hello messages once connected to the
     * network or sends its Status changes according to [WS-Discovery]. In addition it always listens
     * for Probe and Resolve messages and sends responses accordingly.
     * A device in nondiscoverable will not listen to [WS-Discovery] messages or send such messages.
     *
     * @param discoveryListener
     */
    public void discover(DiscoveryListener discoveryListener) {
        discover(DiscoveryMode.ONVIF, discoveryListener);
    }

    /**
     * Discovers a list of network devices on the LAN using the specified discovery mode.
     * <p>
     * The discovery protocol used is Simple Service Discovery Protocol (SSDP).
     * When a device is added to the network, SSDP allows that device to advertise its services
     * to control points on the network. This is achieved by sending SSDP alive messages.
     * <p>
     * When a control point is added to the network, SSDP allows that control point to actively
     * search for devices of interest on the network or listen passively to the SSDP alive messages of device.
     *
     * @param discoveryListener
     */
    public void discover(DiscoveryMode mode, DiscoveryListener discoveryListener) {
        discovery.probe(mode, discoveryListener);
    }

    /**
     * Gets a list of interface addresses on this device.
     *
     * @return
     */
    public List<InetAddress> getInterfaceAddresses() {
        return discovery.getInterfaceAddresses();
    }

    /**
     * Gets a list of all broadcast addresses on this device.
     *
     * @return
     */
    public List<InetAddress> getBroadcastAddresses() {
        return discovery.getBroadcastAddresses();
    }

    /**
     * Gets the local IP address of this device.
     *
     * @return
     */
    public String getLocalIpAddress() {
        return discovery.getLocalIpAddress();
    }

    //Properties

    /**
     * Gets the timeout (in milliseconds) to discover devices.
     * Defaults to 10 seconds.
     *
     * @return the timeout in milliseconds.
     */
    public int getDiscoveryTimeout() {
        return discovery.getDiscoveryTimeout();
    }

    /**
     * Sets the timeout in milliseconds to discover devices.
     * Defaults to 10 seconds
     *
     * @param timeoutMs the timeout in milliseconds
     */
    public void setDiscoveryTimeout(int timeoutMs) {
        discovery.setDiscoveryTimeout(timeoutMs);
    }

    /**
     * Gets the discovery mode.
     * Defaults to ONVIF
     *
     * @return the discovery mode
     */
    public DiscoveryMode getDiscoveryMode() {
        return discovery.getDiscoveryMode();
    }

    /**
     * Sets the discovery mode.
     */
    public void setDiscoveryMode(DiscoveryMode mode) {
        discovery.setDiscoveryMode(mode);
    }

}
