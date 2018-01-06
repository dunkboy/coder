package com.four.king.kong.basic.utils;

/**
 * <p>Description: 字符串替换操作接口.</p>
 * <p>Copyright: Copyright(c) 2017.</p>
 * <p>Company: xxx.</p>
 * <p>CreateTime: 2017/1/10.</p>
 *
 * @author xxx
 * @version 1.0
 */
public interface IStringReplaceProcess
{

    /**
     * <p>Description: 替换.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/10.</p>
     *
     * @param key         替换关键字
     * @param src         原字符串
     * @param prefixIndex 前缀关键字
     * @param suffixIndex 后缀关键字
     * @return string
     * @author xxx
     * @version 1.0
     */
    String doReplace(String key, StringBuffer src, int prefixIndex, int suffixIndex);
}
