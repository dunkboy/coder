package com.four.king.kong.basic.core.service.support;

import com.four.king.kong.basic.core.service.IJdbcPasswordDecoder;
import com.four.king.kong.basic.core.service.IJdbcPasswordEncoder;
import com.four.king.kong.basic.utils.AES128Encrypt;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 密码加密接口默认实现.</p>
 * <p>Copyright: Copyright(c) 2017.</p>
 * <p>Company: xxx.</p>
 * <p>CreateTime: 2017/1/10.</p>
 *
 * @author xxx
 * @version 1.0
 */
public class DefaultJdbcPasswordDecoderImpl implements IJdbcPasswordEncoder, IJdbcPasswordDecoder
{

    /**
     * logger
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * aes 加密对象
     */
    private AES128Encrypt aes;

    /**
     * <p>Description: 类初始化调用方法.</p>
     * <p>Copyright: Copyright(c)2016.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 20170105.</p>
     *
     * @author cb
     * @version 1.0
     **/
    @PostConstruct
    public void init()
    {
        aes = new AES128Encrypt();
    }

    /**
     * <p>Description: 加密.</p>
     * <p>Copyright: Copyright(c)2016.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 20170105.</p>
     *
     * @param password 密码
     * @return String
     * @author xxx
     * @version 1.0
     **/
    @Override
    public String encode(String password)
    {
        if (StringUtils.isNotEmpty(password))
        {
            try
            {
                password = aes.encrypt(password, createSecretKey());
            }
            catch (Exception e)
            {
                logger.error(null, e);
            }
        }
        return password;
    }

    /**
     * <p>Description: 解密.</p>
     * <p>Copyright: Copyright(c)2016.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 20170105.</p>
     *
     * @param password 密码
     * @return String
     * @author cb
     * @version 1.0
     **/
    @Override
    public String decode(String password)
    {
        if (StringUtils.isNotEmpty(password))
        {
            try
            {
                password = aes.decrypt(password, createSecretKey());
            }
            catch (Exception e)
            {
                logger.error(null, e);
            }
        }
        Assert.hasText(password, "decoded password must have text; it must not be null, empty, or blank");
        return password;
    }

    /**
     * <p>Description: 密匙.</p>
     * <p>Copyright: Copyright(c)2016.</p>
     * <p>Company: xxx.</p>
     * <p>CreateTime: 20170105.</p>
     *
     * @return byte
     * @author cb
     * @version 1.0
     **/
    private byte[] createSecretKey()
    {
        return new byte[]{104, 117, 97, 119, 101, 105, 95, 99, 100, 115, 102, 95, 112, 97, 115, 115};
    }
}
