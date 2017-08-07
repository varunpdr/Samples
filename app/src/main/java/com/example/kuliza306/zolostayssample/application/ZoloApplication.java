package com.example.kuliza306.zolostayssample.application;

import android.app.Application;

import com.example.kuliza306.zolostayssample.database.DaoMaster;
import com.example.kuliza306.zolostayssample.database.DataBaseHelper;

/**
 * Created by kuliza306 on 07/08/17.
 */

public class ZoloApplication extends Application {

    private static IZoloComponent component;
    private static ZoloApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        component=DaggerIZoloComponent.builder().zoloModule(new ZoloModule(instance.getApplicationContext())).build();
    }

    public static IZoloComponent component() {
        return component;
    }
}
