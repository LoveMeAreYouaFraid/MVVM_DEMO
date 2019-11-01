package com.lp.work.utils.http;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.lp.work.R;
import com.lp.work.interFace.MyHttpCallBack;
import com.lp.work.interFace.MyHttpCallBackInDownLoad;
import com.lp.work.interFace.MyHttpUpFileCallBack;
import com.lp.work.mode.KEY;
import com.lp.work.utils.FileUtils;
import com.lp.work.utils.LogUtils;
import com.lp.work.utils.MyApp;
import com.lp.work.utils.SpUtsil;
import com.lp.work.utils.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.lp.work.utils.http.HttpRequestMode.DOWN;
import static com.lp.work.utils.http.HttpRequestMode.GET;
import static com.lp.work.utils.http.HttpRequestMode.POST;
import static com.lp.work.utils.http.HttpRequestMode.PUT;
import static com.lp.work.utils.http.HttpRequestMode.UP_FILE;


/**
 * Created by 10237 on 2016/11/11.
 * <p>
 * 网络请求
 */

public class HttpUtil {

    private OkHttpClient okHttpClient;
    private Request request;
    private String ApiUri;
    private final int ok = 1, error = 2;
    private String[] key, value;
    private String StringJson = MyApp.getInstance().getResources().getString(R.string.qqsb);
    private String errorString;
    private MyHttpCallBack callBack;
    private MyHttpUpFileCallBack myHttpUpFileCallBack;
    private MyHttpCallBackInDownLoad callBackInDownLoad;
    private long time;
    private Call call;
    private File file;
    private long UserPercentage = 0;
    private String httpType;
    private boolean isPubParam = true;
    private String requestJsonString;
    private String fileSuffix;//文件后缀名
    private int outTime = 3;

    public HttpUtil() {
        newHttpUtil(20);
    }

    public HttpUtil(int s) {
        newHttpUtil(s);
    }


    private void newHttpUtil(int s) {
        outTime = s;
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1000 * outTime, TimeUnit.SECONDS)//连接超时
                .readTimeout(1000 * outTime, TimeUnit.SECONDS) //读取超时
                .writeTimeout(1000 * outTime, TimeUnit.SECONDS) //写超时
                .sslSocketFactory(createSSLSocketFactory(), new X509TrustManagers())
                .hostnameVerifier(new HostnameVerifiers())
                .build();
        time = System.currentTimeMillis();
    }

    public OkHttpClient get() {
        return okHttpClient;
    }


    public HttpUtil SetApiUrl(String uri) {
        ApiUri = SpUtsil.getString("api", "") + uri;
        httpType = uri;
        return this;
    }

    /**
     * 传外部api
     *
     * @param uri    服务器前缀
     * @param apiUri api名称
     * @return this
     */
    public HttpUtil SetApiUrl(String uri, String apiUri) {
        httpType = apiUri;
        ApiUri = uri + apiUri;
        return this;
    }

    public HttpUtil isPubParam(boolean isPubParams) {
        isPubParam = isPubParams;
        return this;
    }

    public HttpUtil SetKey(String... key) {
        this.key = key;
        return this;
    }

    public HttpUtil SetJson(String json) {
        this.requestJsonString = json;
        return this;
    }

    public HttpUtil SetValue(String... value) {
        this.value = value;
        return this;
    }


    public void GET(MyHttpCallBack callBack) {
        this.callBack = callBack;

        try {
            readyParameter(GET);
        } catch (Exception e) {
            ToastUtils.show(e.getMessage());
        }

    }

    public void DOWN(MyHttpCallBackInDownLoad callBackInDownLoad, String fileSuffix) {
        this.callBackInDownLoad = callBackInDownLoad;
        this.fileSuffix = fileSuffix;

        try {
            readyParameter(DOWN);
        } catch (Exception e) {
            ToastUtils.show(e.getMessage());
        }
    }


    public HttpUtil POST(MyHttpCallBack callBack) {
        this.callBack = callBack;

        try {
            readyParameter(POST);
        } catch (Exception e) {
            ToastUtils.show(e.getMessage());
        }
        return this;
    }

    public HttpUtil UpFile(MyHttpUpFileCallBack callBack) {
        this.myHttpUpFileCallBack = callBack;

        try {
            readyParameter(UP_FILE);
        } catch (Exception e) {
            ToastUtils.show(e.getMessage());
        }
        return this;
    }


    public HttpUtil PUT(MyHttpCallBack callBack) {
        this.callBack = callBack;
        readyParameter(PUT);
        return this;
    }

    public HttpUtil setFile(File file) {
        this.file = file;
        return this;
    }

    /**
     * 关闭请求
     *
     * @return
     */
    public HttpUtil cancleAll() {
        try {
            call.cancel();
        } catch (Exception ignored) {
        }

        return this;
    }

    /**
     * 请求主体
     */
    private void Request(final int type) {

        if (!NetWorkUtils.isNetworkConnected()) {
            StringJson = MyApp.getInstance().getString(R.string.wlwlj);
            errorString = MyApp.getInstance().getString(R.string.wlwlj);
            if (callBack != null) {
                handler.sendEmptyMessage(error);
            } else if (myHttpUpFileCallBack != null) {
                handler.sendEmptyMessage(4);
            } else if (callBackInDownLoad != null) {
                handler.sendEmptyMessage(6);
            }

            return;
        }

        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                LogUtils.e(ApiUri + "耗时=" + (System.currentTimeMillis() - time) + "");
                LogUtils.e(ApiUri + "onFailure" + e.getMessage());
                if (callBack != null) {
                    handler.sendEmptyMessage(error);
                } else if (myHttpUpFileCallBack != null) {
                    handler.sendEmptyMessage(4);
                } else if (callBackInDownLoad != null) {
                    handler.sendEmptyMessage(6);
                }


            }

            @Override
            public void onResponse(Call call, Response response) {

                LogUtils.e("耗时=" + (System.currentTimeMillis() - time) + "");

//                try {
                switch (type) {
                    case DOWN:
                        File fileapk = FileUtils.newFile(fileSuffix);
                        InputStream is = null;
                        byte[] buf = new byte[2048];
                        int len;
                        FileOutputStream fos = null;
                        try {
                            long total = response.body().contentLength();
                            long current = 0;
                            long old = 0;
                            is = response.body().byteStream();
                            fos = new FileOutputStream(fileapk);
                            int Percentile = (int) (total / 100);
                            while ((len = is.read(buf)) != -1) {
                                current += len;
                                fos.write(buf, 0, len);
                                if (Percentile != 0) {
                                    UserPercentage = (current / Percentile);
                                }


                                if (old != UserPercentage) {

                                    LogUtils.e(UserPercentage + "");

                                    if (UserPercentage != 100) {
                                        callBackInDownLoad.downloadUpProgress(UserPercentage, httpType);
                                    }

                                    old = UserPercentage;
                                }

                            }
                            StringJson = fileapk.getPath();
                            handler.sendEmptyMessage(5);
                            LogUtils.e(StringJson + "");
                            fos.close();
                            is.close();
                        } catch (IOException e) {
                            LogUtils.e(e.getMessage());
                        } finally {
                            try {
                                if (is != null) {
                                    is.close();
                                }
                                if (fos != null) {
                                    fos.close();
                                }
                            } catch (IOException e) {
                                LogUtils.e(e.getMessage());
                                e.fillInStackTrace();
                            }
                        }
                        break;
                    case POST:
                    case PUT:
                    case GET:
                        try {

                            StringJson = response.body().string();

                            LogUtils.e(ApiUri + "返回的数据=" + StringJson);

                            try {
//                                PublicMode mode = new Gson().fromJson(StringJson, PublicMode.class);
//
//                                if (mode.getResultCode().equals("1")) {// ok
//
//                                    LogUtils.e("222");
//                                    handler.sendEmptyMessage(ok);
//
//                                }
//                                else if (mode.getResultCode().equals("-1")
//                                        && mode.getResultMsg().equals("token失效")) {
//
//
//                                    Intent intent = new Intent(MyApp.getInstance(), LoginActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    MyApp.getInstance().startActivity(intent);
//
//                                }
//                                else {
//                                    errorString = MyApp.getInstance().getResources().getString(R.string.qqsb);
//
//                                    LogUtils.e("zzz");
//                                    handler.sendEmptyMessage(error);
//                                }
                            } catch (Exception e) {

                                LogUtils.e("aaa");
                                if (StringJson.contains("502")) {

                                    errorString = "The server 502";

                                } else if (StringJson.contains("500")) {

                                    errorString = "The server 500";

                                } else if (StringJson.contains("404")) {

                                    errorString = "The server 404";

                                } else {

                                    errorString = MyApp.getInstance().getResources().getString(R.string.qqsb);
                                }

                                handler.sendEmptyMessage(error);
                            }

                        } catch (Exception e) {
                            LogUtils.e("qqq");
                            errorString = MyApp.getInstance().getResources().getString(R.string.qqsb);
                            handler.sendEmptyMessage(error);
                        }


                        LogUtils.e("________________");
                        LogUtils.e("errorString" + errorString);
                        break;
                    case UP_FILE:
                        try {
                            StringJson = response.body().string();
                            LogUtils.e(ApiUri + "返回的数据=" + StringJson);
//                            PublicMode upFulemodes = new Gson().fromJson(StringJson, PublicMode.class);

//                        try {
//                            if (upFulemodes.getResultCode().equals("1")) {// ok
//                                handler.sendEmptyMessage(3);
//                            } else {// error
//                                handler.sendEmptyMessage(4);
//                            }
                        } catch (Exception e) {
                            handler.sendEmptyMessage(4);
                            e.printStackTrace();
                        }

//                        } catch (Exception e) {
                        //
//                            LogUtils.e(e.getMessage());
//                            myHttpUpFileCallBack.error(e.getMessage(), file.getAbsolutePath(), httpType);
//                        }

                        break;
                }

//                } catch (Exception e) {
//                    StringJson = errorString;
//                    LogUtils.e(e.getMessage());
////                    handler.sendEmptyMessage(error);
//
//                    if (callBack == null) {
//                        myHttpUpFileCallBack.error(StringJson, file.getAbsolutePath(), httpType);
//                    } else {
//                        callBack.error(StringJson, httpType);
//                    }
//
//                }

            }
        });
    }

    /**
     * GET 方式 拼接数据
     *
     * @param key
     * @param value
     * @return
     */
    private String ParamString(String[] key, String[] value) {
        StringBuilder mParamString = new StringBuilder();
        if (key == null || key.length == 0) {
            return mParamString.toString();
        } else {
            for (int i = 0; i < key.length; i++) {
                if (!TextUtils.isEmpty(key[i])) {
                    if (value[i] == null) {
                        value[i] = "";
                    }
                    if (i == 0) {
                        mParamString.append("?").append(key[i]).append("=").append(value[i]);
                    } else {
                        mParamString.append("&").append(key[i]).append("=").append(value[i]);
                    }

                }

            }
        }
        return mParamString.toString();

    }


    /**
     * POST/PUT 数据 json方式
     *
     * @param key
     * @param value
     * @return
     */
    private RequestBody mRequestBody(String[] key, String[] value) {
        JsonObject json = new JsonObject();

        if (key != null) {
            for (int i = 0; i < key.length; i++) {
                if (!TextUtils.isEmpty(key[i])) {
                    if (TextUtils.isEmpty(value[i])) {
                        value[i] = "";

                    }
                    json.addProperty(key[i], value[i]);
                }
            }
        }


        String string = json.toString();
        if (!TextUtils.isEmpty(requestJsonString)) {
            string = requestJsonString;
        }
        LogUtils.e(string);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8")
                , string);

    }

    /**
     * POST/PUT 数据 表单方式
     *
     * @param key
     * @param value
     * @return
     */
    private RequestBody mRequestBodyData(String[] key, String[] value) {
        FormBody.Builder builder = new FormBody.Builder();
        if (key != null) {
            for (int i = 0; i < key.length; i++) {
                if (!TextUtils.isEmpty(key[i])) {
                    if (TextUtils.isEmpty(value[i])) {
                        value[i] = "";
                    }
                    builder.add(key[i], value[i]);
                }
            }
        }

        return builder.build();

    }

    private void readyParameter(int type) {
        /**
         * online 准备Request 数据
         *
         * @param type
         */
        Request.Builder builder = new Request.Builder();
        if (isPubParam) {
            LogUtils.e("addHeader:TOKEN=" + SpUtsil.getString(KEY.TOKEN, ""));
            LogUtils.e("addHeader:LANGUAGE=" + SpUtsil.getString(KEY.LANGUAGE, "zh"));

            builder.addHeader("token", SpUtsil.getString(KEY.TOKEN, ""))
                    .addHeader("language", SpUtsil.getString(KEY.LANGUAGE, "zh"));


        }

        switch (type) {
            case GET:
                builder.url(ApiUri + ParamString(key, value));
                break;
            case POST:
                builder.url(ApiUri)
                        .post(mRequestBody(key, value));
                break;
            case PUT:
                builder.url(ApiUri)
                        .put(mRequestBody(key, value));
                break;
            case DOWN:

                builder = new Request.Builder();

                builder.url(ApiUri);
                break;
            case UP_FILE:

                MultipartBody.Builder upFileBody = new MultipartBody.Builder();
                upFileBody.setType(MultipartBody.FORM);

                upFileBody.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), file));

                upFileBody.addFormDataPart("userId", SpUtsil.getString(KEY.USER_ID, ""));

                MultipartBody multipartBody = upFileBody.build();

                builder.url(ApiUri)
                        .post(multipartBody);

                break;
        }
        request = builder.build();
        Request(type);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case ok:
                    callBack.ok(StringJson, httpType);
                    break;
                case error:
                    callBack.error(errorString, httpType);
                    break;

                case 3:

                    myHttpUpFileCallBack.ok(StringJson, file.getAbsolutePath(), httpType);
                    break;
                case 4:

                    myHttpUpFileCallBack.error(StringJson, file.getAbsolutePath(), httpType);
                    break;
                case 5:
                    callBackInDownLoad.ok(StringJson, httpType);
                    break;
                case 6:
                    callBackInDownLoad.error(StringJson, httpType);
                    break;
            }
            return false;
        }
    });

    /**
     * https 配置
     *
     * @return
     */
    private SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception ignored) {
        }

        return ssfFactory;
    }

    /**
     * https 配置
     *
     * @return
     */
    private class X509TrustManagers implements X509TrustManager {

        @SuppressLint("TrustAllX509TrustManager")
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {

        }

        @SuppressLint("TrustAllX509TrustManager")
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    /**
     * https 配置
     *
     * @return
     */
    private class HostnameVerifiers implements HostnameVerifier {
        @SuppressLint("BadHostnameVerifier")
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

}