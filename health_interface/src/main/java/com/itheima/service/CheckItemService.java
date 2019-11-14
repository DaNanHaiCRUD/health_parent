package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * 检查项业务层接口
 * @author lizefeng
 * @date 2019/11/6 19:42
 */
public interface CheckItemService {

    /**
     * 新增检查项
     * @param checkItem
     * @return
     */
    public boolean add(CheckItem checkItem);

    /**
     * 分页+条件查询数据
     * @param queryPageBean
     * @return
     */
    PageResult findPage(QueryPageBean queryPageBean);

    /**
     * 根据id删除检查项
     * @param id
     * @return
     */
    boolean delete(Integer id);

    /**
     * 根据id查询检查项
     * @param id
     * @return
     */
    CheckItem findById(Integer id);

    /**
     * 修改检查项
     * @param checkItem
     * @return
     */
    boolean update(CheckItem checkItem);

    /**
     * 查询所有检查项
     * @return
     */
    List<CheckItem> findAll();
}
