package com.example.kuliza306.zolostayssample.database;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by kuliza306 on 05/08/17.
 */

public class ZoloDaoGenerator {

    public static void main(String args[]) throws Exception {

        Schema schema=new Schema(2,"com.example.kuliza306.zolostayssample.database");
        schema.enableKeepSectionsByDefault();

        Entity userInfo= schema.addEntity("UserInfoData");
        userInfo.setTableName("UserInfoData");
        userInfo.addLongProperty("id").primaryKey().autoincrement();
        userInfo.addStringProperty("phoneNumber");
        userInfo.addStringProperty("emailId");
        userInfo.addStringProperty("name");
        userInfo.addStringProperty("password");

        new DaoGenerator().generateAll(schema, "../zolostayssample/app/src/main/java/");

    }
}
