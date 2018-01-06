package com.four.king.kong.basic.utils;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * <p>Title: ArrayUtils</p>
 * <p>Description: ArrayUtils</p>
 * <p>Copyright:Copyright(c) xxx 2017</p>
 * <p>Company: xxx</p>
 * <p>CreateTime: 2017/6/27 16:55</p>
 *
 * @author xxx
 * @version 1.0
 **/
public class ArrayUtils
{
    /**
     * 根据指定属性对集合顺序排序 ArrayUtils.sortAsc(list, r -> r.properties());
     *
     * @param data 集合对象
     * @param func 委托
     * @param <T>  数据类型
     * @param <R>  要排序的属性的数据类型
     */
    public static <T, R extends Comparable<? super R>> void sortAsc(List<T> data, Function<T, R> func)
    {
        Comparator<T> comparator = Comparator.comparing(func);
        data.sort(comparator);
    }

    /**
     * 根据指定属性对集合倒序排序
     *
     * @param data 集合对象
     * @param func 委托
     * @param <T>  数据类型
     * @param <R>  要排序的属性的数据类型
     */
    public static <T, R extends Comparable<? super R>> void sortDesc(List<T> data, Function<T, R> func)
    {
        Comparator<T> comparator = Comparator.comparing(func).reversed();
        data.sort(comparator);
    }
    
    
    /**
     * 判断是否空集合
     * @param list List<T>
     * @return true 表示是空的集合，false 表示不是空集合
     */
    public static <T> boolean isEmpty(List<T> list)
    {
        return null == list || list.isEmpty();
    }
}
