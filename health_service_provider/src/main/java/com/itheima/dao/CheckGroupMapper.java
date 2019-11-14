package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 检查组数据层接口
 * @author lizefeng
 * @date 2019/11/10 11:18
 */
public interface CheckGroupMapper extends Mapper<CheckGroup> {
    /**
     * 新增检查组数据
     * @param checkGroup
     * @return
     */
    int addGroup(CheckGroup checkGroup);

    /**
     * 新增中间表数据
     * @param map
     */
    void addGroupAndItem(Map<String, Object> map);

    /**
     * 查询所有检查组
     * @return
     */
    Page<CheckGroup> findAll(String queryString);

    /**
     * 根据检查组id查找所包含的检查项
     * @param id
     * @return
     */
    List<Integer> findById(Integer id);

    /**
     * 根据id删除中间表的数据
     * @param id
     * @return
     */
    int deletById(Integer id);
}
