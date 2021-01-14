package com.auto.demo.utils.ThreeDES科拓;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/1/5 11:49
 */
public class Test {

    public static void main(String[] args) {
        try {
            String desstr = desEncrypt("12354");
            String pstr =  desDecrypt(desstr);
            System.out.println("plainText:12354");
            System.out.println("Encode:"+desstr);
            System.out.println("Decode:"+pstr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private static BASE64Encoder base64 = new BASE64Encoder();
    private static String iv = "20210105";
    private static byte[] myIV = iv.getBytes();
    //private static byte[] myIV = { 50, 51, 52, 53, 54, 55, 56, 57 };
    //private static String strkey = "W9qPIzjaVGKUp7CKRk/qpCkg/SCMkQRu"; // 字节数必须是8的倍数
    // private static String strkey =  "01234567890123456789012345678912";
    private static String strkey = "971D0E8C9DD0E3CFEB167C4D        ";
    public static String desEncrypt(String input) throws Exception
    {

        BASE64Decoder base64d = new BASE64Decoder();
        DESedeKeySpec p8ksp = null;
        p8ksp = new DESedeKeySpec(base64d.decodeBuffer(strkey));
        Key key = null;
        key = SecretKeyFactory.getInstance("DESede").generateSecret(p8ksp);

        byte[] plainBytes = (byte[])null;
        Cipher cipher = null;
        byte[] cipherText = (byte[])null;

        plainBytes = input.getBytes("gb2312");
        cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        SecretKeySpec myKey = new SecretKeySpec(key.getEncoded(), "DESede");
        IvParameterSpec ivspec = new IvParameterSpec(myIV);
        cipher.init(1, myKey, ivspec);
        cipherText = cipher.doFinal(plainBytes);
        return removeBR(base64.encode(cipherText));

    }

    public static String desDecrypt(String cipherText) throws Exception
    {

        BASE64Decoder base64d = new BASE64Decoder();
        DESedeKeySpec p8ksp = null;
        p8ksp = new DESedeKeySpec(base64d.decodeBuffer(strkey));
        Key key = null;
        key = SecretKeyFactory.getInstance("DESede").generateSecret(p8ksp);

        Cipher cipher = null;
        byte[] inPut = base64d.decodeBuffer(cipherText);
        cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        SecretKeySpec myKey = new SecretKeySpec(key.getEncoded(), "DESede");
        IvParameterSpec ivspec = new IvParameterSpec(myIV);
        cipher.init(2, myKey, ivspec);
        byte[] output = cipher.doFinal(inPut);
        return new String(output, "UTF8");

    }

    private static String removeBR(String str) {
        StringBuffer sf = new StringBuffer(str);

        for (int i = 0; i < sf.length(); ++i)
        {
            if (sf.charAt(i) == 'n')
            {
                sf = sf.deleteCharAt(i);
            }
        }
        for (int i = 0; i < sf.length(); ++i) {
            if (sf.charAt(i) == 'r')
            {
                sf = sf.deleteCharAt(i);
            }
        }

        return sf.toString();
    }
}



