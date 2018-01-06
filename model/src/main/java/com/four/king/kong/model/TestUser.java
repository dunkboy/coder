package com.four.king.kong.model;

import org.apache.ibatis.type.Alias;

/**
 * <p>Title: TestUser</p>
 * <p>Description: TestUser</p>
 * <p>Copyright:Copyright(c) sefon 2017</p>
 * <p>Company: four king kong</p>
 * <p>CreateTime: 2018/1/6 12:26</p>
 *
 * @author cb
 * @version 1.0
 **/
@Alias("user")
public class TestUser
{
    private Long userId;
    private String userName;
    private String password;

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
