package com.four.king.kong.basic;

import com.four.king.kong.basic.utils.AES128Encrypt;

/**
 * <p>Title: ChenTest</p>
 * <p>Description: ChenTest</p>
 * <p>Copyright:Copyright(c) xxx 2017</p>
 * <p>Company: xxx</p>
 * <p>CreateTime: 2018/1/5 21:03</p>
 *
 * @author xxx
 * @version 1.0
 **/
public class ChenTest
{
    public static void main(String[] args) throws Exception
    {
        //mysql数据库密码加密
        testMysqlPasswordEncrypt("123456");
    }

    public static void testMysqlPasswordEncrypt(String password) throws Exception
    {
        AES128Encrypt aes = new AES128Encrypt();
        String encrypt = aes.encrypt(password);
        System.out.println(encrypt);
    }


}
