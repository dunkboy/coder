package com.four.king.kong.basic.utils;

import com.four.king.kong.basic.core.domain.SessionContext;
import com.four.king.kong.basic.utils.bean.ServiceUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * <p>Description: web工具类.</p>
 * <p>Copyright: Copyright(c) 2017.</p>
 * <p>Company: xxx.</p>
 * <p>CreateTime: 2017/1/10.</p>
 *
 * @author xxx
 * @version 1.0
 */
public abstract class WebUtils extends TransferWebUtils
{
    /**
     * WEB_ROOT
     */
    private static String webROOT;

    /**
     * <p>Description: 获取webRoot.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/10.</p>
     *
     * @return 字符串
     * @author xxx
     * @version 1.0
     */
    public static String getWebRoot()
    {
        if (webROOT == null)
        {
            ApplicationContext ac = ServiceUtils.getApplicationContext();
            Assert.isTrue(ac instanceof WebApplicationContext,
                    ac + " not instanceof " + WebApplicationContext.class.getName());
            WebApplicationContext wac = (WebApplicationContext) ac;
            webROOT = wac.getServletContext().getRealPath("");
        }
        return webROOT;
    }

    /**
     * <p>Description: remoteAddr.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/10.</p>
     *
     * @return 字符串
     * @author xxx
     * @version 1.0
     */
    public static String remoteAddr()
    {
        return remoteAddr(SessionContext.get().currentRequest());
    }

    /**
     * <p>Description: remoteAddr.</p>
     * <p>Copyright: Copyright(c) 2017.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 2017/1/10.</p>
     *
     * @param request 对象
     * @return 字符串
     * @author xxx
     * @version 1.0
     */
    public static String remoteAddr(HttpServletRequest request)
    {
        if (request == null)
        {
            return null;
        }

        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }

        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

    /**
     * headers
     *
     * @return 字符串
     */
    public static String headers()
    {
        return headers(SessionContext.get().currentRequest());
    }

    /**
     * headers
     *
     * @param request 请求对象
     * @return 字符串
     */
    public static String headers(HttpServletRequest request)
    {
        StringBuffer hs = new StringBuffer();
        Enumeration<String> en = request.getHeaderNames();
        while (en.hasMoreElements())
        {
            String name = en.nextElement();
            String value = request.getHeader(name);
            hs.append(name).append("=").append(value).append(";");
        }
        return hs.toString();
    }

    /**
     * params
     *
     * @return 字符串
     */
    public static String params()
    {
        SessionContext sc = SessionContext.get();
        if (sc != null)
        {
            return params(sc.currentRequest());
        }
        else
        {
            return "";
        }
    }

    /**
     * params
     *
     * @param request 对象
     * @return 字符串
     */
    public static String params(HttpServletRequest request)
    {
        StringBuffer ps = new StringBuffer();
        if (request != null)
        {
            Enumeration<String> en = request.getParameterNames();
            while (en.hasMoreElements())
            {
                String name = en.nextElement();
                String[] values = request.getParameterValues(name);
                if (values != null && !ArrayUtils.isEmpty(values))
                {
                    for (String value : values)
                    {
                        ps.append(name).append("=").append(value).append(";");
                    }
                }
                else
                {
                    ps.append(name).append("=").append(";");
                }
            }
        }
        return ps.toString();
    }

    /**
     * hasParams
     *
     * @param names 名称集合
     * @return 是 否
     */
    public static boolean hasParams(String... names)
    {
        return hasParams(SessionContext.get().currentRequest(), names);
    }

    /**
     * hasParams
     *
     * @param request 对象
     * @param names 名字集合
     * @return 是 否
     */
    public static boolean hasParams(HttpServletRequest request, String... names)
    {
        for (String name : names)
        {
            if (StringUtils.isEmpty(request.getParameter(name)))
            {
                return false;
            }
        }
        return true;
    }
}

/**
 * <p>Description: 转换工具类.</p>
 * <p>Copyright: Copyright(c) 2017.</p>
 * <p>Company: xxx.</p>
 * <p>CreateTime: 2017/1/10.</p>
 *
 * @author xxx
 * @version 1.0
 */
class TransferWebUtils extends org.springframework.web.util.WebUtils
{
}