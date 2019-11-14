package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;

import java.util.List;
import java.util.Map;

/**
 * 检查组业务接口
 * @author lizefeng
 * @date 2019/11/10 11:14
 */
public interface CheckGroupService {

    /**
     * 新增检查组
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    boolean add(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 分页+条件查询检查组
     * @param queryPageBean
     * @return
     */
    PageResult findPage(QueryPageBean queryPageBean);

    /**
     * 编辑检查组数据回显
     * @param id
     * @return
     */
    Map<String,Object> upload(Integer id);

    /**
     * 编辑检查组
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    boolean update(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();

}
