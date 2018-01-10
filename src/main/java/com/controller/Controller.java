package com.controller;

import com.Util.URLGetUtil;
import com.Util.UnZipOrRarUtil;
import com.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: Media7312
 * @Description:
 * @Date: Created in 9:54 2018/1/10
 */
@org.springframework.stereotype.Controller
public class Controller {
    /**
     *@Description: 文件后缀名判定，前端小哥来写吧！
     *
     */
    private static final String [] FILE_ITEM_A = {"rar","zip"};
    private static final String [] FILE_ITEM_B = {"word","txt","gif"};
    @Autowired
    private UserService userService;
    @RequestMapping("/test")
    public String TestA(Model model) throws Exception{

        model.addAttribute("userName",userService.getUserName());
        return "test";
    }
     /**
        * 文件上传到对应文件夹，文件夹路径可再com.Util.URLGetUtil中修改
        * @param： 请求
        * @return： 返回结果
        * @throws Exception
        */
    @RequestMapping("/upload")
    public String TestB(HttpServletRequest request)throws IOException{
        /**
         *将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
         */
        long  startTime=System.currentTimeMillis();
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;

            Iterator iterator = multipartHttpServletRequest.getFileNames();
            while (iterator.hasNext()){
                MultipartFile file = multipartHttpServletRequest.getFile(iterator.next().toString());
                String FileName = file.getOriginalFilename();

                if (file != null){
                    String path = URLGetUtil.getName(FileName);
                    file.transferTo(new File(path));
                    /**
                     * UnZipOrRarUtil.UnZip(path);
                     * 可以解压ZIP  偷懒不做了！
                     */
                    UnZipOrRarUtil.UnRar(path);
                }

            }
        }
        long  endTime=System.currentTimeMillis();
        System.out.println("运行时间："+String.valueOf(endTime-startTime)+"ms");
        return "succeed";
    }
     /**
        *
        * @param：多文件 MultipartFile[] multipartFiles
        * @return： 返回结果
        * @throws Exception
        */
    @RequestMapping("/multiUpload")
    public String MultiUpload(@RequestParam("uploadFile")MultipartFile[] multipartFiles,HttpServletRequest request) throws IOException{
        MultipartFile multipartFile = null;
        for (int i = 0; i < multipartFiles.length; i ++){
            multipartFile = multipartFiles[i];
            String FolderName = multipartFile.getOriginalFilename();
            if (multipartFile != null){
                String path = URLGetUtil.getName(FolderName);
                multipartFile.transferTo(new File(path));
            }
        }
        return "succeed";
    }

}
