package com.xz.example;

//import org.xhtmlrenderer.pdf.ITextFontResolver;
//import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Description:
 * @Author: houyong
 * @Date: 2020/2/19
 */
public class HTML2PDF {
    public static void main(String[] args) {
        try {
            String url="http://10.10.22.185:3000/ecg?projectId=431000-923a0d14be0244eb8c509ce0a57684bb&subjectId=002&studentId=cdd17a5c-833f-417b-b6d4-d3ed60fedc72";
            String pdf="D:\\workspace\\code\\test\\test.pdf";

//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
//            System.out.println(sdf.format(new Date()));
//            String a=getHtmlPageResponse( url);
//            //System.out.println(a);
//            System.out.println(sdf.format(new Date()));
            //renderPdf();
            //itextToPdf();

            //convertHtmlToPdf(url,pdf);

            //exportPdfFile(url);

//            cdpTest3();
            //chromeTest();
            itextRender();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void itextRender(){
        try {
            ITextRenderer renderer = new ITextRenderer();

            System.setProperty("javax.xml.transform.TransformerFactory", "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");
            // 解决base64图片支持问题
            //SharedContext sharedContext = renderer.getSharedContext();
            //单独的类；
            //sharedContext.setReplacedElementFactory(new B64ImgReplacedElementFactory());
            //sharedContext.getTextRenderer().setSmoothingThreshold(0);
            //将HTML的内容写入对象
            Document htmlDocument = Jsoup.connect("https://www.baidu.com/").get();
            renderer.setDocument("http://10.10.22.185:3000/ecg?projectId=431000-923a0d14be0244eb8c509ce0a57684bb&subjectId=002&studentId=cdd17a5c-833f-417b-b6d4-d3ed60fedc72");
            //renderer.setDocumentFromString("<a href=''>a</a>");
            // 如果是HTML的路径用这个方法renderer.setDocument(htmlPath);
            //解决中文不显示的问题
            //ITextFontResolver fontResolver = renderer.getFontResolver();
            //String fontPath = ResourceUtils.getURL("classpath:templates/simsun.ttc").getPath();
            //fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //布局
            renderer.layout();
            //生成PDF
            FileOutputStream out=new FileOutputStream("d:/testR.pdf");
            renderer.createPDF(out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }


    }
//    public static void chromeTest(){
//        ChromeOptions chromeOptions = new ChromeOptions();
//        //chromeOptions.addArguments("--headless");
//        //chromeOptions.addArguments("--disable-gpu");
//        WebDriver driver = new ChromeDriver();
//        String url="http://10.10.22.185:3000/ecg?projectId=431000-923a0d14be0244eb8c509ce0a57684bb&subjectId=002&studentId=cdd17a5c-833f-417b-b6d4-d3ed60fedc72";
//
//        driver.get(url);
//        driver.navigate();
//    }



//    public static void cdpTest3(){
//        File tempFile =new File("d:/pdfTest/temp2111");
//        if(tempFile.exists()){
//            new File("d:/pdfTest/temp2111/DevToolsActivePort").delete();
//        };
//
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
//        System.out.println("准备："+sdf.format(new Date()));
//
//        Options options = Options.builder()
//                .headless(true)
//                .userDataDir(tempFile.toPath())
//                .build();
//        Launcher launcher = new Launcher(options);
//
//
//
//        try (SessionFactory factory = launcher.launch()) {
//
//            String context = factory.createBrowserContext();
//            Session session = factory.create(context);
//
//
//            String url="http://10.10.22.185:3000/ecg?projectId=431000-923a0d14be0244eb8c509ce0a57684bb&subjectId=001&studentId=cdd17a5c-833f-417b-b6d4-d3ed60fedc72";
//            url="https://www.aliyun.com/";
//            System.out.println("准备2："+sdf.format(new Date()));
//            html2Pdf(session,url,"d:/pdfTest/102.pdf");
//            //session.getCommand().getPage().disable();
//            System.out.println("准备3："+sdf.format(new Date()));
//            html2Pdf(session,url,"d:/pdfTest/103.pdf");
//            System.out.println("准备1："+sdf.format(new Date()));
//
//
//            factory.disposeBrowserContext(context);
//        }
//
//        launcher.kill();
//
//
//    }



//    public static void cdpTest2(){
//        File tempFile =new File("d:/pdfTest/temp2111");
//        if(tempFile.exists()){
//            new File("d:/pdfTest/temp2111/DevToolsActivePort").delete();
//        };
//
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
//        System.out.println("准备："+sdf.format(new Date()));
//
//        Options options = Options.builder()
//                .headless(true)
//                .userDataDir(tempFile.toPath())
//                .build();
//        Launcher launcher = new Launcher(options);
//
//        Launcher launcher2 = new Launcher(options);
//
//        try (SessionFactory factory = launcher.launch()) {
//
//            String context = factory.createBrowserContext();
//            Session session = factory.create(context);
//
//
//            String context2 = factory.createBrowserContext();
//            Session session2 = factory.create(context);
//
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    String url="http://10.10.22.185:3000/ecg?projectId=431000-923a0d14be0244eb8c509ce0a57684bb&subjectId=001&studentId=cdd17a5c-833f-417b-b6d4-d3ed60fedc72";
//                    html2Pdf(session,url,"d:/pdfTest/102.pdf");
//                    System.out.println("准备1："+sdf.format(new Date()));
//                }
//            }).start();
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    String url="http://10.10.22.185:3000/ecg?projectId=431000-923a0d14be0244eb8c509ce0a57684bb&subjectId=002&studentId=cdd17a5c-833f-417b-b6d4-d3ed60fedc72";
//                    html2Pdf(session2,url,"d:/pdfTest/101.pdf");
//                    System.out.println("准备2："+sdf.format(new Date()));
//                }
//            }).start();
//
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//
//            //factory.disposeBrowserContext(context);
//        }
//
//        //launcher.kill();
//
//
//    }

//    public static void cdpTest(){
//        List<String> urls=new ArrayList<>();
//        urls.add("http://10.10.22.185:3000/ecg?projectId=431000-923a0d14be0244eb8c509ce0a57684bb&subjectId=001&studentId=cdd17a5c-833f-417b-b6d4-d3ed60fedc72");
//        //urls.add("http://10.10.22.185:3000/ecg?projectId=431000-923a0d14be0244eb8c509ce0a57684bb&subjectId=002&studentId=cdd17a5c-833f-417b-b6d4-d3ed60fedc72");
//        //urls.add("http://10.10.22.185:3000/ecg?projectId=431000-923a0d14be0244eb8c509ce0a57684bb&subjectId=003&studentId=cdd17a5c-833f-417b-b6d4-d3ed60fedc72");
//        for(int i=0;i<1;i++){
//            cdpLunchTest("d:/pdfTest/"+i,urls);
//        }
//
//    }
//
//    public static void cdpLunchTest(String tempPath, List<String> urls){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//        File tempFile =new File(tempPath);;
//        if(tempFile.exists()){
//            File toolFile=new File(tempPath+"/DevToolsActivePort");
//            if(toolFile.exists()){
//                toolFile.delete();
//            }
//        };
//
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
//        System.out.println("准备："+sdf.format(new Date()));
//
//        Options options = Options.builder()
//                .headless(true)
//                .userDataDir(tempFile.toPath())
//                .build();
//        Launcher launcher = new Launcher(options);
//
//        try (SessionFactory factory = launcher.launch()) {
//            for(int i=0;i<1;i++){
//                cdpSession( factory, urls);
//            }
//
//        }
//
//        launcher.kill();
//
//            }
//        }).start();
//    }

//    public static void cdpSession(SessionFactory factory, List<String> urls){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String context = factory.createBrowserContext();
//                try (Session session = factory.create(context)) {
//                    for(String oneUrl:urls){
//                        html2Pdf(session,oneUrl,"d:/pdfTest/test/"+ UUID.randomUUID() +".pdf");
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                factory.disposeBrowserContext(context);
//            }
//        }).start();
//    }


//    public static void html2Pdf(Session session,String url,String path){
//        FileOutputStream out=null;
//        IO io=null;
//        String stream=null;
//        try {
//
//
//            session.navigate(url);
//            session.waitDocumentReady();
//
//            PrintToPDFResult result=session.getCommand().getPage().printToPDF(null, null,
//                    true, null,
//                    null, null,
//                    null, null,
//                    null, null,
//                    null, null,
//                    null, null,
//                    null, ReturnAsStream);
//
//            io = session.getCommand().getIO();
//            stream = result.getStream();
//
//            out =new FileOutputStream(path);
//
//            boolean eof = false;
//            while ( ! eof ) {
//                ReadResult streamResult = io.read(stream);
//                eof = streamResult.getEof();
//                if (streamResult.getBase64Encoded()) {
//                    if ( streamResult.getData() != null &&
//                            ! streamResult.getData().isEmpty() ) {
//                        byte[] content = getDecoder().decode(streamResult.getData());
//                        try {
//                            out.write(content);
//                            //Files.write(file.toPath(), content, APPEND);
//                        } catch (IOException e) {
//                            throw new CdpException(e);
//                        }
//                    }
//                } else {
//                    throw new CdpException("Inavlid content encoding: it must be base64");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                out.flush();
//                out.close();
//                io.close(stream);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//    }

//    public static void renderPdf(){
//        try {
//            ITextRenderer renderer = new ITextRenderer();
//            String url="http://10.10.22.185:3000/ecg?projectId=431000-923a0d14be0244eb8c509ce0a57684bb&subjectId=002&studentId=cdd17a5c-833f-417b-b6d4-d3ed60fedc72";
//
//            renderer.setDocument(url);
//            renderer.layout();
//            renderer.createPDF(new FileOutputStream("d:/tren.pdf"));
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

//    public static void itextToPdf() {
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
//        System.out.println(sdf.format(new Date()));
//
//        HttpURLConnection conn=null;
//        InputStream inputStream=null;
//        OutputStream pdfStream=null;
//        try {
//
//            String url="http://www.baidu.com";
//
//            Document htmlDocument = Jsoup.connect(url).get();
//
//            ConverterProperties converterProperties=new ConverterProperties();
//            converterProperties.setBaseUri("D:\\pdfTest");
//            PdfWriter writer = new PdfWriter("d:/pdfTest/test1.pdf");
//            HtmlConverter.convertToPdf(htmlDocument.html(),writer,converterProperties);
//
//            System.out.println(sdf.format(new Date()));
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
////            try {
////                //pdfStream.close();
////                //inputStream.close();
////                //conn.disconnect();
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
//        }
//    }



//    public static String getHtmlPageResponse(String url) throws Exception {
//        String result = "";
//
//        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
//
//        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常
//        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常
//        webClient.getOptions().setActiveXNative(false);
//        webClient.getOptions().setCssEnabled(true);//是否启用CSS
//        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
//        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX
//
//        webClient.getOptions().setTimeout(30000);//设置“浏览器”的请求超时时间
//        webClient.setJavaScriptTimeout(30000);//设置JS执行的超时时间
//
//        HtmlPage page;
//        try {
//            page = webClient.getPage(url);
//        } catch (Exception e) {
//            webClient.close();
//            throw e;
//        }
//        webClient.waitForBackgroundJavaScript(30000);//该方法阻塞线程
//
//
//
//        result = page.asXml();
//        webClient.close();
//
//        return result;
//    }





//
//    public static boolean convertHtmlToPdf(String url, String outputFile)
//            throws Exception {
//
//        OutputStream os = new FileOutputStream(outputFile);
//        ITextRenderer renderer = new ITextRenderer();
//        //InputStream inputStream = new ByteArrayInputStream(Jsoup.connect(url).get().html().getBytes());
//        //String urlStr = IOUtils.toString(inputStream);
//        //renderer.setDocumentFromString(urlStr);
//        renderer.setDocument("D:\\pdfTest\\test1.html");
////        // 解决中文支持问题
//        ITextFontResolver fontResolver = renderer.getFontResolver();
//        //fontResolver.addFont(SystemConstant.local_dir + "/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//        //解决图片的相对路径问题，绝对路径不需要写
////        renderer.getSharedContext().setBaseURL("file:/D:/");
//        renderer.layout();
//        renderer.createPDF(os);
//
//        os.flush();
//        os.close();
//        return true;
//    }
//
//
//    public static File exportPdfFile(String urlStr)  {
//        // String outputFile = this.fileRoot + "/" +
//        // ServiceConstants.DIR_PUBINFO_EXPORT + "/" + getFileName() + ".pdf";
//        String outputFile = "d:/test3.pdf";
//        OutputStream os;
//        try {
//            os = new FileOutputStream(outputFile);
//
//            ITextRenderer renderer = new ITextRenderer();
//
//            String str = getHtmlFile(urlStr);
//            renderer.setDocumentFromString(str);
//            ITextFontResolver fontResolver = renderer.getFontResolver();
//
//            //fontResolver.addFont("C:/WINDOWS/Fonts/SimSun.ttc",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 宋体字
//            //fontResolver.addFont("C:/WINDOWS/Fonts/Arial.ttf",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 宋体字
//            renderer.layout();
//
//            renderer.createPDF(os);
//
//            System.out.println("转换成功！");
//            os.flush();
//            os.close();
//            return new File(outputFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//
//    }
//
//    // 读取页面内容 add by huangt 2012.6.1
//    public static String getHtmlFile(String urlStr) throws  IOException {
//        URL url;
//        try {
//            if (urlStr.indexOf("?") != -1) {
//                urlStr = urlStr + "&locale="
//                        + LocaleContextHolder.getLocale().toString();
//            } else {
//                urlStr = urlStr + "?locale="
//                        + LocaleContextHolder.getLocale().toString();
//            }
//            url = new URL(urlStr);
//
//            URLConnection uc = url.openConnection();
//            InputStream is = uc.getInputStream();
//
//            Tidy tidy = new Tidy();
//
//            OutputStream os2 = new ByteArrayOutputStream();
//            tidy.setXHTML(true); // 设定输出为xhtml(还可以输出为xml)
//            tidy.setCharEncoding(Configuration.UTF8); // 设定编码以正常转换中文
//            tidy.setTidyMark(false); // 不设置它会在输出的文件中给加条meta信息
//            tidy.setXmlPi(true); // 让它加上<?xml version="1.0"?>
//            tidy.setIndentContent(true); // 缩进，可以省略，只是让格式看起来漂亮一些
//            tidy.parse(is, os2);
//
//            is.close();
//
//            // 解决乱码 --将转换后的输出流重新读取改变编码
//            String temp;
//            StringBuffer sb = new StringBuffer();
//            BufferedReader in = new BufferedReader(new InputStreamReader(
//                    new ByteArrayInputStream(
//                            ((ByteArrayOutputStream) os2).toByteArray()),
//                    "utf-8"));
//            while ((temp = in.readLine()) != null) {
//                sb.append(temp);
//            }
//
//            return sb.toString();
//        } catch (IOException e) {
//            // logger.error("读取客户端网页文本信息时出错了" + e.getMessage());
//            throw e;
//        }
//
//    }
}
