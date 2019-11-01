package com.lp.work.utils.http;

public class ApiUtils {

    public static String DEV_API = "";//设备api地址

    public static String DEV_MAC;// 设备mac地址

//    public static final String VFUN = "http://osp-beta.vfunmedia.com/proxy";

    public static final String VFUN = ".vfunmedia.com/proxy";

    public static final String QT = ".91qutou.com/proxy";

    public static final String FORMAL = "https://osp.91qutou.com/proxy";

    public static final String LUSEE = ".lusee.cn/proxy";

    public static String REQUEST_URL = FORMAL;

    //登录
    public static final String login = "/api/appV2/sys/login";

    //登出
    public static final String logOut = "/api/appV2/sys/logOut";

    //获取版本
    public static final String getNewVersion = "/api/appV2/sys/getNewVersion";

    //绑定设备
    public static final String saveUserClient = "/api/appV2/sys/saveUserClient";

    //修改密码
    public static final String CHANG_PASSWORD = "/api/appV2/sys/updatePwd";

    //gps轨迹
    public static final String marker = "/api/appV2/gps/marker";

    //上传文件
    public static final String FileUpload = "/api/appV2/FileUpload/file/";

    //点位信息
    public static final String getXqBuildPoints = "/api/appV2/pointInfo/getXqBuildPoints";

    //修改记录
    public static final String pointLog = "/api/appV2/pointInfo/pointLog";

    //刊播记录
    public static final String getKBList = "/api/appV2/pointInfo/getKBList";

    //获取当前运营商当前公益广告列表
    public static final String getPubAdVideoOrAudio = "/api/appV2/pointInfo/getPubAdVideoOrAudio";

    //点位广告
    public static final String getPointAd = "/api/appV2/pointInfo/getPointAd";

    //运维记录
    public static final String byDateGetBusiLogList = "/api/appV2/pointInfo/byDateGetBusiLogList";

    //点位信息修改
    public static final String updatePoint = "/api/appV2/pointInfo/updatePoint";

    //巡视项目列表
    public static final String getCheckList = "/api/appV2/patrol/getCheckList";

    //任务统计数据
    public static final String getEqBusiReport = "/api/appV2/pointInfo/getEqBusiReport";

    //获取点位上下刊监播任务
    public static final String listJob = "/api/appV2/publish/listJob";

    //安装人员
    public static final String getValidUser = "/api/appV2/sys/getValidUser";

    //点位激活
    public static final String activetionPoint = "/api/appV2/eq/activetionPoint";

    //运维业务 (点位新增、设备换机、设备拆机、运维任务验收)
    public static final String uploadBusiJob = "/api/appV2/eq/uploadBusiJob";

    //点位停机 恢复
    public static final String uploadStopJob = "/api/appV2/eq/uploadStopJob";

    //下刊恢复
    public static final String advertisingRestore = "/api/appV2/pointInfo/advertisingRestore";

    //巡视任务提交
    public static final String uploadCheckJob = "/api/appV2/patrol/uploadCheckJob";

    //消息获取
    public static final String getAppMsg = "/api/appV2/sys/getAppMsg/";

    //消息读取
    public static final String readAppMsg = "/api/appV2/sys/readAppMsg";

    //点位验收信息
    public static final String getPointAcceptingInfo = "/api/appV2/pointInfo/getPointAcceptingInfo/";

    //上传点位上下刊监播任务
    public static final String uploadJob = "/api/appV2/publish/uploadJob";

    //app音频上刊回调 告诉后台记录
    public static final String audioCallback = "/api/appV2/pointInfo/audioCallback";


}
