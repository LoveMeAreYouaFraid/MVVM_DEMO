package com.lp.work.interFace;

/**
 * Created by 10237 on 2016/11/11.
 */

public interface MyHttpCallBack {
    void ok(String jsonString, String httpTY);

    void error(String e, String httpTY);

}
