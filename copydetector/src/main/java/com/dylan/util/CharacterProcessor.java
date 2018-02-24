package com.dylan.util;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

public class CharacterProcessor {
    public static short getGB2312Id (char ch) {
        try {
            byte[] buffer = Character.toString(ch).getBytes("GB2312");
            if (buffer.length != 2) {
                return -1; // 正常情况下buffer应该是两个字节，否则说明ch不属于GB2312编码，故返回'-1'，此时说明不认识该字符
            }
            int b0 = (int) (buffer[0] & 0x0FF) -161;  // 编码从A1开始，因此减去0xA1=161
            int b1 = (int) (buffer[1] & 0x0FF) -161;
            return (short) (b0 * 94 + b1); // 第一个字符和最后一个字符没有汉字，因此每个区只收16*6-2=94个汉字
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static boolean isChinese (char ch) {
        return (ch >= 0x4E00 && ch <= 0x9FA5);
    }

    @Test
    public void testIsChinese () {
        System.out.println(isChinese('哈'));
    }
    @Test
    public void testGetGB2312Id () {
        System.out.println(getGB2312Id('你'));
    }
}
