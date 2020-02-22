package com.example.chuanke.chuanke.util;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    //发送JSON字符串 如果成功则返回成功标识。
    public static void doJsonPost(final Handler handler, final String urlPath, final String Json) {

        //在子线程中操作网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                // HttpClient 6.0被抛弃了
                String result = "";
                BufferedReader reader = null;
                try {
                    URL url = new URL(urlPath);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Charset", "UTF-8");
                    // 设置文件类型:
                    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    // 设置接收类型否则返回415错误
                    //conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
                    conn.setRequestProperty("accept", "application/json");
                    // 往服务器里面发送数据
                    if (Json != null && !TextUtils.isEmpty(Json)) {
                        byte[] writebytes = Json.getBytes();
                        // 设置文件长度
                        conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                        OutputStream outwritestream = conn.getOutputStream();
                        outwritestream.write(Json.getBytes());
                        outwritestream.flush();
                        outwritestream.close();
                        Log.d("hlhupload", "doJsonPost: conn" + conn.getResponseCode());
                    }
                    int responseCode = conn.getResponseCode();
                    if (responseCode == 200) {
                        reader = new BufferedReader(
                                new InputStreamReader(conn.getInputStream()));
                        result = reader.readLine();

                        Message msg = Message.obtain();
                        msg.obj = result;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    //发送JSON字符串 如果成功则返回成功标识。
    public static String doAsyncJsonPost(final String urlPath, final String Json) {

        // HttpClient 6.0被抛弃了
        String result = "";
        BufferedReader reader = null;
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            // 设置文件类型:
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            // 设置接收类型否则返回415错误
            //conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
            conn.setRequestProperty("accept", "application/json");
            // 往服务器里面发送数据
            if (Json != null && !TextUtils.isEmpty(Json)) {
                byte[] writebytes = Json.getBytes();
                // 设置文件长度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(Json.getBytes());
                outwritestream.flush();
                outwritestream.close();
                Log.d("hlhupload", "doJsonPost: conn" + conn.getResponseCode());
            }
            if (conn.getResponseCode() == 200) {
                reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                result = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    // Get请求 如果成功则返回成功标识。

    /**
     * GET请求
     * @param handler 回调方法
     * @param urlPath 地址
     * @return JSON格式字符串
     */
    public static void doAsyncGet(final Handler handler, final String urlPath, final int flag) {

        //在子线程中操作网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = "";
                BufferedReader reader = null;
                try {
                    URL url = new URL(urlPath);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Charset", "UTF-8");
                    // 设置文件类型:
                    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    // 设置接收类型否则返回415错误
                    //conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
                    conn.setRequestProperty("accept", "application/json");

                    if (conn.getResponseCode() == 200) {
                        reader = new BufferedReader(
                                new InputStreamReader(conn.getInputStream()));
                        result = reader.readLine();

                        Message msg = Message.obtain();
                        msg.obj = result;
                        msg.what = flag;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }).start();
    }
}
