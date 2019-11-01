package com.lp.work.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import com.lp.work.BuildConfig;


/**
 * lipeng by 2019 05:25
 */
public class LogUtils {


    @SuppressLint("DefaultLocale")
    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        String customTagPrefix = "";
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag + "******";
    }


    public static void e(String content) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        if (!TextUtils.isEmpty(content)) {
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);
            if (content.length() > 4000) {
                for (int i = 0; i < content.length(); i += 4000) {
                    if (i + 4000 < content.length()) {
                        Log.e(tag + i, content.substring(i, i + 4000));
                    } else {
                        Log.e(tag + i, content.substring(i));
                    }
                }
            } else {
                Log.e(tag, content);
            }
        }
    }


    private static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }
}