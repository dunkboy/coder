package com.four.king.kong.portal.example.dao;

import com.four.king.kong.model.TestUser;
import com.four.king.kong.model.form.TestForm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Title: IDebugDao</p>
 * <p>Description: IDebugDao</p>
 * <p>Copyright:Copyright(c) sefon 2017</p>
 * <p>Company: four king kong</p>
 * <p>CreateTime: 2018/1/6 12:25</p>
 *
 * @author cb
 * @version 1.0
 **/
@Repository(IDebugDao.DAO_ID)
public interface IDebugDao
{
    /**
     * DAO_ID
     */
    String DAO_ID = "debugDao";

    //分页获取数据
    List<TestUser> pagingQueryUser(TestForm form);

    //用户入库
    void insertOneUser(TestUser user);

    //用户批量入库
    void batchInsertUsers(List<TestUser> users);

    //用户更新
    void updateOneUser(TestUser user);

    //用户批量更新
    void batchUpdateUsers(@Param("users") List<TestUser> users);

    //删除用户
    void deleteOneUser(@Param("userId") Long userId);

    //批量删除用户
    void batchDeleteUsers(@Param("userIds") List<Long> userIds);
}
