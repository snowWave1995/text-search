package com.snowwave.textsearch.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

/**
 * Created by zhangfuqiang on 2018/1/26.
 * 支持多种格式
 */
public class TextUtil {

    public static String FILE_DIR = "D:/upload/";
    public static String[] TEXT_FILE_EXTD = new String[] {"txt", "doc", "docx","pdf","ppt"};


    /**
     * 判断是否合法
     * @param ext
     * @return
     */
    public static boolean isFileAllowed(String ext) {
        for (String format:TEXT_FILE_EXTD) {
            if (ext.toLowerCase().equals(format)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 用来读取txt文件的方法
     * @param fileName
     * @return
     */
    public static String getTextFromTxt(String fileName) {
        String encoding = "gbk";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 用来读取doc文件的方法
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String getTextFromDoc(String filePath) throws Exception{

        FileInputStream fis = new FileInputStream(new File(filePath));
        WordExtractor extractor = new WordExtractor(fis);

        return extractor.getText();

    }

    /**
     * 用来读取docx文件
     * @param filePath
     * @return
     * @throws IOException
     * @throws Exception
     */
    @SuppressWarnings("resource")
    public static String getTextFromDocx(String filePath) throws IOException {
        FileInputStream in = new FileInputStream(filePath);
        XWPFDocument doc = new XWPFDocument(in);
        XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
        String text = extractor.getText();
        in.close();
        return text;
    }

    /**
     * 用来读取pdf文件
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String getTextFromPDF(String filePath) throws IOException{
        File input = new File(filePath);
        PDDocument pd = PDDocument.load(input);
        PDFTextStripper stripper = new PDFTextStripper();
        String res = stripper.getText(pd);
        pd.close();
        return res;
    }

    /**
     * 用来读取ppt文件
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String getTextFromPPT( String filePath) throws IOException{
        FileInputStream in = new FileInputStream(filePath);
        PowerPointExtractor extractor = new PowerPointExtractor(in);
        String content = extractor.getText();
        extractor.close();
        return content;
    }


}
