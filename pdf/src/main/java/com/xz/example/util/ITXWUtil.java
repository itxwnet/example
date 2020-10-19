//package net.itxw.example.util;
//
//import org.jsoup.Jsoup;
//import org.jsoup.helper.StringUtil;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.*;
//import java.sql.*;
//import java.util.*;
//
///**
// * @Author: houyong
// * @Date: 2019/4/5
// */
//public class ITXWUtil {
//    private static Connection connection=null;
//
//
//    //public static void main(String[] args) {
//
//        //createArticleKeywordIndex();
//
//        //List<String> allLink=getAllHref("https://www.runoob.com/linux/linux-command-manual.html","content","https://www.runoob.com/linux/","https://www.runoob.com");
//        //cacheHtmls(allLink,"D:\\software\\PHPWAMP_IN3\\wwwroot\\api\\linux\\","content");
//
//        //changeContent("D:\\software\\PHPWAMP_IN3\\wwwroot\\api\\linux");
//        //renameFiles("D:\\software\\PHPWAMP_IN3\\wwwroot\\api\\linux");
//        //convertCode("D:\\software\\PHPWAMP_IN3\\wwwroot\\api\\jeasyui");
//        //createChmMenu("D:\\software\\PHPWAMP_IN3\\wwwroot\\api\\vue\\menu.php");
//    //}
//
//    /**
//     * chm 反编译 Contents 生成menu代码
//     * @param path
//     */
//    public static void createChmMenu(String path){
//        try {
//            File file = new File(path);
//            FileReader fr=new FileReader(file);
//
//            BufferedReader br=new BufferedReader(fr);
//            StringBuffer content=new StringBuffer();
//            String strLine=null;
//            while((strLine=br.readLine())!=null){
//                content.append(strLine);
//            }
//
//
//            br.close();
//            fr.close();
//
//
//            Document doc = Jsoup.parseBodyFragment(content.toString());
//            Elements els=doc.getElementsByTag("object");
//
//            for(int i=0;i<els.size();i++){
//                Element el=els.get(i);
//                Element nameEl=el.getElementsByAttributeValue("name","Name").first();
//                Element valueEl=el.getElementsByAttributeValue("name","Local").first();
//                String text=nameEl.attr("value");
//                String href=valueEl!=null?valueEl.attr("value"):null;
//                el.tagName("a");
//                el.removeAttr("type");
//                el.text(text);
//                if(href!=null)el.attr("href",href);
//
//            }
//
//            System.out.println(doc);
//
//            file.delete();
//
//            OutputStream osw=new FileOutputStream(file);
//            OutputStreamWriter os=new OutputStreamWriter(osw,"utf-8");
//            BufferedWriter bw=new BufferedWriter(os);
//            bw.write(doc.toString());
//            bw.close();
//            os.close();
//            osw.close();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 递归重命名文件名
//     * @param path
//     */
//    public static void renameFiles(String path){
//        String prefix=".html";
//        String suffix=".php";
//
//        try {
//            File fileDir = new File(path);
//            File[] files = fileDir.listFiles();
//
//            for(File file:files){
//
//                String filePath=file.getPath();
//                if(file.isDirectory()){
//                    renameFiles(filePath);
//                }else{
//                    String fileName=file.getPath();
//                    if(fileName.endsWith(prefix)){
//                        String newFileName=fileName.replace(prefix,suffix);
//                        File newFile=new File(newFileName);
//                        file.renameTo(newFile);
//                    }
//
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 文件gbk转utf8
//     * @param path
//     */
//    public static void convertCode(String path){
//        String fix=".php";
//        try {
//            File fileDir = new File(path);
//            File[] files = fileDir.listFiles();
//
//            for(File file:files){
//
//                String filePath=file.getPath();
//                if(file.isDirectory()){
//                    convertCode(filePath);
//                }else{
//                    String fileName=file.getName();
//                    if(fileName.endsWith(fix)){
//
//                        FileInputStream is =new FileInputStream(file);
//                        InputStreamReader fr=new InputStreamReader(is,"gb2312");
//                        BufferedReader reader=new BufferedReader(fr);
//
//                        StringBuffer sb=new StringBuffer();
//
//                        String str="";
//                        while ((str=reader.readLine())!=null){
//                            sb.append(str+"\r\n");
//                        }
//
//                        reader.close();
//                        fr.close();
//                        is.close();
//
//                        file.delete();
//
//                        String content=sb.toString();
//                        System.out.println(content);
//
//                        OutputStream osw=new FileOutputStream(filePath);
//                        OutputStreamWriter os=new OutputStreamWriter(osw,"utf-8");
//                        BufferedWriter bw=new BufferedWriter(os);
//                        bw.write(content);
//                        bw.close();
//                        os.close();
//                        osw.close();
//                    }
//
//                }
//
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    /**
//     * 修改内容，将指定内容提取到title
//     */
//    public static void changeContent(String path){
//        String fix=".php";
//        try {
//            File fileDir = new File(path);
//            File[] files = fileDir.listFiles();
//
//            Document tmpelate=Jsoup.parse(new File("D:\\software\\PHPWAMP_IN3\\wwwroot\\api\\commonTemplate.php"),"utf-8");
//
//            for(File file:files){
//
//                String filePath=file.getPath();
//                if(file.isDirectory()){
//                    changeContent(filePath);
//                }else{
//                    String fileName=file.getName();
//                    if(fileName.endsWith(fix)){
//
//                        Document doc=Jsoup.parse(file,"utf-8");
//
//                        tmpelate.getElementsByTag("article").html(doc.html());
//
//                        Elements el=doc.getElementsByTag("h1");
//                        String title=el!=null&&el.size()>0?el.get(0).text():"";
//
//                        title=title+"_IT学问网";
//                        tmpelate.getElementsByTag("title").html(title);
//
//                        OutputStream osw=new FileOutputStream(filePath);
//                        OutputStreamWriter os=new OutputStreamWriter(osw,"utf-8");
//                        BufferedWriter bw=new BufferedWriter(os);
//                        bw.write(tmpelate.html());
//                        bw.close();
//                        os.close();
//                        osw.close();
//                    }
//
//                }
//
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //下载reidsfans
//    public static void main(String[] args) {
////        String domain="http://doc.redisfans.com/";
////        String path="D:/bcxw/redisfans/";
////
////        List<String> allHref= getAllHref("http://doc.redisfans.com/index.html","redis");
////
////
////        cacheHtmls(allHref,path,domain);
//        renameFiles("D:\\bcxw\\redisfansPhp");
//    }
//
//    /**
//     * 存储html
//     * @param urls
//     * @param cachePath
//     */
//    public static void cacheHtmls(List<String> urls,String cachePath,String domain){
//        try {
//            for(int i=0;i<urls.size();i++){
//                String url=urls.get(i);
//                String link=domain+url;
//                Document doc=Jsoup.connect(link).get();
//
//
//                String[] fileNames=link.split("/");
//                String fileName=fileNames[fileNames.length-1];
//
//                String relativePath=url.replace(fileName,"");
//
//                String fileParentPath=cachePath+relativePath;
//                File parentPath=new File(fileParentPath);
//                if(!parentPath.exists()){
//                    parentPath.mkdirs();
//                }
//
//
//                OutputStream osw=new FileOutputStream(fileParentPath+fileName);
//                OutputStreamWriter os=new OutputStreamWriter(osw,"utf-8");
//                BufferedWriter bw=new BufferedWriter(os);
//
//                //String html=doc.getElementById(contentId)!=null?doc.getElementById(contentId).html():"";
//                //html="itxwHead"+html+"itxwFoot";
//                bw.write(doc.html());
//                bw.close();
//                os.close();
//                osw.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 获取某页面所有链接
//     * @param url
//     * @param contentId
//     * @return
//     */
//    public static List<String> getAllHref(String url ,String contentId){
//        List<String> returnList=new ArrayList<>();
//        try {
//
//            Document doc=Jsoup.connect(url).get();
//
//            Elements els=doc.getElementById(contentId).getElementsByTag("a");
//            for(int i=0;i<els.size();i++){
//                String link=els.get(i).attr("href");
//                if(!link.startsWith("http")&&!link.contains("#")){
//                    returnList.add(link);
//                }
//                //link=link.startsWith("/")?siteDomain+link:parentPath+link;
//
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return returnList;
//    }
//
///***********************************************article index**************************************************************/
//    public static void createArticleKeywordIndex(){
//
//        try {
//
//
//            //mysql article
//
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/itxw?useSSL=true&serverTimezone=UTC","root", "123456");
//            connection=conn;
//            Statement st=conn.createStatement();
//            String sql="select id,title,content from itxwArticle";
//            ResultSet rs=st.executeQuery(sql);
//
//            while(rs.next()){
//                int id = rs.getInt("id");
//                String content = rs.getString("content");
//                String title = rs.getString("title");
//                String url="/article/"+id+".html";
//                Document doc = Jsoup.parse(content);
//                String description=Jsoup.parse(doc.text()).text();
//                description=description.substring(0,description.length()>100?100:description.length());
//                SaveArticleKeywordIndex( url, title, description);
//            }
//
//            //mysql book
//            sql="select id,title,content from itxwBook";
//            rs=st.executeQuery(sql);
//
//            while(rs.next()){
//                int id = rs.getInt("id");
//                String content = rs.getString("content");
//                String title = rs.getString("title");
//                String url="/book/"+id+".html";
//                Document doc = Jsoup.parse(content);
//                String description=Jsoup.parse(doc.text()).text();
//                description=description.substring(0,description.length()>100?100:description.length());
//                SaveArticleKeywordIndex( url, title, description);
//            }
//
//            String apiPath="D:\\software\\PHPWAMP_IN3\\wwwroot\\api";
//            String basePath="D:\\software\\PHPWAMP_IN3\\wwwroot";
//            IndexFileKeyword(basePath,apiPath);
//
//
//            st.close();
//            conn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void IndexFileKeyword(String basePath,String path) throws IOException, SQLException {
//        File fileDir = new File(path);
//        File[] files = fileDir.listFiles();
//        for(File file:files){
//
//            if(file.isDirectory()){
//                IndexFileKeyword(basePath,file.getPath());
//            }else if(file.isFile()){
//                if(file.getName().toLowerCase().endsWith(".php")){
//                    Document doc = Jsoup.parse(file,"utf-8");
//
//                    Elements titleEls=doc.getElementsByTag("title");
//                    if(titleEls!=null&&!titleEls.isEmpty()){
//                        String title=titleEls.get(0).text().replace("_IT学问网","");
//                        if(title.length()>=4){
//                            String url=file.getPath().replace(basePath,"").replace("\\","/");
//                            url=url.substring(0,url.length()-3)+"html";
//                            String description=Jsoup.parse(doc.text()).text();
//                            description=description.substring(0,description.length()>100?100:description.length());
//                            description=description.replace("_IT学问网","");
//                            SaveArticleKeywordIndex( url, title, description);
//                        }
//                    }
//
//
//                }
//
//            }
//
//        }
//    }
//
//    public static void SaveArticleKeywordIndex(String url,String title,String description) throws SQLException {
//
//        List<String> delWords = Arrays.asList("如何","解决","问题","区别","方式","方法","介绍","详解","详细","关于","使用","案例","代码","一个","获取","简介","范例","for","or","on","with","an","by","the","as","of","is","are","to","has","get","version","library","and","in","what","this");
//
//        String regEx="[-\\[\\]\\(\\)\\<\\>\"\'\\\\,_~^:]";
//        description=description.replaceAll(regEx," ");
//
//        String tempTitle=title.toLowerCase().replaceAll(regEx," ");
//        Set<String> keywordList = new HashSet();
//        List<Term> ks=HanLP.segment(tempTitle);
//        for (Term t:ks) {
//            String oneWord=t.word.trim();
//            if(oneWord.length()>1&&!delWords.contains(oneWord)){
//                keywordList.add(oneWord);
//            }
//
//        }
//
//
//        String keyword=StringUtil.join(keywordList," ");
//        System.out.println(description+"ccc");
//        String sql="insert into itxwIndex_index(url,title,keywords,description) values('"+url+"','"+tempTitle+"','"+keyword+"','"+description+"')";
//        Statement st=connection.createStatement();
//
//        st.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
//
//        ResultSet rs = st.getGeneratedKeys();
//
//        if(rs.next()){
//            int id= rs.getInt(1);
//
//            List sqlvalues=new ArrayList();
//            for(Object o:keywordList){
//                sqlvalues.add("('"+id+"','"+o+"')");
//            }
//
//            String keywordSql="insert into itxwindex_keyword values"+StringUtil.join(sqlvalues,",");
//            st.executeUpdate(keywordSql);
//
//        }
//
//        st.close();
//
//    }
//}
