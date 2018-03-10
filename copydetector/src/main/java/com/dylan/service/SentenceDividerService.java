package com.dylan.service;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

public class SentenceDividerService {
    public static List<String> divide(String str){

        str = CharMatcher.is('。').replaceFrom(str, "。@");
        str = CharMatcher.is('\n').replaceFrom(str, "\n@");
        str = CharMatcher.is('!').replaceFrom(str, "!@");
        str = CharMatcher.is('！').replaceFrom(str, "！@");
        str = CharMatcher.is(';').replaceFrom(str, ";@");
        str = CharMatcher.is('；').replaceFrom(str, "；@");
        str = CharMatcher.is('?').replaceFrom(str, "?@");
        str = CharMatcher.is('？').replaceFrom(str, "？@");

        //System.out.println(detectedDocx);
        return Lists.newArrayList(
                Splitter.on('@')
                        .omitEmptyStrings()
                        .split(str)
        );
    }
    @Test
    public void test(){
        System.out.println(divide("123。456"));
    }
}
