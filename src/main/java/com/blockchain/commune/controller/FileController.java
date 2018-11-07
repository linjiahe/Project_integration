package com.blockchain.commune.controller;




import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.utils.IdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by chijr on 16/12/4.
 */

@Controller
@Api(value = "文件上传", description = "图片和文件上传管理都在这里")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
public class FileController {
    @Value("${web.upload-path}")
    private String uploadPath;

    @RequestMapping(value = "/filemanager/path", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "路径测试")
    public String doPath(@RequestBody String body) {

        Map map = new HashMap();
        map.put("uploadPath", this.uploadPath);

        return ResponseHelper.successFormat(map);


    }

    @RequestMapping(value = "/fileupload", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "单文件上传")
    public String handleFileUpload(HttpServletRequest req,
                                   @RequestParam(required = false) String filename,
                                   @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseHelper.errorException(510, "文件为空");
        }
        try {
            long s = file.getBytes().length;
            double m = s / 1024 / 1024;
            if (m > 5) {
                return ResponseHelper.errorException(510, "图片大于5MB");
            }
            if (StringUtils.isEmpty(filename)) {
                filename = file.getOriginalFilename();
            }
            String filePath = this.remoteFilePath(file);

            System.out.print("path:" + filePath);

            HashMap<String, Object> mm = new HashMap<String, Object>();

            int pos = req.getRequestURL().lastIndexOf("/");

            String imgUrlPath = req.getRequestURL().substring(0, pos);

            imgUrlPath = imgUrlPath + filePath;


            mm.put("filename", imgUrlPath);
            return ResponseHelper.successFormat(mm);


        } catch (Exception e) {

            return ResponseHelper.errorException(511, "文件错误" + e.getMessage());
        }
    }


    @RequestMapping(value = "/multiupload", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "多文件件上传,最多5个")
    public String handleMultiFileUpload(HttpServletRequest req,
                                        @RequestParam(required = false) MultipartFile file1,
                                        @RequestParam(required = false) MultipartFile file2,
                                        @RequestParam(required = false) MultipartFile file3,
                                        @RequestParam(required = false) MultipartFile file4,
                                        @RequestParam(required = false) MultipartFile file5
    ) {


        List<String> imgPathList = new ArrayList<String>();

        String imgUrlPath = req.getRequestURL().toString();

        int pos = imgUrlPath.lastIndexOf("/");

        imgUrlPath = imgUrlPath.substring(0, pos);

        try {
            if (file1 != null && !file1.isEmpty()) {
                long s1 = file1.getBytes().length;
                double m1 = s1 / 1024 / 1024;
                if (m1 > 5) {
                    return ResponseHelper.errorException(510, "图片1大于5MB");
                }
                String path = this.remoteFilePath(file1);

                if (path != null) {
                    imgPathList.add(imgUrlPath + path);
                }

            }

            if (file2 != null && !file2.isEmpty()) {

                long s2 = file2.getBytes().length;
                double m2 = s2 / 1024 / 1024;
                if (m2 > 5) {
                    return ResponseHelper.errorException(510, "图片2大于5MB");
                }
                String path = this.remoteFilePath(file2);

                if (path != null) {
                    imgPathList.add(imgUrlPath + path);
                }

            }
            if (file3 != null && !file3.isEmpty()) {

                long s3 = file3.getBytes().length;
                double m3 = s3 / 1024 / 1024;
                if (m3 > 5) {
                    return ResponseHelper.errorException(510, "图片3大于5MB");
                }
                String path = this.remoteFilePath(file3);

                if (path != null) {
                    imgPathList.add(imgUrlPath + path);
                }

            }
            if (file4 != null && !file4.isEmpty()) {

                long s4 = file4.getBytes().length;
                double m4 = s4 / 1024 / 1024;
                if (m4 > 5) {
                    return ResponseHelper.errorException(510, "图片4大于5MB");
                }
                String path = this.remoteFilePath(file4);

                if (path != null) {
                    imgPathList.add(imgUrlPath + path);
                }

            }
            if (file5 != null && !file5.isEmpty()) {

                long s5 = file5.getBytes().length;
                double m5 = s5 / 1024 / 1024;
                if (m5 > 5) {
                    return ResponseHelper.errorException(510, "图片5大于5MB");
                }
                String path = this.remoteFilePath(file5);

                if (path != null) {
                    imgPathList.add(imgUrlPath + path);
                }

            }
        } catch (Exception e) {
            return ResponseHelper.errorException(511, "文件错误" + e.getMessage());
        }

        if (imgPathList.size() == 0) {


            return ResponseHelper.errorException(404, "没有找到图片文件");
        }

        HashMap<String, Object> mm = new HashMap<String, Object>();
        mm.put("urllist", imgPathList);
        return ResponseHelper.successFormat(mm);


    }


    private String fileUrlPath(MultipartFile file) {


        String filename = file.getOriginalFilename();

        int pos = filename.lastIndexOf(".");
        String extName = "";

        if (pos != -1) {

            extName = filename.substring(pos + 1);

        }


        String newFileName = IdUtil.getFileName() + "." + extName;

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

        String dateString = df.format(new Date());

        String newFilePath = "/upload/" + dateString + "/" + newFileName;

        return newFilePath;

    }


    //生成远程的文件名和路径
    private String remoteFilePath(MultipartFile file) {


        String filename = file.getOriginalFilename();

        int pos = filename.lastIndexOf(".");
        String extName = "";

        if (pos != -1) {

            extName = filename.substring(pos + 1);

        }

        String newFileName = IdUtil.getFileName() + "." + extName;

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

        String dateString = df.format(new Date());

        String newFilePath = this.uploadPath + "/upload/" + dateString + "/";


        File checkFile = new File(newFilePath);
        if (!checkFile.exists() && !checkFile.isDirectory()) {

            checkFile.mkdirs();
        }

        String filePath = newFilePath + newFileName;


        byte[] bytes = new byte[0];
        try {

            bytes = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
            stream.write(bytes);
            stream.close();
//            String iconFilePath = newFilePath + "icon_" + newFileName;
//            ThumbnailatorUtils.ImgThumb(filePath, iconFilePath, 375, 375 * 5);
//
//            String smallFilePath = newFilePath + "small_" + newFileName;
//            ThumbnailatorUtils.ImgThumb(filePath, smallFilePath, 750, 750 * 5);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return "/upload/" + dateString + "/" + newFileName;


    }


    private String saveToFile(String prefixPath, MultipartFile file) {


        try {


            String newFileNameWithPath = this.remoteFilePath(file);


            byte[] bytes = file.getBytes();


            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(newFileNameWithPath)));
            stream.write(bytes);
            stream.close();

            return prefixPath + "/" + this.fileUrlPath(file);


        } catch (Exception e) {

            return null;
        }


    }


}
