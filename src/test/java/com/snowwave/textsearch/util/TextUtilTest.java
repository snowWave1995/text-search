package com.snowwave.textsearch.util;

import org.junit.Test;

/**
 * Created by zhangfuqiang on 2018/5/25.
 */
public class TextUtilTest {
    @Test
    public void getTextFromDoc() throws Exception {
        String file = new String("D:\\upload\\测试doc文档.doc");
        String res = TextUtil.getTextFromDoc(file);
        System.out.println(res);
    }

    @Test
    public void getTextFromDocx() throws Exception {
        String file = new String("D:\\upload\\测试doc文档.docx");
        String res = TextUtil.getTextFromDocx(file);
        System.out.println(res);
    }

    @Test
    public void getTextFromPDF() throws Exception {
        String file = new String("D:\\upload\\测试doc文档.pdf");
        String res = TextUtil.getTextFromPDF(file);
        System.out.println(res);
    }

    @Test
    public void getTextFromPPT() throws Exception {
        String file = new String("D:\\upload\\测试doc文档.ppt");
        String res = TextUtil.getTextFromPPT(file);
        System.out.println(res);
    }


    @Test
    public void readToString() throws Exception {
        String file = new String("D:\\upload\\测试doc文档.txt");
        String res = TextUtil.getTextFromTxt(file);
        System.out.println(res);
    }

}