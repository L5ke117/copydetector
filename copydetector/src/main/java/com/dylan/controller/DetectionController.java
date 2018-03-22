package com.dylan.controller;

import com.dylan.service.SentenceDividerService;
import com.dylan.service.SimilarityDetectorService;
import com.dylan.util.FileReader;
import com.google.common.base.CharMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

@Controller
public class DetectionController {
    @RequestMapping("detect")
    public String uploadAndDetect(HttpServletRequest request, Model model)
            throws IllegalStateException, IOException {
        String detectedFileName = "";
        String comparedFileName = "";
        List<String> detectedList = new ArrayList<>();
        List<String> comparedList = new ArrayList<>();
        Map<Integer, String> detectedMap = new HashMap<>();
        Map<Integer, String> comparedMap = new HashMap<>();

        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            Iterator iter = multiRequest.getFileNames();


            if (iter.hasNext()) {
                String detectedString = new String();

                MultipartFile detectedFile = multiRequest.getFile(iter.next().toString());
                detectedFileName = detectedFile.getOriginalFilename();
                String path = "D:/testUpload/";     //先把上传的文件保存至本地
                //String path = "/var/lib/copydetector/files/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String filePath = path + detectedFileName;
                File dest = new File(filePath);
                if (!dest.exists()) {
                    detectedFile.transferTo(dest);
                }
                if (detectedFile.getOriginalFilename().contains(".docx")) {
                    detectedString = FileReader.DocxReader(filePath);
                } else if (detectedFile.getOriginalFilename().contains(".txt")) {
                    detectedString = FileReader.TxtReader(filePath);
                } else if (detectedFile.getOriginalFilename().contains(".doc")) {
                    detectedString = FileReader.DocReader(filePath);
                }
                detectedList = SentenceDividerService.divide(detectedString);
            }
            if (iter.hasNext()) {
                String comparedString = new String();
                MultipartFile comparedFile = multiRequest.getFile(iter.next().toString());
                comparedFileName = comparedFile.getOriginalFilename();
                String path = "D:/testUpload/";     //先把上传的文件保存至本地
                //String path = "/var/lib/copydetector/files/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String filePath = path + comparedFileName;
                File dest = new File(filePath);
                if (!dest.exists()) {
                    comparedFile.transferTo(dest);
                }

                if (comparedFile.getOriginalFilename().contains(".docx")) {
                    comparedString = FileReader.DocxReader(filePath);
                } else if (comparedFile.getOriginalFilename().contains(".txt")) {
                    comparedString = FileReader.TxtReader(filePath);
                } else if (comparedFile.getOriginalFilename().contains(".doc")) {
                    comparedString = FileReader.DocReader(filePath);
                }
                comparedList = SentenceDividerService.divide(comparedString);
            }

            final int[] i = {1};
            detectedList.forEach(x -> detectedMap.put(i[0]++, x));
            final int[] j = {1};
            comparedList.forEach(x -> comparedMap.put(j[0]++, x));

            //InitResultMaps initResultMaps = SimilarityDetection.initialInspection2(detectedMap, comparedMap);
            Map<Integer, Integer> suspectMap = SimilarityDetectorService.initialInspection(detectedMap, comparedMap);

            Map<Integer, Integer> duplicateMap = new IdentityHashMap<>();
            for (Map.Entry<Integer, Integer> m : suspectMap.entrySet()) {
                int key1 = m.getKey() - 128;
                int key2 = m.getValue();

                int publicSubstringLength = SimilarityDetectorService.secondInspection(detectedMap.get(key1), comparedMap.get(key2));
                if (publicSubstringLength >= 13) {
                    duplicateMap.put(key1 + 128, key2);
                }
            }

            List<Integer> detectedDuplicateList = new ArrayList<>();
            duplicateMap.forEach((x, y) -> detectedDuplicateList.add(x - 128));
            List<Integer> comparedDuplicateList = new ArrayList<>();
            duplicateMap.forEach((x, y) -> comparedDuplicateList.add(y));

            final String[] outPut1 = new String[1];
            detectedMap.forEach(
                    (x, y) -> {
                        y = CharMatcher.is('\n').replaceFrom(y, "<br>");
                        if (detectedDuplicateList.contains(x)) {
                            y = "<span class=\"bg-danger text-danger\" onMouseOut=\"this.className='bg-danger text-danger'\" onMouseOver=\"this.className='bg-primary text-primary'\">" + y + "</span>";
                        } else {
                            //y = "<font color=\"black\">" + y + "</color>";
                            y = "<span>" + y + "</span>";
                        }
                        outPut1[0] = outPut1[0] + y;
                    }
            );

            final String[] outPut2 = new String[1];
            comparedMap.forEach(
                    (x, y) -> {
                        y = CharMatcher.is('\n').replaceFrom(y, "<br>");
                        if (comparedDuplicateList.contains(x)) {
                            y = "<span class=\"bg-danger text-danger\" onMouseOut=\"this.className='bg-danger text-danger'\" onMouseOver=\"this.className='bg-primary text-primary'\">" + y + "</span>";
                        } else {
                            y = "<span>" + y + "</span>";
                        }
                        outPut2[0] = outPut2[0] + y;
                    }
            );
            model.addAttribute("file1", outPut1[0]);
            model.addAttribute("file2", outPut2[0]);

            double duplicateWordRate;
            final int[] fileLength = {0};
            detectedMap.forEach((x, y) -> fileLength[0] = fileLength[0] + y.length());
            final int[] duplicateLength = {0};
            detectedMap.forEach(
                    (x, y) -> {
                        if (detectedDuplicateList.contains(x)) {
                            duplicateLength[0] = duplicateLength[0] + y.length();
                        }
                    }
            );
            duplicateWordRate = 1.0 * duplicateLength[0] / fileLength[0];
            model.addAttribute("OriginalLength", fileLength[0] - duplicateLength[0]);
            model.addAttribute("duplicateLength", duplicateLength[0]);
            DecimalFormat df = new DecimalFormat("######0.00");
            model.addAttribute("duplicateWordRate", df.format(duplicateWordRate * 100));

        }
        return "result";
    }
}
