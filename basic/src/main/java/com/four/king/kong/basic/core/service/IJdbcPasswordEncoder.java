package com.four.king.kong.basic.core.service;

/**
 * <p>Description: IJdbcPasswordEncoder.</p>
 * <p>Copyright: Copyright(c) 2017.</p>
 * <p>Company: xxx.</p>
 * <p>CreateTime: 2017/1/10.</p>
 *
 * @author xxx
 * @version 1.0
 */
public interface IJdbcPasswordEncoder
{

    /**
     * <p>Description: encode.</p>
     * <p>Copyright: Copyright(c)2016.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 20170105.</p>
     *
     * @param password password
     * @return String
     * @version 1.0
     **/
    String encode(String password);
}
