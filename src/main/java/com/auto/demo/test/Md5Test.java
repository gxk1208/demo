package com.auto.demo.test;

import java.security.MessageDigest;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/5/27 17:23
 */
public class Md5Test {
    /***

     * MD5加密 生成32位md5码

     *

     * @return 返回32位md5码

     */

    public static String md5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;

        try {
            md5 = MessageDigest.getInstance("MD5");

        } catch (Exception e) {
            System.out.println(e.toString());

            e.printStackTrace();

            return "";

        }

        byte[] byteArray = inStr.getBytes("UTF-8");

        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;

            if (val < 16) {
                hexValue.append("0");

            }

            hexValue.append(Integer.toHexString(val));

        }

        return hexValue.toString();

    }

    public static String string2MD5(String inStr) {
        MessageDigest md5 = null;

        try {
            md5 = MessageDigest.getInstance("MD5");

        } catch (Exception e) {
            System.out.println(e.toString());

            e.printStackTrace();

            return "";

        }

        char[] charArray = inStr.toCharArray();

        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)

            byteArray[i] = (byte) charArray[i];

        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;

            if (val < 16)

                hexValue.append("0");

            hexValue.append(Integer.toHexString(val));

        }

        return hexValue.toString();

    }

    /**

     * 加密

     */

    public static String convertMD5(String inStr) {
        char[] a = inStr.toCharArray();

        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ '↓');

        }

        String s = new String(a);

        return s;

    }

    /**

     * 解密算法 (执行一次加密)进行两次解密

     * @param str

     * @return

     */

    public static String md5Decode(String str) {
        return convertMD5(convertMD5(str));

    }

// 测试主函数

    public static void main(String args[]) {
        String s = new String("20210527");
        // a6aeb3ffa55fc7d664406af9c3bd0f1b

        System.out.println("原始：" + s);

        System.out.println("MD5后：" + string2MD5(s));

        System.out.println("加密的：" + convertMD5(s));

        System.out.println("解密的：" + convertMD5(convertMD5(s)));

    }

}
