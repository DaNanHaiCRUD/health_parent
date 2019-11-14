package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import tk.mybatis.mapper.common.Mapper;

/**
 * 检查项数据层接口
 * @author lizefeng
 * @date 2019/11/6 19:44
 */
public interface CheckItemMapper extends Mapper<CheckItem> {

    /**
     * 新增检查项
     * @param checkItem
     * @return
     */
    int add(CheckItem checkItem);

    /**
     * 条件查询
     * @param queryString
     * @return
     */
    Page<CheckItem> findPage(String queryString);

    /**
     * 根据检查项id查询中间表
     * @param id
     * @return
     */
    int selectById(Integer id);
}
