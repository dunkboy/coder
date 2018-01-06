package com.four.king.kong.basic.utils;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 字节工具类
 */
public abstract class ByteUtils
{
    /**
     * 字符编码
     */
    public static final String CHARSET_NAME = "UTF-8";

    /**
     * 使用 BIG_ENDIAN
     *
     * @param content 内容
     * @param offset  起始位置
     * @return int 数量
     * @see java.nio.Bits#getIntB()
     * @see ByteOrder#BIG_ENDIAN
     */
    public static int getInt(byte[] content, int offset)
    {
        int value1 = (content[offset + 0] & 0xff) << 24;
        int value2 = (content[offset + 1] & 0xff) << 16;
        int value = value1 | value2 | ((content[offset + 2] & 0xff) << 8) | ((content[offset + 3] & 0xff) << 0);
        return value;
    }

    /**
     * 使用 BIG_ENDIAN
     *
     * @param content 内容
     * @param offset  起始位置
     * @return long 数量
     * @see java.nio.Bits#getLongB()
     * @see ByteOrder#BIG_ENDIAN
     */
    public static long getLong(byte[] content, int offset)
    {
        long value1 = ((long) content[offset + 0] & 0xff) << 56;
        long value2 = ((long) content[offset + 1] & 0xff) << 48;
        long value3 = ((long) content[offset + 2] & 0xff) << 40;
        long value4 = ((long) content[offset + 3] & 0xff) << 32;
        long value5 = ((long) content[offset + 4] & 0xff) << 24;
        long value6 = ((long) content[offset + 5] & 0xff) << 16;
        long value7 = ((long) content[offset + 6] & 0xff) << 8;
        long value8 = ((long) content[offset + 7] & 0xff) << 0;
        long value9 = value1 | value2;
        long value10 = value3 | value4;
        return value9 | value10 | value5 | value6 | value7 | value8;
    }

    /**
     * 获取字节
     *
     * @param content 内容
     * @return byte[] 字节数组
     */
    public static byte[] getBytes(String content)
    {
        if (StringUtils.isEmpty(content))
        {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        try
        {
            return content.getBytes(CHARSET_NAME);
        }
        catch (UnsupportedEncodingException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 使用 BIG_ENDIAN
     *
     * @param content 内容
     * @param offset  起始位置
     * @param value   值
     * @see java.nio.Bits#putIntB()
     * @see ByteOrder#BIG_ENDIAN
     */
    public static void put(byte[] content, int offset, int value)
    {
        content[offset + 0] = (byte) (value >> 24);
        content[offset + 1] = (byte) (value >> 16);
        content[offset + 2] = (byte) (value >> 8);
        content[offset + 3] = (byte) (value >> 0);
    }

    /**
     * 使用 BIG_ENDIAN
     *
     * @param content 内容
     * @param offset  起始位置
     * @param value   值
     * @see java.nio.Bits#putIntB()
     * @see ByteOrder#BIG_ENDIAN
     */
    public static void put(byte[] content, int offset, long value)
    {
        content[offset + 0] = (byte) (value >> 56);
        content[offset + 1] = (byte) (value >> 48);
        content[offset + 2] = (byte) (value >> 40);
        content[offset + 3] = (byte) (value >> 32);
        content[offset + 4] = (byte) (value >> 24);
        content[offset + 5] = (byte) (value >> 16);
        content[offset + 6] = (byte) (value >> 8);
        content[offset + 7] = (byte) (value >> 0);
    }

    /**
     * 获取socket地址
     *
     * @param buffer buffer
     * @return InetSocketAddress InetSocketAddress
     */
    public static InetSocketAddress getSocketAddress(ByteBuffer buffer)
    {
        return new InetSocketAddress(getInet4Address(buffer), buffer.getInt());
    }

    /**
     * getInet4Address
     *
     * @param buffer buffer
     * @return Inet4Address Inet4Address
     */
    public static Inet4Address getInet4Address(ByteBuffer buffer)
    {
        byte[] address = new byte[4];
        buffer.get(address);
        return (Inet4Address) getInetAddress(address);
    }

    /**
     * getInetAddress
     *
     * @param address 地址
     * @return InetAddress InetAddress
     */
    public static InetAddress getInetAddress(byte[] address)
    {
        try
        {
            return InetAddress.getByAddress(address);
        }
        catch (UnknownHostException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 转换为字符串
     *
     * @param content 内容
     * @return String 字符串
     */
    public static String toString(byte[] content)
    {
        return toString(content, 0, ArrayUtils.getLength(content));
    }

    /**
     * 转换为字符串
     *
     * @param content 内容
     * @param offset  开始位置
     * @param length  长度
     * @return String 字符串
     */
    public static String toString(byte[] content, int offset, int length)
    {
        try
        {
            return new String(content, offset, length, CHARSET_NAME);
        }
        catch (UnsupportedEncodingException e)
        {
            throw new IllegalArgumentException(e);
        }
    }
}
