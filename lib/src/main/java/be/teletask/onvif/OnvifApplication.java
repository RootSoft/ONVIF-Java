package be.teletask.onvif;

import be.teletask.onvif.listeners.DiscoveryListener;
import be.teletask.onvif.models.Device;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

public class OnvifApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DiscoveryManager manager = new DiscoveryManager();
        manager.discover(new DiscoveryListener() {
            @Override
            public void onDiscoveryStarted() {
                System.out.println("Discovery started");
            }

            @Override
            public void onDevicesFound(List<Device> devices) {
                for (Device device : devices)
                    System.out.println("Devices found: " + device.getHostName());
            }

        });
    }
}
