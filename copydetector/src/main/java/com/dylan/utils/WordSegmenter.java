package com.dylan.utils;

import org.apdplat.word.segmentation.Word;

import java.util.List;

public class WordSegmenter {
    public static String WordSeg (String input) {
        List<Word> words = org.apdplat.word.WordSegmenter.seg(input);
        String output = words.toString();
        return output;
    }
}
