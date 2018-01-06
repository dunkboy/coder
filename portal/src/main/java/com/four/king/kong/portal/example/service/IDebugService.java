package com.four.king.kong.portal.example.service;

import com.four.king.kong.basic.core.domain.Pagination;
import com.four.king.kong.model.TestUser;
import com.four.king.kong.model.form.TestForm;

/**
 * <p>Title: IDebugService</p>
 * <p>Description: IDebugService</p>
 * <p>Copyright:Copyright(c) sefon 2017</p>
 * <p>Company: four king kong</p>
 * <p>CreateTime: 2018/1/6 13:45</p>
 *
 * @author cb
 * @version 1.0
 **/
public interface IDebugService
{
    /**
     * SERVICE_ID
     */
    String SERVICE_ID = "debugService";

    Pagination<TestUser> pagingGetUsers(TestForm form);
}
