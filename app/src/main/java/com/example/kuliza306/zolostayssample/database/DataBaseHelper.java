package com.example.kuliza306.zolostayssample.database;

/**
 * Created by kuliza306 on 05/08/17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static DataBaseHelper instance;
    private static Context mContext;

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, DaoMaster.SCHEMA_VERSION);
    }

    public static DataBaseHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DataBaseHelper.class) {
                if (instance == null) {
                    instance = new DataBaseHelper(context, "turvo", null);
                    mContext = context;
                }
            }
        }

        return instance;
    }

    public static void resetDatabase(SQLiteDatabase db) {
        DaoMaster.dropAllTables(db, true);
        DaoMaster.createAllTables(db, true);
    }

    public static void resetLocationTable(SQLiteDatabase db) {
        UserInfoDataDao.dropTable(db, true);
        UserInfoDataDao.createTable(db, true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DaoMaster.createAllTables(db, false);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DaoMaster.dropAllTables(db, true);
        DaoMaster.createAllTables(db, false);
    }

}

