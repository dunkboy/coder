package com.four.king.kong.basic.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.support.PropertiesLoaderSupport;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Method;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * <p>Description: props工具类.</p>
 * <p>Copyright: Copyright(c) 2017.</p>
 * <p>Company: xxx.</p>
 * <p>CreateTime: 2017/1/10.</p>
 *
 * @author xxx
 * @version 1.0
 */
public class PropsUtils
{
    /**
     * webAppRoot
     */
    public static final String WEB_APP_ROOT = "webAppRoot";
    /**
     * 获取方法对象
     */
    private static Method mp = ReflectionUtils.findMethod(PropertiesLoaderSupport.class, "mergeProperties");
    /**
     * 初始化PROPS
     */
    private static Properties props = new Properties();

    /**
     * 获取值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static String get(String key, String defaultValue)
    {
        return props.getProperty(key, defaultValue);
    }

    /**
     * 获取值
     *
     * @param key 值
     * @return 值
     */
    public static String get(String key)
    {
        return get(key, "");
    }

    /**
     * 获取值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 是 否
     */
    public static boolean get(String key, boolean defaultValue)
    {
        try
        {
            return Boolean.valueOf(props.getProperty(key));
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    /**
     * 替换方法
     *
     * @param text   文本
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 字符串
     */
    public static String replace(String text, final String prefix, final String suffix)
    {
        return StringUtils.replace(text, prefix, suffix, new IStringReplaceProcess()
        {
            @Override
            public String doReplace(String key, StringBuffer src, int prefixIndex, int suffixIndex)
            {
                String value = props.getProperty(key);
                return StringUtils.isEmpty(value) ? prefix + key + suffix : value;
            }
        });
    }

    /**
     * <p>Description: 解析类.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/10.</p>
     *
     * @author xxx
     * @version 1.0
     */
    public static final class Resolver implements ApplicationContextAware
    {
        /**
         * 设置应用上下文
         *
         * @param ac 上下文对象
         */
        @Override
        public void setApplicationContext(ApplicationContext ac)
        {
            props.clear();
            for (Entry<String, PropertiesLoaderSupport> entry : ac.getBeansOfType(PropertiesLoaderSupport.class)
                    .entrySet())
            {
                ReflectionUtils.makeAccessible(mp);
                Properties pro = (Properties) ReflectionUtils.invokeMethod(mp, entry.getValue());
                props.putAll(pro);
            }
            if (ac instanceof WebApplicationContext)
            {
                WebApplicationContext wac = (WebApplicationContext) ac;
                props.put(WEB_APP_ROOT, wac.getServletContext().getRealPath(""));
            }
        }
    }
}
