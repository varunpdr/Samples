package com.example.kuliza306.zolostayssample.application;

import android.app.Application;
import android.content.Context;

import com.example.kuliza306.zolostayssample.database.DaoMaster;
import com.example.kuliza306.zolostayssample.database.DaoSession;
import com.example.kuliza306.zolostayssample.database.DataBaseHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kuliza306 on 07/08/17.
 */

@Module
public class ZoloModule {

    Context context;


    public ZoloModule(Context context)
    {
        this.context=context;
    }


    @Provides
    protected DaoSession provideDaoSession() {
        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(context);
        DaoMaster daoMaster = new DaoMaster(dataBaseHelper.getWritableDatabase());
        return daoMaster.newSession();
    }

}
