package com.lp.work.interFace;

public interface MyHttpUpFileCallBack {
    void ok(String jsonString, String path, String httpTY);

    void error(String e, String path, String httpTY);

    void Progress(long e, String name);
}
