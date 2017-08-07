package com.example.kuliza306.zolostayssample.database;

import android.content.Context;

import com.example.kuliza306.zolostayssample.application.ZoloApplication;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by kuliza306 on 05/08/17.
 */

public class DataProviderManager {

    @Inject
    DaoSession sDaoSession;

    public DataProviderManager() {
        ZoloApplication.component().inject(this);
    }

    public UserInfoData getUserLoginInfo(Context ctx, String phoneNumber, String password) {
        List<UserInfoData> userInfoData = sDaoSession.getUserInfoDataDao().loadAll();
        for (int i = 0; i < userInfoData.size(); i++) {
            if (userInfoData.get(i).getPhoneNumber().equalsIgnoreCase(phoneNumber) && userInfoData.get(i).getPassword().equalsIgnoreCase(password)) {
                return userInfoData.get(i);
            }
        }

        return null;

    }

    public UserInfoData getUserEmailInfo(Context ctx, String email) {

        List<UserInfoData> userInfoData = sDaoSession.getUserInfoDataDao().loadAll();
        for (int i = 0; i < userInfoData.size(); i++) {
            if (userInfoData.get(i).getEmailId().equalsIgnoreCase(email.toLowerCase())) {
                return userInfoData.get(i);
            }
        }

        return null;

    }

    public UserInfoData getUserInfo(Context ctx, String phoneNumber, String emailId) {

        List<UserInfoData> userInfoData = sDaoSession.getUserInfoDataDao().loadAll();
        for (int i = 0; i < userInfoData.size(); i++) {
            if (userInfoData.get(i).getPhoneNumber().equalsIgnoreCase(phoneNumber) || userInfoData.get(i).getEmailId().equalsIgnoreCase(emailId)) {
                return userInfoData.get(i);
            }
        }

        return null;

    }

    public void insertUserInfo(Context ctx, UserInfoData userInfoData) {
        sDaoSession.getUserInfoDataDao().insertOrReplace(userInfoData);
    }

    public void replaceUserInfo(Context mContext, UserInfoData userInfoData) {
        sDaoSession.getUserInfoDataDao().insertOrReplace(userInfoData);


    }
}
