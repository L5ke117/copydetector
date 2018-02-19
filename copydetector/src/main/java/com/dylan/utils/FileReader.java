package com.dylan.utils;

import java.io.*;
import java.util.List;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import org.apdplat.word.analysis.SimHashPlusHammingDistanceTextSimilarity;
import org.apdplat.word.analysis.TextSimilarity;
import org.apdplat.word.segmentation.Word;
public class FileReader {
    public static String TxtReader(String filePath){
        StringBuilder result = new StringBuilder();
        try{
            String encoding = "GB2312";
            File file = new File(filePath);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file),encoding);
            BufferedReader br = new BufferedReader(isr);
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

    public static String DocReader(String filePath){
        try {
            //word 2003： 图片不会被读取
            File file =new File(filePath);
            InputStream is = new FileInputStream(file);
            WordExtractor ex = new WordExtractor(is);
            String docText = ex.getText();
            return docText;
        }catch (Exception e){
            e.printStackTrace();
            return "ERROR";
        }
    }

    public static String DocxReader(String filePath){
        try {
            //word 2007 图片不会被读取， 表格中的数据会被放在字符串的最后
            OPCPackage opcPackage = POIXMLDocument.openPackage(filePath);
            POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
            String docxText = extractor.getText();
            return docxText;

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    public static void main(String[] args) {
        String filePath1 = "C:/课题/第四节  资优生未来发展的跟踪系统的开发运用.docx";
        String filePath2 = "C:/课题/第六部分 校园信息化系统助力资优生培育.docx";
        String filePath3 = "C:/课题/第四节  资优生未来发展的跟踪系统的开发运用.doc";
        String filePath4 = "C:/课题/text.txt";
        //String text1 = DocxReader(filePath1);
        //String text2 = DocxReader(filePath2);
        //String text3 = DocReader(filePath3);
        String text4 = TxtReader(filePath4);
        //System.out.println("text1:" + text1);
        //System.out.println("text2:" + text2);
        //System.out.println("text3:" + text3);
        //System.out.println("text4:" + text4);
        String splitedText4 = WordSegmenter.WordSeg(text4);
        System.out.println(splitedText4);
    }
}
