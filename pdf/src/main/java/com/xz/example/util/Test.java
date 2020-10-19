//package net.itxw.example.util;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.*;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Author: houyong
// * @Date: 2019/4/23
// */
//public class Test {
//
//
//    public static void main(String[] args) {
//        try {
//            Map<String,String> m=getHrefKeyValue();
//
//            System.out.println(m);
//
//            File fileDir = new File("D:\\software\\PHPWAMP_IN3\\wwwroot\\api\\mysql");
//            File[] files = fileDir.listFiles();
//
//            for(File file:files){
//                if(file.getName().endsWith("php")) {
//
//                    boolean needUpdate=false;
//
//                    Document doc = Jsoup.parse(file, "utf-8");
//                    Elements els=doc.getElementsByTag("a");
//                    for(Element el:els){
//                        String t=el.attr("title");
////                        if(StringUtils.isNotEmpty(t)){
////                            String[] arr= t.split("\\.");
////                            if(arr.length>=5){
////                                String key=arr[0]+"."+arr[1]+"."+arr[2];
////                                String href=m.get(key);
////                                if(StringUtils.isNotEmpty(t)){
////                                    needUpdate=true;
////                                    el.attr("href",href);
////                                }
////                            }
////                        }
//                    }
//
//
//
//                    if(needUpdate) {
//
//                        file.delete();
//
//
//                        OutputStream osw = new FileOutputStream(file);
//                        OutputStreamWriter os = new OutputStreamWriter(osw, "utf-8");
//                        BufferedWriter bw = new BufferedWriter(os);
//                        String c=doc.toString();
//                        c=c.replace("<!--?","<?");
//                        c=c.replace("?-->","?>");
//                        bw.write(c);
//                        bw.close();
//                        os.close();
//                        osw.close();
//                    }
//                }
//
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    public static Map<String,String> getHrefKeyValue(){
//        Map<String,String> resultMap=new HashMap<>();
//
//        try {
//            Document doc= Jsoup.parse(new File("D:\\software\\PHPWAMP_IN3\\wwwroot\\api\\mysql\\common_list.php"),"utf-8");
//
//            Elements els=doc.getElementsByTag("a");
//            for(int i=0;i<els.size();i++){
//                Element el=els.get(i);
//                String text=el.text();
//                String[] textArr=text.split("\\.");
//                if(textArr.length>=4){
//                    String key=textArr[0]+"."+textArr[1]+"."+textArr[2];
//                    String href=el.attr("href");
//                    resultMap.put(key,href);
//                }
//
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return resultMap;
//
//    }
//}
