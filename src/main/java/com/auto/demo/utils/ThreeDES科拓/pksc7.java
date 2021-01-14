
package com.auto.demo.utils.ThreeDES科拓;

import java.io.IOException;
import java.security.Security;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/1/5 8:44
 */

public class pksc7 {
    public static void main(String[] args) throws IOException {
/*        String str = des3EncodeECB("Key3Des","@##h586ellAAon你好");
        System.out.println(str);
        System.out.println(des3DecodeECB("Key3Des",str));*/
    }
    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }
    private static final String DES3 = "DESede";
}
