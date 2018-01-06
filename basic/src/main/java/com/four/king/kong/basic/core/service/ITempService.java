package com.four.king.kong.basic.core.service;

import java.io.File;
import java.io.InputStream;

/**
 * <p>Description: 临时目录处理接口.</p>
 * <p>Copyright: Copyright(c) 2017.</p>
 * <p>Company: xxx.</p>
 * <p>CreateTime: 2017/1/10.</p>
 *
 * @author xxx
 * @version 1.0
 */
public interface ITempService
{
    /**
     * SERVICE_ID
     */
    String SERVICE_ID = "basic.tempService";

    /**
     * <p>Description: 创建文件.</p>
     * <p>Copyright: Copyright(c)2016.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 20170105.</p>
     *
     * @param delay 参数对象
     * @return File
     * @version 1.0
     **/
    File create(long delay);

    /**
     * <p>Description: create.</p>
     * <p>Copyright: Copyright(c)2016.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 20170105.</p>
     *
     * @param delay   参数对象
     * @param content content
     * @return File
     * @version 1.0
     **/
    File create(long delay, byte[] content);

    /**
     * <p>Description: create.</p>
     * <p>Copyright: Copyright(c)2016.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 20170105.</p>
     *
     * @param delay 参数对象
     * @param is    is
     * @return File
     * @version 1.0
     **/
    File create(long delay, InputStream is);

    /**
     * <p>Description: get.</p>
     * <p>Copyright: Copyright(c)2016.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 20170105.</p>
     *
     * @param name name
     * @return File
     * @version 1.0
     **/
    File get(String name);

    /**
     * <p>Description: getRoot.</p>
     * <p>Copyright: Copyright(c)2016.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 20170105.</p>
     *
     * @return File
     * @version 1.0
     **/
    File getRoot();
}
