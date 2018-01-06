package com.four.king.kong.basic.core.domain;

/**
 * <p>Title: SearchForm</p>
 * <p>Description: SearchForm</p>
 * <p>Copyright:Copyright(c) sefon 2017</p>
 * <p>Company: xxx</p>
 * <p>CreateTime: 2017/8/21 12:19</p>
 *
 * @author xxx
 * @version 1.0
 **/
public class SearchForm
{
    /**
     * 当前页码
     */
    private int pageNum=1;

    /**
     * 每页显示记录数, 默认为15
     */
    private int pageSize=15;

    public int getPageNum()
    {
        return pageNum;
    }

    public void setPageNum(int pageNum)
    {
    	 if (pageNum <= 0)
         {
             this.pageNum = 1;
         }
         else
         {
             this.pageNum = pageNum;
         }
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        if (pageSize <= 0)
        {
            this.pageSize = 15;
        }
        else
        {
            this.pageSize = pageSize;
        }
    }

}
