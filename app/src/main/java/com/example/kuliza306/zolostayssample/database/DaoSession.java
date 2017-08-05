package com.example.kuliza306.zolostayssample.database;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.example.kuliza306.zolostayssample.database.UserInfoData;

import com.example.kuliza306.zolostayssample.database.UserInfoDataDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userInfoDataDaoConfig;

    private final UserInfoDataDao userInfoDataDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userInfoDataDaoConfig = daoConfigMap.get(UserInfoDataDao.class).clone();
        userInfoDataDaoConfig.initIdentityScope(type);

        userInfoDataDao = new UserInfoDataDao(userInfoDataDaoConfig, this);

        registerDao(UserInfoData.class, userInfoDataDao);
    }
    
    public void clear() {
        userInfoDataDaoConfig.getIdentityScope().clear();
    }

    public UserInfoDataDao getUserInfoDataDao() {
        return userInfoDataDao;
    }

}