package com.test.app;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import be.teletask.onvif.DiscoveryManager;
import be.teletask.onvif.DiscoveryMode;
import be.teletask.onvif.listeners.DiscoveryListener;
import be.teletask.onvif.models.Device;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    TextView resultsText;
    WifiManager.MulticastLock lock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.discoverButton).setOnClickListener(v -> {
            startDiscovery();
        });

        resultsText = findViewById(R.id.resultsText);
    }

    private void startDiscovery() {
        resultsText.setText("");
        lockMulticast();
        DiscoveryManager discoveryManager = new DiscoveryManager();
        discoveryManager.setDiscoveryTimeout(10000);
        discoveryManager.discover(DiscoveryMode.ONVIF, new DiscoveryListener() {
            @Override
            public void onDiscoveryStarted() {
                Log.d(TAG, "discovery started");
                updateText("discovery started");
            }

            @Override
            public void onDevicesFound(List<Device> devices) {
                for (Device device : devices) {
                    Log.d(TAG, "device found: " + device.getHostName());
                    updateText("device found: " + device.getHostName());
                }
            }

            @Override
            public void onDiscoveryFinished() {
                Log.d(TAG, "discovery finished");
                updateText("discovery finished");
                unlockMulticast();
            }
        });
    }

    private void updateText(String text) {
        new Handler(Looper.getMainLooper()).post(() -> {
            String currentText = resultsText.getText().toString();
            resultsText.setText(currentText + "\n" + text);
        });
    }

    private void lockMulticast() {
        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi == null) return;

        lock = wifi.createMulticastLock("ONVIF");
        lock.acquire();
    }

    private void unlockMulticast() {
        if (lock != null) {
            lock.release();
        }
    }
}