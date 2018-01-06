package com.four.king.kong.basic.utils;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Description: 时间工具类.</p>
 * <p>Copyright: Copyright(c) 2017.</p>
 * <p>Company: xxx.</p>
 * <p>CreateTime: 2017/1/10.</p>
 *
 * @author xxx
 * @version 1.0
 */
public abstract class DateUtils
{

    public static final String DEFAULT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static Date parse(String pattern, String source)
    {
        if (StringUtils.isEmpty(source))
        {
            return null;
        }

        try
        {
            return new SimpleDateFormat(pattern).parse(source);
        }
        catch (ParseException e)
        {
            throw new RuntimeException("can not parse " + source + " pattern " + pattern, e);
        }
    }

    public static String format(String pattern, Date source)
    {
        if (source == null)
        {
            return null;
        }

        return new SimpleDateFormat(pattern).format(source);
    }
}
