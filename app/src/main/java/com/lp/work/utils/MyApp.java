package com.lp.work.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.multidex.MultiDexApplication;

public class MyApp extends MultiDexApplication {
    private static MyApp myApp;

    public static synchronized MyApp getInstance() {
        return myApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
    }

    /**
     * 获取设备信息
     *
     * @return PackageInfo
     */
    public static PackageInfo getPackageInfo() {
        PackageInfo pi = null;
        try {
            PackageManager pm;
            pm = MyApp.getInstance().getPackageManager();
            pi = pm.getPackageInfo(MyApp.getInstance().getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

}
