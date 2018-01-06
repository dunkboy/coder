package com.four.king.kong.basic.core.domain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: session上下文.</p>
 * <p>Copyright: Copyright(c) 2017.</p>
 * <p>Company: xxx.</p>
 * <p>CreateTime: 2017/1/10.</p>
 *
 * @author xxx
 * @version 1.0
 */
public class SessionContext
{
    /**
     * 用户ID
     */
    public static final String USER_ID = "userId";
    /**
     * 用户
     */
    public static final String USER = "user";
    /**
     * response
     */
    public static final String RESPONSE = "response";
    /**
     * request
     */
    public static final String REQUEST = "request";
    /**
     * locale
     */
    public static final String LOCALE = "locale";
    /**
     * CTX
     */
    private static final ThreadLocal<SessionContext> CTX = new ThreadLocal<SessionContext>();
    /**
     * US
     */
//    private static IUserService us = null;
    /**
     * 属性值
     */
    private Map<String, Object> attributes = new HashMap<String, Object>();

    /**
     * 构造方法
     *
     * @return SessionContext 上下文
     */
    public static SessionContext get()
    {
        return CTX.get();
    }

    /**
     * set
     *
     * @param context 上下文
     */
    public static void set(SessionContext context)
    {
        CTX.set(context);
    }

    /**
     * getUserService
     *
     * @return IUserService
     */
//    private static IUserService getUserService()
//    {
//        if (us == null)
//        {
//            us = ServiceUtils.get(IUserService.SERVICE_ID, IUserService.class);
//        }
//        return us;
//    }

    /**
     * copy
     *
     * @return 上下文
     */
    public SessionContext copy()
    {
        SessionContext context = new SessionContext();
        context.attributes.putAll(attributes);
        return context;
    }

    /**
     * current
     *
     * @param key 键
     * @return 对象
     */
    public Object current(String key)
    {
        return attributes.get(key);
    }

    /**
     * current
     *
     * @param key   键
     * @param value 值
     */
    public void current(String key, Object value)
    {
        attributes.put(key, value);
    }

    /**
     * currentRequest
     *
     * @return HttpServletRequest HttpServletRequest
     */
    public HttpServletRequest currentRequest()
    {
        return (HttpServletRequest) current(REQUEST);
    }

    /**
     * currentRequest
     *
     * @param request request
     */
    public void currentRequest(HttpServletRequest request)
    {
        current(REQUEST, request);
    }

    /**
     * currentResponse
     *
     * @return HttpServletResponse HttpServletResponse
     */
    public HttpServletResponse currentResponse()
    {
        return (HttpServletResponse) current(RESPONSE);
    }

    /**
     * currentResponse
     *
     * @param response response
     */
    public void currentResponse(HttpServletResponse response)
    {
        current(RESPONSE, response);
    }

    /**
     * currentUser
     *
//     * @param <U> 泛型类型
     * @return U 泛型对象
     */
    @SuppressWarnings("unchecked")
//    public <U extends BasicUser<?>> U currentUser()
//    {
//        U u = (U) current(USER);
//        if (u == null)
//        {
//            String id = (String) current(USER_ID);
//            if (id != null && (u = getUserService().getUserById(id)) != null)
//            {
//                currentUser(u);
//            }
//            else if (id != null)
//            {
//                // 如果数据库没有找到，那么就进行保存，这里不用更新用户，因为SEP用户的id和登陆名是不会改变的
//                u = getUserService().saveUser();
//                currentUser(u);
//            }
//        }
//        return u;
//    }

    /**
     * 获取当前用户
     *
     * @param <U>  泛型类型
     * @param user 用户
     */
//    public <U extends BasicUser<?>> void currentUser(U user)
//    {
//        current(USER, user);
//    }

    /**
     * 获取当前用户ＩＤ
     *
     * @return string 字符串
     */
    public String currentUserId()
    {
        String userId = (String) current(USER_ID);
        if (userId == null)
        {
//            BasicUser<?> u = (BasicUser<?>) current(USER);
//            if (u != null)
//            {
//                current(USER_ID, userId = u.getId());
//            }
        }
        return userId;
    }

    /**
     * 获取当前用户ＩＤ
     *
     * @param userId 用户ID
     */
    public void currentUserId(String userId)
    {
        current(USER_ID, userId);
    }

    /**
     * currentLocale
     *
     * @return 字符串
     */
    public String currentLocale()
    {
        return (String) current(LOCALE);
    }

    /**
     * currentLocale
     *
     * @param locale locale
     */
    public void currentLocale(String locale)
    {
        current(LOCALE, locale);
    }
}
