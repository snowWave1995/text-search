package com.snowwave.textsearch.service;

import com.snowwave.textsearch.util.TextUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Created by zhangfuqiang on 2018/1/26.
 */
@Service
public class FileService {


    /**
     * 从多种格式文档中读取内容到字符串中
     * @param file
     * @return
     * @throws IOException
     */
    public String getStringFromFile(MultipartFile file) throws Exception {

        //判断文件是否合法，通过文件后缀
        int dotPos = file.getOriginalFilename().lastIndexOf(".");
        if (dotPos < 0) {
            return "非法文件";
        }
        String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();

        //判断后缀
        if (!TextUtil.isFileAllowed(fileExt)) {
            return null;
        }

        String fileName = file.getOriginalFilename();
        Files.copy(file.getInputStream(), new File(TextUtil.FILE_DIR + fileName).toPath(),
                StandardCopyOption.REPLACE_EXISTING);
        String string = "";
         if (fileExt.equals("txt")) {
             string = TextUtil.getTextFromTxt(TextUtil.FILE_DIR + fileName);
         }else if (fileExt.equals("doc")){
             string = TextUtil.getTextFromDoc(TextUtil.FILE_DIR + fileName);
         }else if (fileExt.equals("docx")) {
             string = TextUtil.getTextFromDocx(TextUtil.FILE_DIR + fileName);
         } else if (fileExt.equals("pdf")) {
             string = TextUtil.getTextFromPDF(TextUtil.FILE_DIR + fileName);
         } else if (fileExt.equals("ppt")) {
             string = TextUtil.getTextFromPPT(TextUtil.FILE_DIR + fileName);
         }else {
             string = "当前不支持此类型";
         }
        return string;
    }

}
