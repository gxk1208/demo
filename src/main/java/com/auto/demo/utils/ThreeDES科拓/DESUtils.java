package com.auto.demo.utils.ThreeDES科拓;

import com.alibaba.fastjson.JSON;
import com.auto.demo.dto.IdTest;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.tomcat.util.codec.binary.Base64;
import java.security.Key;
import java.security.Security;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/1/5 8:27
 */


//  3DES CBC  PKCS7Padding(5和它一样) base64 gb2312编码

public class DESUtils {
    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public static void main(String[] args) throws Exception {

        String strKey = "971D0E8C9DD0E3CFEB167C4D";
        byte[] key=new BASE64Decoder().decodeBuffer(strKey);
        key = strKey.getBytes("gb2312");
        String iv = "20210105";
        byte[] keyiv = iv.getBytes("gb2312");
        IdTest idTest = new IdTest();
        idTest.setId(1);
        String s = JSON.toJSONString(idTest);
s = "{\n" +
        "    \"certificateType\": 1,\n" +
        "    \"contactInfo\": \"17660687829\",\n" +
        "    \"houseId\": \"2602\",\n" +
        "    \"name\": \"孙萌萌\",\n" +
        "    \"projectId\": \"30\",\n" +
        "    \"sex\": 0,\n" +
        "    \"userType\": 1\n" +
        "}";
        byte[] data = s.getBytes("gb2312");


        System.out.println("CBC加密解密");
        byte[] str5 = des3EncodeCBC(key, keyiv, data);
        byte[] str6 = des3DecodeCBC(key, keyiv, str5);
        System.out.println(Base64.encodeBase64String(str5));
        System.out.println(new BASE64Encoder().encode(str5));
        System.out.println(new String(str6, "gb2312"));
    }

    public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data)
            throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS7Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }

    public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data)
            throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS7Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }
}
