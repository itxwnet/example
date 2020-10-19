package com.xz.example;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: houyong
 * @Date: 2020/5/7
 */
public class WordTest {

    public static void main(String[] args) {
        String a="jpsa";

        System.out.println(a.substring(0,3));





    }

//    public static void main(String[] args) {
//
//        try {
//            String srcPath = "d:/wordTest/A佳教育•高一年级线上学习阶段性成果大联考数学（属性、标签版）.docx";
//            String desPath = "d:/wordTest/wp.pdf";
//            Word2Pdf(srcPath, desPath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }


    // 将word格式的文件转换为pdf格式
    public static void Word2Pdf(String srcPath, String desPath) throws IOException {
        // 源文件目录
        File inputFile = new File(srcPath);
        if (!inputFile.exists()) {
            System.out.println("源文件不存在！");
            return;
        }
        // 输出文件目录
        File outputFile = new File(desPath);
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().exists();
        }
        // 调用openoffice服务线程
        String command = "C:\\Program Files (x86)\\OpenOffice 4\\program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
        Process p = Runtime.getRuntime().exec(command);

        // 连接openoffice服务
        OpenOfficeConnection connection = new SocketOpenOfficeConnection(
                "127.0.0.1", 8100);
        connection.connect();

        // 转换word到pdf
        DocumentConverter converter = new OpenOfficeDocumentConverter(
                connection);
        converter.convert(inputFile, outputFile);

        // 关闭连接
        connection.disconnect();

        // 关闭进程
        p.destroy();
        System.out.println("转换完成！");
    }

    public static void word2pdfSpire(){
        try {
            Document doc = new Document("d:/wordTest/A佳教育•高一年级线上学习阶段性成果大联考数学（属性、标签版）.docx");
            OutputStream out=new FileOutputStream("d:/wordTest/wp.pdf");
            doc.saveToFile(out, FileFormat.PDF);
            out.close();


//            //加载word示例文档
//            Document document = new Document();
//            document.loadFromFile("Sample.docx");
//
//
//            //保存结果文件
//            document.saveToFile().save.saveToFile("out/toPDF.pdf", FileFormat.PDF)
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void word2pdf(){
        try {
            FileInputStream fileInputStream = new FileInputStream("d:/wordTest/错题集元素&样式整理.docx");
            XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);
            PdfOptions pdfOptions = PdfOptions.create();
            FileOutputStream fileOutputStream = new FileOutputStream("d:/wordTest/poi笔记.pdf");
            PdfConverter.getInstance().convert(xwpfDocument,fileOutputStream,pdfOptions);
            fileInputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        replaceContent();
    }

    //拼接多个 pdf 应用不同的页码和页脚
    public static void mergeDoc(){
        try {
            InputStream templateIS = new FileInputStream("D:\\template.docx");
            InputStream contentIS = new FileInputStream("D:\\templateContent.docx");

            XWPFDocument templateDoc = new XWPFDocument(templateIS);
            XWPFDocument contentDoc = new XWPFDocument(contentIS);



            XmlOptions optionsOuter = new XmlOptions();
            optionsOuter.setSaveOuter();


            //XmlObject templateXml=templateDoc.getDocument().copy();
            //XmlObject contentXml=templateDoc.getDocument().copy();




            //System.out.println(newBody.toString());

            //String templateXml=templateDoc.getDocument().getBody().xmlText(optionsOuter);
            //String contentXml=contentDoc.getDocument().getBody().xmlText(optionsOuter);

            List<XWPFParagraph> paragraphs=templateDoc.getParagraphs();

            XWPFParagraph tempParagraph=paragraphs.get(paragraphs.size()-1);
            XmlCursor cursor=tempParagraph.getCTP().newCursor();
            cursor.toNextSibling();//下移光标
            templateDoc.insertNewParagraph(cursor).setPageBreak(false);


            //templateDoc.getParagraphs().get(0).


            templateDoc.getDocument().getBody();

            //appendBody(templateDoc.getDocument().getBody(), contentDoc.getDocument().getBody());
            templateDoc.getDocument().addNewBody().set(contentDoc.getDocument().getBody());

            OutputStream out=new FileOutputStream("d:/resultMerge.docx");

            templateDoc.write(out);

            out.close();

            templateIS.close();
            contentIS.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void appendBody(CTBody src, CTBody append) throws Exception {
        XmlOptions optionsOuter = new XmlOptions();
        optionsOuter.setSaveOuter();
        String appendString = append.xmlText(optionsOuter);
        String srcString = src.xmlText();
        String prefix = srcString.substring(0,srcString.indexOf(">")+1);
        String mainPart = srcString.substring(srcString.indexOf(">")+1,srcString.lastIndexOf("<"));
        String sufix = srcString.substring( srcString.lastIndexOf("<") );
        String addPart = appendString.substring(appendString.indexOf(">") + 1, appendString.lastIndexOf("<"));
        CTBody makeBody = CTBody.Factory.parse(prefix+mainPart+addPart+sufix);
        src.set(makeBody);
    }

    /**
     * 在模板变量位置替换内容
     * 包括文字 图片 公式 表格 页眉页脚
     */
    public static void replaceContent() {
        try {

            InputStream templateIS = new FileInputStream("D:\\wordTest\\template.docx");
            InputStream contentIS = new FileInputStream("D:\\wordTest\\content.docx");


            XWPFDocument templateDoc = new XWPFDocument(templateIS);
            XWPFDocument contentDoc = new XWPFDocument(contentIS);

            Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);

            //填充页眉页脚
            List<XWPFHeader>  headers=templateDoc.getHeaderList();
            for(XWPFHeader header:headers){
                List<XWPFParagraph>  paragraphs=header.getParagraphs();
                for(XWPFParagraph paragraph:paragraphs){
                    List<XWPFRun> runs=paragraph.getRuns();
                    Map<String,Integer> posMap=new HashMap<>();
                    for(int r=0;r<runs.size();r++){
                        XWPFRun hr=runs.get(r);
                        String text=hr.text();
                        //变量位置
                        if("header".equals(text)){

                            XWPFRun newRun=paragraph.insertNewRun(r);
                            newRun.setText("【迅智中学】    侯勇");
                            newRun.getCTR().setRPr(hr.getCTR().getRPr());
                            paragraph.removeRun(r+1);
                            break;
                        }
                    }

                }
            }


            List<XWPFParagraph> contentParagraphs=contentDoc.getParagraphs();

            List<XWPFParagraph> templateParagraphs=templateDoc.getParagraphs();



            for(int i=0;i<templateParagraphs.size();i++){
                XWPFParagraph templateParagraph=templateParagraphs.get(i);
                String ptext=templateParagraph.getParagraphText();
                if(!StringUtils.isEmpty(ptext)) {
                    if (pattern.matcher(ptext).find()) {
                        List<XWPFRun> templateRuns = templateParagraph.getRuns();
                        for (int j=0;j<templateRuns.size();j++) {
                            XWPFRun run=templateRuns.get(j);
                            String runText=run.text();
                            if (pattern.matcher(runText).find()) {
                                //设置姓名
                                if(runText.equals("${studentName}")){
                                    //tempRun.get
                                            //templateParagraph.createRun().setText("侯勇");
                                }

                                //tempRun.setText("哈哈哦哦");

                                //XmlCursor xmlCursor = templateParagraph.getCTP().newCursor();
//                                    xmlCursor.toNextSibling();
                                //templateDoc.getDocument().addNewBody().set(contentDoc.getDocument().getBody());

//                                templateParagraph

//                                for(XWPFParagraph contentParagraph:contentParagraphs){
//                                    //在匹配到的段落插入 内容段落
//                                    XmlCursor xmlCursor = templateParagraph.getCTP().newCursor();
//                                    xmlCursor.toNextSibling();//下移光标
//                                    XWPFParagraph newParagraph=templateDoc.insertNewParagraph(xmlCursor);
//
////                                    List<XWPFRun> contentRuns=contentParagraph.getRuns();
////                                    for(XWPFRun contentRun:contentRuns){
////                                        XWPFRun newRun=newParagraph.createRun();
////                                        newParagraph.getCTP().setPPr(contentParagraph.getCTP().getPPr());
////                                        newRun.getCTR().setRPr(contentRun.getCTR().getRPr());
////                                        newRun.setText(contentRun.text());
////                                    }
//                                }



                                break;
                            }
                        }
                    }
                }
            }





            OutputStream out=new FileOutputStream("d:/wordTest/replaceResult.docx");

            templateDoc.write(out);

            out.close();

            templateIS.close();
            contentIS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
