package com.dylan.util;

import org.apdplat.word.analysis.JaccardTextSimilarity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TextSimilarity {
    public static double getWordSimilarityScore (String text1, String text2) {
        org.apdplat.word.analysis.TextSimilarity textSimilarity = new JaccardTextSimilarity();
        return textSimilarity.similarScore(text1, text2);
    }
    public static double getCosineSimilarityScore (String text1, String text2) {
        if (text1 != null && text1.trim().length() > 0 && text2 != null && text2.trim().length() > 0) {
            Map<Integer, int[]> charMap = new HashMap<Integer, int[]>();
            //char currentChar;
            //int charIndex;
            //将两个字符串中的中文字符以及出现的总数封装到charMap中
            for (int i = 0; i < text1.length(); i++) {
                char currentChar1 = text1.charAt(i);
                if(CharacterProcessor.isChinese(currentChar1)) {
                    int charIndex = CharacterProcessor.getGB2312Id(currentChar1);
                    if (charIndex != -1) {
                        int[] times = charMap.get(charIndex);
                        if (times != null && times.length == 2) {
                            times[0]++;
                        }
                        else {
                            times = new int[2];
                            times[0] = 1;
                            times[1] = 0;
                            charMap.put(charIndex, times);
                        }
                    }
                }
            }

            for (int i = 0; i < text2.length(); i++) {
                char currentChar2 = text2.charAt(i);
                if(CharacterProcessor.isChinese(currentChar2)) {
                    int charIndex = CharacterProcessor.getGB2312Id(currentChar2);
                    if (charIndex != -1) {
                        int[] times = charMap.get(charIndex);
                        if (times != null && times.length == 2) {
                            times[1]++;
                        }
                        else
                        {
                            times = new int[2];
                            times[0] = 0;
                            times[1] = 1;
                            charMap.put(charIndex, times);
                        }
                    }
                }
            }

            Iterator<Integer> iterator = charMap.keySet().iterator();
            double x = 0;
            double y = 0;
            double dotProduct = 0;
            while (iterator.hasNext()) {
                int[] temp = charMap.get(iterator.next());
                dotProduct += temp[0] * temp[1];
                x += temp[0] * temp[0];
                y += temp[1] * temp[1];
            }
            System.out.println("dotProduct: "+ Double.toString(dotProduct));
            System.out.println("x: " + Double.toString(x));
            System.out.println("y: " + Double.toString(y));
            return dotProduct / Math.sqrt(x * y);
        }
        else
            throw new NullPointerException("Null or empty text!");
    }
}
