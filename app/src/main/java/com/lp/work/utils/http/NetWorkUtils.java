package com.lp.work.utils.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.lp.work.utils.MyApp;


/**
 * 判断是否有网
 * Created by Administrator on 2017/1/11 0011.
 */

public class NetWorkUtils {

    public static boolean isNetworkConnected() {

        ConnectivityManager mConnectivityManager = (ConnectivityManager) MyApp.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert mConnectivityManager != null;
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null;

    }


}
