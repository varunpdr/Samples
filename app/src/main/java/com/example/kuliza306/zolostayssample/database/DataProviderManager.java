package com.example.kuliza306.zolostayssample.database;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by kuliza306 on 05/08/17.
 */

public class DataProviderManager {

    private static DaoSession sDaoSession;

    public static UserInfoData getUserLoginInfo(Context ctx, String phoneNumber, String password) {

        List<UserInfoData> userInfoData = getsDaoSession(ctx).getUserInfoDataDao().loadAll();
        for (int i = 0; i < userInfoData.size(); i++) {
            if (userInfoData.get(i).getPhoneNumber().equalsIgnoreCase(phoneNumber) && userInfoData.get(i).getPassword().equalsIgnoreCase(password)) {
                return userInfoData.get(i);
            }
        }

        return null;

    }

    public static UserInfoData getUserEmailInfo(Context ctx, String email) {

        List<UserInfoData> userInfoData = getsDaoSession(ctx).getUserInfoDataDao().loadAll();
        for (int i = 0; i < userInfoData.size(); i++) {
            if (userInfoData.get(i).getEmailId().equalsIgnoreCase(email.toLowerCase())) {
                return userInfoData.get(i);
            }
        }

        return null;

    }

    public static UserInfoData getUserInfo(Context ctx, String phoneNumber, String emailId) {

        List<UserInfoData> userInfoData = getsDaoSession(ctx).getUserInfoDataDao().loadAll();
        for (int i = 0; i < userInfoData.size(); i++) {
            if (userInfoData.get(i).getPhoneNumber().equalsIgnoreCase(phoneNumber) || userInfoData.get(i).getEmailId().equalsIgnoreCase(emailId)) {
                return userInfoData.get(i);
            }
        }

        return null;

    }

    public static void insertUserInfo(Context ctx, UserInfoData userInfoData) {
        getsDaoSession(ctx).getUserInfoDataDao().insertOrReplace(userInfoData);
    }

    private static DaoSession getsDaoSession(Context c) {
        if (sDaoSession == null) {
            DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(c);
            DaoMaster daoMaster = new DaoMaster(dataBaseHelper.getWritableDatabase());
            sDaoSession = daoMaster.newSession();
        }
        return sDaoSession;
    }

    public static void replaceUserInfo(Context mContext, UserInfoData userInfoData) {
       getsDaoSession(mContext).getUserInfoDataDao().insertOrReplace(userInfoData);


    }
}
