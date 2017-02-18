package com.neo.duan.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author : neo.duan
 * @date : 	 2016/9/20
 * @desc : 请描述这个文件
 */
public class SHA {

    public static String getSHA(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            return byteArrayToHexString(mdTemp.digest());
        } catch (Exception e) {
            return null;
        }
    }

    public static String byteArrayToHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result +=
                    Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }
}
