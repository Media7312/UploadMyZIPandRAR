package com.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @Author: Media7312
 * @Description: URLGetUtil工具，获取文件存储绝对路径
 * @Date: Created in 10:17 2018/1/10
 */
public class URLGetUtil {
    private static final String UPLOAD_FILE_URL = "D:/UploadMyZIP/MyZIP/";
 /**
    *
    * @param：后缀名
    * @return： String：生成的文件名
    * @throws Exception
    */
    public static String  getName(String Suffix_name){
        String FileName = UUID.randomUUID().toString();
        String nowDate = UPLOAD_FILE_URL + FileName + Suffix_name;
        return nowDate;
    }




}
