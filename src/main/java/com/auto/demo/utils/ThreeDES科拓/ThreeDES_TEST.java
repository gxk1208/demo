package com.auto.demo.utils.ThreeDES科拓;

import com.alibaba.fastjson.JSON;
import com.auto.demo.dto.IdTest;
import springfox.documentation.spring.web.json.Json;

import java.net.URLEncoder;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/1/4 18:49
 */
public class ThreeDES_TEST {
    public static void main(String[] args) throws Exception {
        final String key = "971D0E8C9DD0E3CFEB167C4D        ";
        // 加密流程
        IdTest idTest = new IdTest();
        idTest.setId(1);
        String s = JSON.toJSONString(idTest);
        ThreeDES threeDES = new ThreeDES();
        String telePhone_encrypt = "";
        telePhone_encrypt = threeDES.encryptDESCBC(URLEncoder.encode(s, "gb2312"), key);
        System.out.println(telePhone_encrypt);// nWRVeJuoCrs8a+Ajn/3S8g==

        // 解密流程
        String tele_decrypt = threeDES.decryptDESCBC(telePhone_encrypt, key);
        System.out.println("模拟代码解密:" + tele_decrypt);
    }
}
