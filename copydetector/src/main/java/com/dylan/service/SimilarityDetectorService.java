package com.dylan.service;

import com.google.common.base.CharMatcher;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import com.dylan.entity.model.InitResultMaps;
import org.junit.Test;

public class SimilarityDetectorService {
    public List<String> detectedSentenceList;

    public List<String> referenceSentenceList;


    public static Map<Integer, Integer> initialInspection(Map<Integer, String> detectedMap, Map<Integer, String> referenceMap){
        /**
         * 怀疑的必须全部加入
         */
        Map<Integer, Integer> suspectMap = new IdentityHashMap<>();

        detectedMap.forEach(
                (x, y) -> {
                    y = CharMatcher.invisible().removeFrom(y);
                    y = CharMatcher.ascii().removeFrom(y);
                    y = CharMatcher.anyOf(",.;?!，。；？！").removeFrom(y);
                });
        referenceMap.forEach(
                (x, y) -> {
                    y = CharMatcher.invisible().removeFrom(y);
                    y = CharMatcher.ascii().removeFrom(y);
                    y = CharMatcher.anyOf(",.;?!，。；？！").removeFrom(y);
                });
        for (Map.Entry<Integer, String> m : detectedMap.entrySet()) {
            if (m.getValue().equals("")){
                continue;
            }
            double frequency;
            double sameWords = 0;
            for (Map.Entry<Integer, String> n : referenceMap.entrySet()) {
                if (n.getValue().equals("")){
                    continue;
                }
                String detectedSentence = m.getValue();
                String referenceSentence = n.getValue();
                for (int i = 0 ; i < detectedSentence.length() ; i++){
                    for (int j = 0 ; j < referenceSentence.length() ; j++){
                        if (detectedSentence.charAt(i) == referenceSentence.charAt(j)) {
                            sameWords++;
                            break;
                        }
                    }
                }
                frequency = sameWords/detectedSentence.length();
                if (frequency >= 0.75 || sameWords >= 15) {
                    suspectMap.put(m.getKey()+128, n.getKey());
                }
                /**
                 *
                 */
                sameWords = 0;
            }
        }
        return suspectMap;
    }


    public static InitResultMaps initialInspection2(Map<Integer, String> detectedMap, Map<Integer, String> referenceMap){
        /**
         * 怀疑的必须全部加入
         */
        Map<Integer, Integer> suspectMap = new IdentityHashMap<>();
        Map<Integer, Integer> duplicateMap = new IdentityHashMap<>();

        detectedMap.forEach(
                (x, y) -> {
                    y = CharMatcher.invisible().removeFrom(y);
                    y = CharMatcher.ascii().removeFrom(y);
                    y = CharMatcher.anyOf(",.;?!，。；？！").removeFrom(y);
                });
        referenceMap.forEach(
                (x, y) -> {
                    y = CharMatcher.invisible().removeFrom(y);
                    y = CharMatcher.ascii().removeFrom(y);
                    y = CharMatcher.anyOf(",.;?!，。；？！").removeFrom(y);
                });
        for (Map.Entry<Integer, String> m : detectedMap.entrySet()) {
            if (m.getValue().equals("")){
                continue;
            }
            double frequency;
            double sameWords = 0;
            for (Map.Entry<Integer, String> n : referenceMap.entrySet()) {
                if (n.getValue().equals("")){
                    continue;
                }
                String detectedSentence = m.getValue();
                String referenceSentence = n.getValue();
                for (int i = 0 ; i < detectedSentence.length() ; i++){
                    for (int j = 0 ; j < referenceSentence.length() ; j++){
                        if (detectedSentence.charAt(i) == referenceSentence.charAt(j)) {
                            sameWords++;
                            break;
                        }
                    }
                }
                frequency = sameWords/detectedSentence.length();
                if (frequency >= 0.75) {
                    duplicateMap.put(m.getKey()+128, n.getKey());
                }
                if (frequency < 0.75 || sameWords >= 15) {
                    suspectMap.put(m.getKey()+128, n.getKey());
                }
                /**
                 *
                 */
                sameWords = 0;
            }
        }
        InitResultMaps initResultMaps = new InitResultMaps();
        initResultMaps.suspectMap = suspectMap;
        initResultMaps.duplicateMap = duplicateMap;
        return initResultMaps;
    }



    public static int secondInspection(String x, String y){
        x = CharMatcher.invisible().removeFrom(x);
        x = CharMatcher.anyOf(",.;?!，。；？！").removeFrom(x);
        y = CharMatcher.invisible().removeFrom(y);
        y = CharMatcher.anyOf(",.;?!，。；？！").removeFrom(y);
        return getLCString(x.toCharArray(), y.toCharArray());
    }


    public static int getLCString(char[] str1, char[] str2) {
        int i, j;
        int len1, len2;
        len1 = str1.length;
        len2 = str2.length;
        int maxLen = len1 > len2 ? len1 : len2;
        int[] max = new int[maxLen];
        int[] maxIndex = new int[maxLen];
        int[] c = new int[maxLen]; // 记录对角线上的相等值的个数

        for (i = 0; i < len2; i++) {
            for (j = len1 - 1; j >= 0; j--) {
                if (str2[i] == str1[j]) {
                    if ((i == 0) || (j == 0))
                        c[j] = 1;
                    else
                        c[j] = c[j - 1] + 1;
                } else {
                    c[j] = 0;
                }

                if (c[j] > max[0]) { // 如果是大于那暂时只有一个是最长的,而且要把后面的清0;
                    max[0] = c[j]; // 记录对角线元素的最大值，之后在遍历时用作提取子串的长度
                    maxIndex[0] = j; // 记录对角线元素最大值的位置

                    for (int k = 1; k < maxLen; k++) {
                        max[k] = 0;
                        maxIndex[k] = 0;
                    }
                } else if (c[j] == max[0]) { // 有多个是相同长度的子串
                    for (int k = 1; k < maxLen; k++) {
                        if (max[k] == 0) {
                            max[k] = c[j];
                            maxIndex[k] = j;
                            break; // 在后面加一个就要退出循环了
                        }

                    }
                }
            }
        }
        if (max.length > 0)
            return max[0];
        else
            return 0;
    }
    @Test
    public void test1() {

        String str1 = new String("123456abcd567");
        String str2 = new String("234dddabc45678");
        // String str1 = new String("aab12345678cde");
        // String str2 = new String("ab1234yb1234567");
        int c = getLCString(str1.toCharArray(), str2.toCharArray());
        System.out.println(c);
    }
}
