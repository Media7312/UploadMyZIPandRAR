package com.Util;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.cs.ext.GBK;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @Author: Media7312
 * @Description: ZIP、RAR解压工具类
 * @Date: Created in 14:34 2018/1/10
 */
public class UnZipOrRarUtil {
    /**
     * 解压路径 ZIP 备注：注意最后的 /，不可缺少
     */
    private static final String UNZIP_URL = "D:/UploadMyZIP/File/";
    /**
     * 解压路径 RAR备注：注意最后的 /，不可缺少
     */
    private static final String UNRAR_URL = "D:/UploadMyZIP/File/";
     /**
        * 解压ZIP
        * @param：打开文件->绝对路径
        * @throws Exception
        */
    public static synchronized void UnZip(String tarFileName)throws IOException{
        File zipFile = new File(tarFileName);
        ZipFile zip = new ZipFile(zipFile,Charset.forName("GBK"));//解决中文文件夹乱码
        String name = zip.getName().substring(zip.getName().lastIndexOf('\\')+1, zip.getName().lastIndexOf('.'));

        File pathFile = new File(UNZIP_URL+name);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);
            String outPath = (UNZIP_URL + name +"/"+ zipEntryName);

            // 判断路径是否存在,不存在则创建文件路径
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if (!file.exists()) {
                file.mkdirs();
            }
            // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if (new File(outPath).isDirectory()) {
                continue;
            }
            // 输出文件路径信息
//          System.out.println(outPath);
            FileOutputStream out = new FileOutputStream(outPath);
            byte[] buf1 = new byte[2048];
            int len;
            while ((len = in.read(buf1)) > 0) {
                out.write(buf1, 0, len);
            }
            in.close();
            out.close();
        }
        System.out.println("******************解压完毕********************");

    }
     /**
        * 解压RAR文件
        * @param：文件绝对路径
        * @throws Exception
        */
    public static synchronized void UnRar(String tarFileName)throws IOException{
        File zip = new File(tarFileName);
        String name = zip.getName().substring(zip.getName().lastIndexOf('\\')+1, zip.getName().lastIndexOf('.'));
        File dstDiretory = new File(UNRAR_URL + name);
        if (!dstDiretory.exists()) {// 目标目录不存在时，创建该文件夹
            dstDiretory.mkdirs();
        }
        Archive a = null;
        try {
            a = new Archive(new File(tarFileName));
            if (a != null) {
                //a.getMainHeader().print(); // 打印文件信息.
                FileHeader fh = a.nextFileHeader();
                while (fh != null) {
                    //防止文件名中文乱码问题的处理

                    String fileName = fh.getFileNameW().isEmpty()?fh.getFileNameString():fh.getFileNameW();
                    if (fh.isDirectory()) { // 文件夹
                        File fol = new File(UNRAR_URL + File.separator + fileName);
                        fol.mkdirs();
                    } else { // 文件
                        File out = new File(UNRAR_URL + File.separator + fileName.trim());
                        try {
                            if (!out.exists()) {
                                if (!out.getParentFile().exists()) {// 相对路径可能多级，可能需要创建父目录.
                                    out.getParentFile().mkdirs();
                                }
                                out.createNewFile();
                            }
                            FileOutputStream os = new FileOutputStream(out);
                            a.extractFile(fh, os);
                            os.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    fh = a.nextFileHeader();
                }
                a.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("解压完毕");
    }
}
