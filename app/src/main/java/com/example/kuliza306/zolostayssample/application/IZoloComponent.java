package com.example.kuliza306.zolostayssample.application;

import com.example.kuliza306.zolostayssample.database.DaoSession;
import com.example.kuliza306.zolostayssample.database.DataProviderManager;
import com.example.kuliza306.zolostayssample.loginscreen.LoginActivity;

import dagger.Component;
import dagger.Module;

/**
 * Created by kuliza306 on 07/08/17.
 */

@Component(modules = {ZoloModule.class})
public interface IZoloComponent {

    void inject(LoginActivity loginActivity);

    void inject(DataProviderManager dataProviderManager);
}
