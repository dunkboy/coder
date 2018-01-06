package com.four.king.kong.model.form;

import com.four.king.kong.basic.core.domain.SearchForm;
import org.apache.ibatis.type.Alias;

/**
 * <p>Title: TestForm</p>
 * <p>Description: TestForm</p>
 * <p>Copyright:Copyright(c) xxx 2017</p>
 * <p>Company: four king kong</p>
 * <p>CreateTime: 2018/1/6 12:30</p>
 *
 * @author cb
 * @version 1.0
 **/
@Alias("userForm")
public class TestForm extends SearchForm
{
    private String userName;

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }
}
