package com.four.king.kong.basic.utils;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p> Title(文件名): AESEncrypt.java </p>
 * <p> Description(描述): AES-128加密类 </p>
 * <p> Copyright(版权): Copyright (c) 2012 </p>
 * <p> Company(公司): xxx </p>
 * <p> CreateTime(生成日期)：2012-5-22 下午08:22:23 </p>
 * 
 * @author xxx
 * @version Tools_wad
 */
public class AES128Encrypt
{

    private static String ivParameter = "1234567890123456";
    /**
     * Decode password.
     * 
     * @param input password
     * @throws Exception
     *         Exception
     * @return password
     */
    public String decrypt(String input) throws Exception
        
    {
        return decrypt(input, createSecretKey());
    }

    /**
     * Decode password.(2012-9-8 zhangyan add,用户自行指定密钥)
     * 
     * @param input password
     * @param keyValue 密文
     * @throws Exception
     *         Exception
     * @return password
     */
    public String decrypt(String input, byte[] keyValue) throws Exception
    {
//        SecretKeySpec key = new SecretKeySpec(keyValue, "AES");
//        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        cipher.init(Cipher.DECRYPT_MODE, key);
//        byte[] data = Base64.decodeBase64(input);
//        byte[] decryptData = cipher.doFinal(data, 0, data.length);
//        String originalString = ByteUtils.toString(decryptData);

        SecretKeySpec skeySpec = new SecretKeySpec(keyValue, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] encrypted1 = new BASE64Decoder().decodeBuffer(input);//先用base64解密
        byte[] original = cipher.doFinal(encrypted1);
        String decode = new String(original, "utf-8");
        return decode;
    }

    /**
     * Encode password.
     * 
     * @param input password
     * @throws Exception
     *         Exception
     * @return password
     */
    public String encrypt(String input) throws Exception
    {
        return encrypt(input, createSecretKey());
    }

    /**
     * Encode password.(2012-9-8 zhangyan add,用户自行指定密钥)
     * 
     * @param input password
     * @param keyValue 密钥
     * @throws Exception
     *         Exception
     * @return password
     */
    public String encrypt(String input, byte[] keyValue) throws Exception
    {
//        SecretKeySpec key = new SecretKeySpec(keyValue, "AES");
//        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, key);
//        byte[] bs = cipher.doFinal(input.getBytes());
//        String encode = Base64.encodeBase64String(bs);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec skeySpec = new SecretKeySpec(keyValue, "AES");
        //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(input.getBytes("utf-8"));
        //此处使用BASE64做转码。
        String encode = new BASE64Encoder().encode(encrypted);
        return encode;
    }

    /**
     * Create AES-128 encrypt key. 2012-9-8
     * zhangyan add 此方法，目的是将AES-128加密的密钥修改为可读的字符串huawei_cdsf_pass
     * 
     * @return AES-128 key
     */
    public byte[] createSecretKey()
    {
        
        return new byte[] {
                104, 117, 97, 119, 101, 105, 95, 99, 100, 115, 102, 95, 112, 97, 115, 115 
                };
    }
}
