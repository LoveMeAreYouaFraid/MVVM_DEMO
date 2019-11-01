package com.lp.work.utils;

import android.widget.Toast;


public class ToastUtils {
    public static void show(Object info) {
        if (info == null) {
            return;
        }
        if (info.toString().length() == 0 || info.toString().length() > 100 || info.toString().contains("code")) {
            return;
        }
        try {
            Toast.makeText(MyApp.getInstance(), info + "", Toast.LENGTH_SHORT).show();
        } catch (Exception c) {
            LogUtils.e(c.getMessage());
        }

    }


}
