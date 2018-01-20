package com.four.king.kong.portal.example.controller;

import com.four.king.kong.basic.core.domain.Pagination;
import com.four.king.kong.basic.utils.JsonUtils.Status;
import com.four.king.kong.basic.web.controller.BasicController;
import com.four.king.kong.model.TestUser;
import com.four.king.kong.model.form.TestForm;
import com.four.king.kong.portal.example.dao.IDebugDao;
import com.four.king.kong.portal.example.service.IDebugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * <p>Title: DebugController</p>
 * <p>Description: DebugController</p>
 * <p>Copyright:Copyright(c) coder 2017</p>
 * <p>Company: xxx</p>
 * <p>CreateTime: 2018/1/6 12:05</p>
 *
 * @author cb
 * @version 1.0
 **/
@RestController
@RequestMapping("example")
public class DebugController extends BasicController
{
    @Autowired
    @Qualifier(IDebugService.SERVICE_ID)
    private IDebugService debugService;

    @Autowired
    @Qualifier(IDebugDao.DAO_ID)
    IDebugDao debugDao;

    @RequestMapping(method = GET, value = "/users", produces = {BasicController.APPLICATION_JSON_UTF8_VALUE})
    public Status pagingGet(int pageNum, int pageSize, String userName)
    {
        TestForm form = new TestForm();
        form.setPageNum(pageNum);
        form.setPageSize(pageSize);
        form.setUserName(userName);
        Pagination<TestUser> testUserPagination = null;
        try
        {
            testUserPagination = debugService.pagingGetUsers(form);
        }
        catch (Exception e)
        {
            logger.error("xxx", e);
            return new Status(Status.FALSE, "查询失败");
        }
        return new Status(Status.TRUE, testUserPagination);
    }

    @RequestMapping(method = POST, value = "/user", consumes = {BasicController.APPLICATION_JSON_UTF8_VALUE}, produces = {BasicController.APPLICATION_JSON_UTF8_VALUE})
    public Status saveOneUser(@RequestBody @Valid TestUser user, BindingResult result)
    {
        try
        {
            if (result.hasErrors())
            {
                return new Status(Status.FALSE, result.getFieldError().getDefaultMessage());
            }
            debugDao.insertOneUser(user);
        }
        catch (Exception e)
        {
            logger.error("xxx", e);
            return new Status(Status.FALSE, "入库失败");
        }
        return new Status(Status.TRUE, "入库成功");
    }

    @RequestMapping(method = POST, value = "/users", consumes = {BasicController.APPLICATION_JSON_UTF8_VALUE}, produces = {BasicController.APPLICATION_JSON_UTF8_VALUE})
    public Status batchSaveUsers(@RequestBody List<TestUser> users)
    {
        try
        {
            debugDao.batchInsertUsers(users);
        }
        catch (Exception e)
        {
            logger.error("xxx", e);
            return new Status(Status.FALSE, "批量入库失败");
        }
        return new Status(Status.TRUE, "批量入库成功");
    }

    @RequestMapping(method = PUT, value = "/user", consumes = {BasicController.APPLICATION_JSON_UTF8_VALUE}, produces = {BasicController.APPLICATION_JSON_UTF8_VALUE})
    public Status modifyOneUser(@RequestBody TestUser user)
    {
        try
        {
            debugDao.updateOneUser(user);
        }
        catch (Exception e)
        {
            logger.error("xxx", e);
            return new Status(Status.FALSE, "更新失败");
        }
        return new Status(Status.TRUE, "更新成功");
    }

    @RequestMapping(method = PUT, value = "/users", consumes = {BasicController.APPLICATION_JSON_UTF8_VALUE}, produces = {BasicController.APPLICATION_JSON_UTF8_VALUE})
    public Status batchModifyUsers(@RequestBody List<TestUser> users)
    {
        try
        {
            debugDao.batchUpdateUsers(users);
        }
        catch (Exception e)
        {
            logger.error("xxx", e);
            return new Status(Status.FALSE, "批量更新失败");
        }
        return new Status(Status.TRUE, "批量更新成功");
    }

    @RequestMapping(method = DELETE, value = "/user", produces = {BasicController.APPLICATION_JSON_UTF8_VALUE})
    public Status deleteOneUser(Long userId)
    {
        try
        {
            debugDao.deleteOneUser(userId);
        }
        catch (Exception e)
        {
            logger.error("xxx", e);
            return new Status(Status.FALSE, "删除失败");
        }
        return new Status(Status.TRUE, "删除成功");
    }

    @RequestMapping(method = DELETE, value = "/users", consumes = {BasicController.APPLICATION_JSON_UTF8_VALUE}, produces = {BasicController.APPLICATION_JSON_UTF8_VALUE})
    public Status batchDeleteUsers(@RequestBody List<Long> userIds)
    {
        try
        {
            debugDao.batchDeleteUsers(userIds);
        }
        catch (Exception e)
        {
            logger.error("xxx", e);
            return new Status(Status.FALSE, "批量删除失败");
        }
        return new Status(Status.TRUE, "批量删除成功");
    }
}
