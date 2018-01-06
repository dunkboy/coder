package com.four.king.kong.portal.example.service.support;

import com.four.king.kong.basic.core.domain.Pagination;
import com.four.king.kong.model.TestUser;
import com.four.king.kong.model.form.TestForm;
import com.four.king.kong.portal.example.dao.IDebugDao;
import com.four.king.kong.portal.example.service.IDebugService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Title: DebugServiceImpl</p>
 * <p>Description: DebugServiceImpl</p>
 * <p>Copyright:Copyright(c) xxx 2017</p>
 * <p>Company: four king kong</p>
 * <p>CreateTime: 2018/1/6 13:45</p>
 *
 * @author cb
 * @version 1.0
 **/
@Service(IDebugService.SERVICE_ID)
public class DebugServiceImpl implements IDebugService
{
    @Autowired
    @Qualifier(IDebugDao.DAO_ID)
    IDebugDao debugDao;

    @Override
    public Pagination<TestUser> pagingGetUsers(TestForm form)
    {
        //开始分页
        Page<Object> page = PageHelper.startPage(form.getPageNum(), form.getPageSize(), true);
        List<TestUser> testUsers = debugDao.pagingQueryUser(form);
        return new Pagination<>(form.getPageSize(), page.getTotal(), testUsers);
    }
}
