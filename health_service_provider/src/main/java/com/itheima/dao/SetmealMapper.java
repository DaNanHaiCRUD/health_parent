package com.itheima.dao;

import com.itheima.pojo.Setmeal;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

/**
 * 套餐组接口
 * @author lizefeng
 * @date 2019/11/12 9:22
 */
public interface SetmealMapper extends Mapper<Setmeal> {

    /**
     * 新增套餐
     * @param setmeal
     * @return
     */
    int insertSetmeal(Setmeal setmeal);

    /**
     * 新增套餐的中间表
     * @param map
     */
    void insertSetmealAndCheckgroup(Map<String, Object> map);
}
