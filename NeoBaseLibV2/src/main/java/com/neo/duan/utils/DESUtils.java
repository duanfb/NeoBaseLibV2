package com.neo.duan.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;


/**
 * DES 加解密的算法
 * 加密的key必须为8位或者8的倍数，否则在java加密过程中会报错
 *
 */
public class DESUtils {
    private static final String ENCODING = "UTF8";

    /**
     * 解密
     *
     * @return
     * @throws Exception
     */
    public static String decrypt(String key, String data) throws Exception {
        byte[] bytesrc = convertHexString(data);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(ENCODING));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes(ENCODING));

        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

        byte[] retByte = cipher.doFinal(bytesrc);
        return new String(retByte, ENCODING);
    }

    /**
     * 加密
     *
     * @return
     * @throws Exception
     */
    public static String encrypt(String key, String data) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(ENCODING));

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes(ENCODING));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

        byte[] buf = cipher.doFinal(data.getBytes(ENCODING));

        return toHexString(buf).toUpperCase();
    }

    /**
     * 字符串转换为16进制数组
     *
     * @param ss
     * @return
     */
    public static byte[] convertHexString(String ss) {
        byte digest[] = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }

        return digest;
    }

    /**
     * 16进制数组转换为字符串
     *
     * @param b
     * @return
     */
    public static String toHexString(byte b[]) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2)
                plainText = "0" + plainText;
            hexString.append(plainText);
        }

        return hexString.toString();
    }
}
