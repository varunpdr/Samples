package com.example.kuliza306.zolostayssample.database;

import android.content.Context;

import java.util.List;

/**
 * Created by kuliza306 on 05/08/17.
 */

public class DataProviderManager {

    private static DaoSession sDaoSession;
    public static UserInfoData getUserInfo(Context ctx,String phoneNumber) {

        List<UserInfoData> userInfoData= getsDaoSession(ctx).getUserInfoDataDao().loadAll();
        for(int i=0;i<userInfoData.size();i++)
        {
            if(userInfoData.get(i).getPhoneNumber().equalsIgnoreCase(phoneNumber))
            {
                return userInfoData.get(i);
            }
        }

        return null;

    }

    public static void insertUserInfo(Context ctx,UserInfoData userInfoData)
    {
        getsDaoSession(ctx).getUserInfoDataDao().insertOrReplace(userInfoData);
    }

    private static DaoSession getsDaoSession(Context c) {
                if (sDaoSession == null) {
                    DaoMaster daoMaster = new DaoMaster(
                            DataBaseHelper.getInstance(c).getWritableDatabase()
                    );
                    sDaoSession = daoMaster.newSession();
                }
        return sDaoSession;
    }
}
