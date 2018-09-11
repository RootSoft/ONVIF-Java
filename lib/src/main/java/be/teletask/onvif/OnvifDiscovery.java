package be.teletask.onvif;

import be.teletask.onvif.listeners.DiscoveryCallback;
import be.teletask.onvif.listeners.DiscoveryListener;
import be.teletask.onvif.models.Device;
import be.teletask.onvif.models.DiscoveryPacket;
import be.teletask.onvif.models.OnvifPacket;


import java.io.IOException;
import java.net.*;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.*;

/**
 * The OnvifDiscovery class uses the Web Services Dynamic Discovery (WS-Discovery).
 * This is a technical specification that defines a multicast discovery protocol
 * to locate services on a local network.
 * <p>
 * It operates over TCP and UDP port 3702 and uses IP multicast address 239.255.255.250.
 * As the name suggests, the actual communication between nodes is done using web services standards, notably SOAP-over-UDP.
 * <p>
 * With WS-Discovery, the discovery tool puts SSDP queries on the network from its unicast address
 * to 239.255.255.250 multicast address, sending them to the well-known UDP port 3702.
 * The device receives the query, and answers to the discovery tool's unicast IP address from its unicast IP address.
 * The reply contains information about the Web Services (WS) available on the device, let alone the device's IP address itself.
 * <p>
 * UPnP works in a very similar way, but on a different UDP port (1900).
 * Compared to the WS-Discovery, the UPnP is intended for a general use (data sharing, communication, entertainment).
 * <p>
 * Created by Tomas Verhelst on 04/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class OnvifDiscovery {

    //Constants
    public static final String TAG = OnvifDiscovery.class.getSimpleName();
    private static final String MULTICAST_ADDRESS_IPV4 = "239.255.255.250"; // Simple Service Discovery Protocol
    private static final String MULTICAST_ADDRESS_IPV6 = "[FF02::C]";
    private static int DISCOVERY_TIMEOUT = 10000;

    private static final Random random = new SecureRandom();

    //Attributes
    private int discoveryTimeout = DISCOVERY_TIMEOUT;
    private DiscoveryMode mode;
    private DiscoveryListener discoveryListener;

    //Constructors
    OnvifDiscovery() {
        this(DiscoveryMode.ONVIF);
    }

    public OnvifDiscovery(DiscoveryMode mode) {
        this.mode = mode;
    }

    //Methods

    int getDiscoveryTimeout() {
        return discoveryTimeout;
    }

    /**
     * Sets the timeout in milliseconds to discover devices.
     * Defaults to 10 seconds
     *
     * @param timeoutMs the timeout in milliseconds
     */
    void setDiscoveryTimeout(int timeoutMs) {
        discoveryTimeout = timeoutMs;
    }

    DiscoveryMode getDiscoveryMode() {
        return mode;
    }

    void setDiscoveryMode(DiscoveryMode mode) {
        this.mode = mode;
    }

    public void setDiscoveryListener(DiscoveryListener discoveryListener) {
        this.discoveryListener = discoveryListener;
    }

    /**
     * Broadcasts a SOAP-over-UDP package to all network interfaces
     * Discoveries are sent over multicast, replies are sent over unicast (both in UDP)
     * It means that the device will receive the discovery query, but it will not be able to answer back to the discovery tool if the peers are working on different subnets.
     */
    void probe(DiscoveryMode mode, DiscoveryListener discoveryListener) {
        //Sets the mode and discovery callback
        this.mode = mode;
        this.discoveryListener = discoveryListener;

        //Get all interface addresses to send the UDP package on.
        List<InetAddress> addresses = getInterfaceAddresses();

        //Broadcast the message over all the network interfaces
        broadcast(addresses);
    }

    //Properties

    private void broadcast(List<InetAddress> addresses) {
        //Our list will be accessed by multiple threads, hence ConcurrentSkipListSet
        Collection<Device> devices = new ConcurrentSkipListSet<>();

        //Create a new cached thread pool and a monitor service
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService monitor = Executors.newSingleThreadExecutor();
        CountDownLatch latch = new CountDownLatch(addresses.size());
        List<Runnable> runnables = new ArrayList<>();

        //Add runnables to the list to be executed in order
        for (InetAddress address : addresses) {
            runnables.add(() -> {
                try {
                    OnvifPacket packet = createDiscoveryPacket();
                    byte[] data = packet.getData();

                    int port = random.nextInt(20000) + 40000;

                    DatagramSocket client = new DatagramSocket(port, address);
                    client.setBroadcast(true);

                    //Start a new thread to listen for incoming UDP packages
                    new DiscoveryThread(client, discoveryTimeout, mode, new DiscoveryCallback() {

                        @Override
                        public void onDiscoveryStarted() {
                            try {
                                if (address instanceof Inet4Address) {
                                    client.send(new DatagramPacket(data, data.length, InetAddress.getByName(MULTICAST_ADDRESS_IPV4), mode.port));
                                } else {
                                    client.send(new DatagramPacket(data, data.length, InetAddress.getByName(MULTICAST_ADDRESS_IPV6), mode.port));
                                }
                            } catch (IOException ignored) {
                            }
                        }

                        @Override
                        public void onDevicesFound(List<Device> onvifDevices) {
                            devices.addAll(onvifDevices);
                        }

                        @Override
                        public void onDiscoveryFinished() {
                            latch.countDown();
                        }

                    }).start();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        }

        //Notify that we started our discovery
        notifyDiscoveryStarted();

        //Execute a new thread for every probe that should be sent.
        monitor.submit(() -> {
            for (Runnable runnable : runnables) {
                executorService.submit(runnable);
            }

            //Stop accepting new tasks and shuts down threads as they finish
            try {
                executorService.shutdown();

                latch.await(discoveryTimeout, TimeUnit.MILLISECONDS);
                boolean cleanShutdown = executorService.awaitTermination(discoveryTimeout,
                        TimeUnit.MILLISECONDS);

                if (!cleanShutdown) {
                    executorService.shutdownNow();
                }

                notifyDevicesFound(new ArrayList<>(devices));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        monitor.shutdown();

    }

    private OnvifPacket createDiscoveryPacket() {
        String uuid = UUID.randomUUID().toString();
        return new DiscoveryPacket(uuid, mode);
    }

    List<InetAddress> getInterfaceAddresses() {
        List<InetAddress> addresses = new ArrayList<>();
        try {
            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();

                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue; // Don't want to broadcast to the loopback interface
                }

                for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
                    addresses.add(address.getAddress());
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return addresses;
    }

    List<InetAddress> getBroadcastAddresses() {
        List<InetAddress> addresses = new ArrayList<>();
        try {
            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();

                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue; // Don't want to broadcast to the loopback interface
                }

                for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
                    InetAddress broadcast = address.getBroadcast();
                    if (broadcast == null) {
                        continue;
                    }

                    addresses.add(broadcast);
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return addresses;
    }

    String getLocalIpAddress() {

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ignored) {

        }
        return null;
    }

    private void notifyDiscoveryStarted() {
        if (discoveryListener != null)
            discoveryListener.onDiscoveryStarted();
    }

    private void notifyDevicesFound(List<Device> devices) {
        if (discoveryListener != null)
            discoveryListener.onDevicesFound(devices);
    }

}
