package com.auto.demo.dto;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/1/5 11:36
 */
public class pkcs5 {
    public static void main(String[] args) throws Exception {
        String s = Encrypt3DesCBC("{\"id\":1}");
        System.out.println(s);
    }
    private static final String KEY_STRING="971D0E8C9DD0E3CFEB167C4D      ";
    private static final byte[] KEY_IV="20210105".getBytes();//加密IV偏移量，必须为8为字节
    private static final String KEY_ALGORITHM = "DESede";//3DES加密算法
    private static final String PADDING_PATTERN="DESede/CBC/PKCS5Padding";//填充模式

    /**
     * 3Des CBC 模式加密
     * @param data 明文
     * @return Base64编码字符串密文
     * @throws Exception
     */
    public static String Encrypt3DesCBC(String data)throws Exception{
        Cipher cipher = Cipher.getInstance(PADDING_PATTERN);
        IvParameterSpec ips = new IvParameterSpec(KEY_IV);
        cipher.init(Cipher.ENCRYPT_MODE, get3DesKey(), ips);
        byte[] bOut = cipher.doFinal(data.getBytes());
        return new BASE64Encoder().encode(bOut);
    }

    public static Key get3DesKey()throws Exception{
        //将keyString从Base64编码字符串转为原始正常的byte[]
        byte[] key= new BASE64Decoder().decodeBuffer(KEY_STRING);
        //实例化DES密钥规则
        DESedeKeySpec spec = new DESedeKeySpec(key);
        //实例化3DES（desede）密钥工厂
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        //生成秘钥
        return keyfactory.generateSecret(spec);
    }
}


