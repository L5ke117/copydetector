package com.dylan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class FileUploadController {

    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public String fileUploadForm() {
        return "/teacher/uploadFile";
    }
    @ResponseBody
    @RequestMapping(value="/upload", method= RequestMethod.POST)
    public void fileUpload(MultipartHttpServletRequest request) throws IOException {
        //配置文件上传限制
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession()
                                                                                            .getServletContext());
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        commonsMultipartResolver.setMaxUploadSize(104857600L);
        commonsMultipartResolver.setMaxInMemorySize(40960);

        Iterator<String> fileNames = request.getFileNames();

        while(fileNames.hasNext()) {
            String fileName = fileNames.next();
            System.out.println("fileName: " + fileName);
            List<MultipartFile> fileList = request.getFiles(fileName);
            if(fileList.size() > 0) {
                Iterator<MultipartFile> fileIterator = fileList.iterator();
                while(fileIterator.hasNext()) {
                    MultipartFile multipartFile = fileIterator.next();
                    String originalFileName = multipartFile.getOriginalFilename();
                    System.out.println("originalFileName: " + originalFileName);
                    String path = "D:/testUpload/";
                    File dir = new File(path);
                    if(!dir.exists()) {
                        dir.mkdirs();
                    }
                    String filePath = path + originalFileName;
                    System.out.println("filePath: " + filePath);

                    File dest = new File(filePath);
                    if(!dest.exists()) {
                        multipartFile.transferTo(dest);
                    }
                    String contentType = multipartFile.getContentType();
                    System.out.println("contentType: " + contentType);
                    String name = multipartFile.getName();
                    System.out.println("name: "+ name);
                    long size = multipartFile.getSize();
                    System.out.println("size: " + size);
                    System.out.println("------------------------------------------------------");
                }
            }
        }
    }
}
