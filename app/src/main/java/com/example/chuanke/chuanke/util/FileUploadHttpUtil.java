package com.example.chuanke.chuanke.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class FileUploadHttpUtil {

    /**
     * @param actionUrl 上传的地址
     * @param json    上传的键值对参数
     * @param filePath  上传的图片路径
     */
    public static void post(String actionUrl, String json, String filePath) {

        //前面设置报头不需要更改
        try {
            String BOUNDARY = "--------------et567z"; //数据分隔线
            String MULTIPART_FORM_DATA = "Multipart/form-data";
            URL url = new URL(actionUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);//允许输入
            conn.setDoOutput(true);//允许输出
            conn.setUseCaches(false);//不使用Cache
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA + ";boundary=" + BOUNDARY);

            //获取map对象里面的数据，并转化为string
//            StringBuilder sb = new StringBuilder();
//            System.out.println("上传名称:" + params.get("data"));
//            //上传的表单参数部分，不需要更改
//            for (Map.Entry<String, String> entry : params.entrySet()) {//构建表单字段内容
//                sb.append("--");
//                sb.append(BOUNDARY);
//                sb.append("\r\n");
//                sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n");
//                sb.append(entry.getValue());
//                sb.append("\r\n");
//            }
//            System.out.println(sb.toString());


            //上传图片部分
            DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
            outStream.write(json.getBytes());//发送表单字段数据
            //调用自定义方法获取图片文件的byte数组
            byte[] content = readFileImage(filePath);
            //再次设置报头信息
            StringBuilder split = new StringBuilder();
            split.append("--");
            split.append(BOUNDARY);
            split.append("\r\n");


            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!非常重要
            //此处将图片的name设置为file ,filename不做限制，不需要管
            split.append("Content-Disposition: form-data;name=\"file\";filename=\"temp.jpg\"\r\n");
            //这里的Content-Type非常重要，一定要是图片的格式，例如image/jpeg或者image/jpg
            //服务器端对根据图片结尾进行判断图片格式到底是什么,因此务必保证这里类型正确
            split.append("Content-Type: image/png\r\n\r\n");
            outStream.write(split.toString().getBytes());
            outStream.write(content, 0, content.length);
            outStream.write("\r\n".getBytes());
            byte[] end_data = ("--" + BOUNDARY + "--\r\n").getBytes();//数据结束标志
            outStream.write(end_data);
            outStream.flush();


            //返回状态判断
            int cah = conn.getResponseCode();
            //            if (cah != 200) throw new RuntimeException("请求url失败:"+cah);
            if (cah == 200)//如果发布成功则提示成功
            {
                System.out.println("上传成功");
            } else if (cah == 400) {
                System.out.println("400错误");
            } else {
                outStream.close();
                conn.disconnect();
                throw new RuntimeException("请求url失败:" + cah);
            }

            outStream.close();
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] readFileImage(String filePath) throws IOException {

        BufferedInputStream bufferedInputStream = new BufferedInputStream(
                new FileInputStream(filePath));
        int len = bufferedInputStream.available();
        byte[] bytes = new byte[len];
        int r = bufferedInputStream.read(bytes);
        if (len != r) {
            bytes = null;
            throw new IOException("读取文件不正确");
        }
        bufferedInputStream.close();
        return bytes;
    }

}
