package com.four.king.kong.basic.core.service.support;

import com.four.king.kong.basic.core.service.IJdbcPasswordDecoder;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 密码加密 .</p>
 * <p>Copyright: Copyright(c) 2017.</p>
 * <p>Company: xxx.</p>
 * <p>CreateTime: 2017/1/10.</p>
 *
 * @author xxx
 * @version 1.0
 */
public class JdbcPasswordDecoderFactory implements FactoryBean<String>
{

    /**
     * jdbcPasswordDecoder
     */
    private IJdbcPasswordDecoder jdbcPasswordDecoder;
    /**
     * password
     */
    private String password;

    /**
     * <p>Description: 类初始化调用方法.</p>
     * <p>Copyright: Copyright(c)2016.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 20170105.</p>
     *
     * @author xxx
     * @version 1.0
     **/
    @PostConstruct
    public void init()
    {
        Assert.notNull(jdbcPasswordDecoder, "jdbcPasswordDecoder must not be null");
    }

    /**
     * <p>Description: 重写getObject方法.</p>
     * <p>Copyright: Copyright(c)2016.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 20170105.</p>
     *
     * @return String
     * @throws Exception 异常
     * @author xxx
     * @version 1.0
     **/
    @Override
    public String getObject() throws Exception
    {
        return jdbcPasswordDecoder.decode(password);
    }

    /**
     * <p>Description: 重写getObject方法.</p>
     * <p>Copyright: Copyright(c)2016.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 20170105.</p>
     *
     * @return Class
     * @author xxx
     * @version 1.0
     **/
    @Override
    public Class<?> getObjectType()
    {
        return String.class;
    }

    /**
     * <p>Description: 重写isSingleton方法.</p>
     * <p>Copyright: Copyright(c)2016.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 20170105.</p>
     *
     * @return boolean
     * @author xxx
     * @version 1.0
     **/
    @Override
    public boolean isSingleton()
    {
        return true;
    }

    public void setJdbcPasswordDecoder(IJdbcPasswordDecoder jdbcPasswordDecoder)
    {
        this.jdbcPasswordDecoder = jdbcPasswordDecoder;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
