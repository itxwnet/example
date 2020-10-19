package com.xz.example;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.LockSupport;

/**
 * @Description:
 * @Author: houyong
 * @Date: 2020/5/14
 */
public class JACOB {

    public static void main(String[] args) {

        word2pdf();
    }

    static final int wdFormatPDF = 17;// word转PDF 格式
    public static void word2pdf() {
        LockSupport.park();

        String source="D:/wordTest/错题与提分练习.docx";
        String target="D:/wordTest/xx.pdf";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("start:"+df.format(new Date()));

        ComThread.InitSTA();
        ActiveXComponent app = null;
        try {
            app = new ActiveXComponent("Word.Application");
            app.setProperty("Visible", new Variant(false));
            Dispatch docs = app.getProperty("Documents").toDispatch();
            System.out.println("init:"+df.format(new Date()));
            System.out.println("第一个");
            Dispatch doc = Dispatch.call(docs, "Open", source).toDispatch();
            System.out.println("open:"+df.format(new Date()));
            Dispatch.call(doc, "SaveAs", target, wdFormatPDF);
            System.out.println("printPdf:"+df.format(new Date()));
            Dispatch.call(doc, "Close", new Variant(false));
            System.out.println("closed:"+df.format(new Date()));

            System.out.println("第二个");

            source="D:/wordTest/错题与提分练习2.docx";
            target="D:/wordTest/xx2.pdf";
            doc = Dispatch.call(docs, "Open", source).toDispatch();
            System.out.println("open:"+df.format(new Date()));
            Dispatch.call(doc, "SaveAs", target, wdFormatPDF);
            System.out.println("printPdf:"+df.format(new Date()));
            Dispatch.call(doc, "Close", new Variant(false));
            System.out.println("closed:"+df.format(new Date()));


            System.out.println("第三个");

            source="D:/wordTest/错题与提分练习.docx";
            target="D:/wordTest/xx3.pdf";
            doc = Dispatch.call(docs, "Open", source).toDispatch();
            System.out.println("open:"+df.format(new Date()));
            Dispatch.call(doc, "SaveAs", target, wdFormatPDF);
            System.out.println("printPdf:"+df.format(new Date()));
            Dispatch.call(doc, "Close", new Variant(false));
            System.out.println("closed:"+df.format(new Date()));

        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            if (app != null) {
                app.invoke("Quit", 0);
            }
            ComThread.Release();
        }
    }

}
