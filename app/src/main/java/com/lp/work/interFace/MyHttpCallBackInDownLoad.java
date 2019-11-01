package com.lp.work.interFace;

/**
 * Created by 10237 on 2016/11/11.
 */

public interface MyHttpCallBackInDownLoad {
    void ok(String jsonString, String httpTY);

    void error(String e, String httpTY);

    void downloadUpProgress(long Percentile, String httpTY);

}
