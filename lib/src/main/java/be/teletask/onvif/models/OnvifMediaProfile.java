package be.teletask.onvif.models;

/**
 * Created by Tomas Verhelst on 03/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class OnvifMediaProfile {

    //Constants
    public static final String TAG = OnvifMediaProfile.class.getSimpleName();

    //Attributes
    private final String name;
    private final String token;

    //Constructors

    public OnvifMediaProfile(String name, String token) {
        this.name = name;
        this.token = token;
    }

    //Properties

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "OnvifMediaProfile{" +
                "name='" + name + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
