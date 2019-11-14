package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;

/**
 * 套餐组接口
 * @author lizefeng
 * @date 2019/11/12 9:03
 */
public interface SetmealService {

    /**
     * 新增套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    boolean add(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 条件+分页查询数据
     * @param queryPageBean
     * @return
     */
    PageResult findPage(QueryPageBean queryPageBean);
}
