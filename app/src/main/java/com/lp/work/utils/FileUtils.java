package com.lp.work.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;





/*
 * Created by Administrator on 2017/1/1.
 */

public class FileUtils {


    /**
     * @return new File
     */
    public static File newPngFile() {
        return new File(MyApp.getInstance().
                getExternalCacheDir(),
                System.currentTimeMillis() + ".png");
    }

    /**
     * @return new File
     */
    public static File newApkFile() {
        return new File(MyApp.getInstance().
                getExternalCacheDir(),
                System.currentTimeMillis() + ".apk");
    }

    /**
     * @return new File
     */
    public static File newFile(String name) {
        return new File(MyApp.getInstance().
                getExternalCacheDir() + "/" + System.currentTimeMillis() + "." + name);
    }

    /**
     * @return new File
     */
    public static File newErrorLog() {
        File R = new File(MyApp.getInstance().
                getExternalCacheDir() + "/ErrorLog/");

        if (!R.exists()) {

            R.mkdir();
        }
        return new File(MyApp.getInstance().
                getExternalCacheDir() + "/ErrorLog/" + System.currentTimeMillis() + ".log");
    }

    public static File saveBitmapFile(Bitmap bitmap, String newfiles) {

        File newfile = new File(newfiles);

        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newfile));
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, bos);
            } catch (NullPointerException e) {
                LogUtils.e("无法获取这张图片");
            }

            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newfile;
    }

    public static File saveBitmapFile(Bitmap bitmap) {

        File file = new File(FileUtils.getStoragePath() + "/" + System.currentTimeMillis() + ".png");//将要保存图片的路径

        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos);
            } catch (NullPointerException e) {
                LogUtils.e("无法获取这张图片");
            }

            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private static Bitmap getSmallBitmap(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, 0, 1080);//最大分辨率
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        File file = new File(filePath);
        LogUtils.e(file.getName());
        file.delete();

        return bitmap;
    }


    /**
     * Compress Picture Save to CacheCatalog
     * 压缩图片保存到缓存目录
     *
     * @return 压缩后的文件
     */
    public static File CPSCC(String bitmapFile) {
        return saveBitmapFile(getSmallBitmap(bitmapFile), bitmapFile);
    }


    /**
     * 计算压缩比例值(改进版 by touch_ping)
     * <p>
     * 原版2>4>8...倍压缩
     * 当前2>3>4...倍压缩
     *
     * @param options   解析图片的配置信息
     * @param reqWidth  所需图片压缩尺寸最小宽度O
     * @param reqHeight 所需图片压缩尺寸最小高度
     * @return
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    /**
     * 小数的格式化
     */
    public static final DecimalFormat FORMAT = new DecimalFormat("####.##");
    public static final DecimalFormat FORMAT_ONE = new DecimalFormat("####.#");

    /**
     * 根据文件路径获取文件名称
     *
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return "";
        }
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
    }


    /**
     * 根据传入的byte数量转换为对应的byte, Kbyte, Mbyte, Gbyte单位的字符串
     *
     * @param size byte数量
     * @return
     */
    public static String getFileSize(long size) {
        if (size < 0) { //小于0字节则返回0
            return "0B";
        }

        double value = 0f;
        if ((size / 1024) < 1) { //0 ` 1024 byte
            return size + "B";
        } else if ((size / (1024 * 1024)) < 1) {//0 ` 1024 kbyte

            value = size / 1024f;
            return FORMAT.format(value) + "KB";
        } else if (size / (1024 * 1024 * 1024) < 1) {                  //0 ` 1024 mbyte
            value = (size * 100 / (1024 * 1024)) / 100f;
            return FORMAT.format(value) + "MB";
        } else {                  //0 ` 1024 mbyte
            value = (size * 100l / (1024l * 1024l * 1024l)) / 100f;
            return FORMAT.format(value) + "GB";
        }
    }


    public static String getStoragePath() {
        String PATH;
        PATH = MyApp.getInstance().getExternalCacheDir().getAbsolutePath();
        return PATH;
    }

    public static File getStorageVideoPath() {

        File file = new File(MyApp.getInstance().getExternalCacheDir().getAbsolutePath() + "/video");
        if (!file.exists()) {

            file.mkdir();
        }
        return file;
    }

    public static File getStoragePublicVideoPath() {

        File file = new File(MyApp.getInstance().getExternalCacheDir().getAbsolutePath()
                + "/PublicVideo");
        if (!file.exists()) {

            file.mkdir();
        }
        return file;
    }

    public static File getStorageAudioPath() {

        File file = new File(MyApp.getInstance().getExternalCacheDir().getAbsolutePath()
                + "/Audio");
        if (!file.exists()) {

            file.mkdir();
        }
        return file;
    }

    public static boolean isNull(File f) {


        try {
            if (!f.isFile()) {
                return true;
            }

        } catch (Exception e) {
            LogUtils.e("1111111" + e.getMessage());
            return true;
        }

        return false;
    }


    public static boolean delFile(String path) {

        Boolean isDle = false;

        File mFile = new File(path);

        if (mFile.exists()) {
            isDle = mFile.delete();
        } else {
            isDle = true;
        }

        return isDle;

    }

    /**
     * @return new File
     */
    public static List<File> getPngList() {
        List<File> list = new ArrayList<>();

        for (String s :
                MyApp.getInstance().getExternalCacheDir().list()) {
            if (s.contains(".png")) {

                list.add(new File(MyApp.getInstance().
                        getExternalCacheDir() + "/" + s));
            }
        }

        return list;
    }

    /**
     * 获取到文件的MD5值
     *
     * @param file
     * @return
     */
    public static String getFileMD5(File file) {

        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytesToHexString(digest.digest());
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    /**
     * 将String数据存为文件
     */
    public static File getFileFromBytes(String name) {
        byte[] b = name.getBytes();
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = FileUtils.newFile("txt");
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    public static String readtxt(File file) {//按行读取，不能保留换行等格式，所以需要手动添加每行换行符。
//        String result = "";
        StringBuffer txtContent = new StringBuffer();
        try {
            int len = 0;
            FileInputStream in = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(in, "utf-8");
            BufferedReader br = new BufferedReader(reader);
            String s = null;
            while ((s = br.readLine()) != null) {
                if (len != 0) {// 处理换行符的问题，第一行不换行
                    txtContent.append(new String(("\r\n" + s).getBytes(), "utf-8"));
                } else {
                    txtContent.append(new String(s.getBytes(), "utf-8"));
                }
                len++;
            }
            reader.close();
            in.close();
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return txtContent.toString();
    }


}