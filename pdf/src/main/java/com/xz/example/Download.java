//package net.itxw.example;
//
//import org.apache.tomcat.util.http.fileupload.IOUtils;
//
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
///**
// * @Description:
// * @Author: houyong
// * @Date: 2020/2/3
// */
//public class Download {
//    public static void main(String[] args) {
//        HttpURLConnection conn = null;
//        InputStream inputStream = null;
//        BufferedInputStream bis = null;
//        FileOutputStream out = null;
//        try
//        {
//            File file0=new File("D:\\test111");
//            if(!file0.isDirectory()&&!file0.exists()){
//                file0.mkdirs();
//            }
//            out = new FileOutputStream(file0+"\\2.jpg");
//            // 建立链接
//            URL httpUrl=new URL("http://bcxw.net/data/attachment/upload/image/201911/5dd4d3662e162174384491.png");
//            conn=(HttpURLConnection) httpUrl.openConnection();
//            //以Post方式提交表单，默认get方式
//            conn.setRequestMethod("GET");
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//            // post方式不能使用缓存
//            conn.setUseCaches(false);
//            //连接指定的资源
//            conn.connect();
//            //获取网络输入流
//            inputStream=conn.getInputStream();
//
//            IOUtils.copy(inputStream,out);
//
//            System.out.println("下载完成...");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally{
//            try {
//                if(out!=null){
//                    out.close();
//                }
//                if(bis!=null){
//                    bis.close();
//                }
//                if(inputStream!=null){
//                    inputStream.close();
//                }
//            } catch (Exception e2) {
//                e2.printStackTrace();
//            }
//        }
//
//    }
//}
