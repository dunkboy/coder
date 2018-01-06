package com.four.king.kong.basic.core.domain;

import java.util.List;

/**
 * <p>Description: 分页结果集对象.</p>
 * <p>Copyright: Copyright(c) 2017.</p>
 * <p>Company: xxx.</p>
 * <p>CreateTime: 2017/1/9.</p>
 *
 * @param <T> t
 * @author xxx
 * @version 1.0
 */
public class Pagination<T>
{
    /**
     * 获取多少行
     */
    private int limit;
    /**
     * 结果总数
     */
    private long total;
    /**
     * 结果集
     */
    private List<T> result;

    /**
     * 无参构造
     */
    public Pagination()
    {
    }

    /**
     * 有参构造
     *
     * @param total  总数
     * @param result result
     */
    public Pagination(long total, List<T> result)
    {
        this(0, total, result);
    }

    /**
     * 有参构造
     *
     * @param limit  limit
     * @param total  total
     * @param result result
     */
    public Pagination(int limit, long total, List<T> result)
    {
        this.limit = limit;
        this.total = total;
        this.result = result;
    }

    public int getLimit()
    {
        return limit;
    }

    public void setLimit(int limit)
    {
        this.limit = limit;
    }

    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public List<T> getResult()
    {
        return result;
    }

    public void setResult(List<T> result)
    {
        this.result = result;
    }

}
