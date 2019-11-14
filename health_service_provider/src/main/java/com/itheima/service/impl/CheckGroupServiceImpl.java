package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupMapper;
import com.itheima.dao.CheckItemMapper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.MarshalledObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检查组业务层实现类
 *
 * @author lizefeng
 * @date 2019/11/10 11:16
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupMapper checkGroupMapper;
    @Autowired
    private CheckItemMapper checkItemMapper;

    /**
     * 新增检查组
     *
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @Override
    public boolean add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //1.判断编码,名称,助记码是否重复
        CheckGroup cg = new CheckGroup();
        cg.setCode(checkGroup.getCode());
        //1.1判断编码是否重复
        CheckGroup c1 = checkGroupMapper.selectOne(cg);
        if (c1 != null && c1.getCode().equals(checkGroup.getCode())) {
            //编码重复
            throw new RuntimeException("编码重复，请重新输入");
        }

        //1.2判断名称是否重复
        cg = new CheckGroup();
        cg.setName(checkGroup.getName());
        c1 = checkGroupMapper.selectOne(cg);
        if (c1 != null && c1.getName().equals(checkGroup.getName())) {
            //编码重复
            throw new RuntimeException("名称重复，请重新输入");
        }

        //1.3判断助记码是否重复
        cg = new CheckGroup();
        cg.setHelpCode(checkGroup.getHelpCode());
        c1 = checkGroupMapper.selectOne(cg);
        if (c1 != null && c1.getHelpCode().equals(checkGroup.getHelpCode())) {
            //编码重复
            throw new RuntimeException("助记码重复，请重新输入");
        }

        //2.添加数据
        int count = checkGroupMapper.addGroup(checkGroup);
        if (count > 0) {
            //添加数据成功
            for (Integer checkitemId : checkitemIds) {
                Map<String, Object> map = new HashMap<>();
                map.put("checkgroupId", checkGroup.getId());
                map.put("checkitemId", checkitemId);
                checkGroupMapper.addGroupAndItem(map);
            }
            return true;
        }
        return false;
    }

    /**
     * 分页+条件查询检查组
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //获取页码
        Integer currentPage = queryPageBean.getCurrentPage();
        if (currentPage < 0 || currentPage == null) {
            currentPage = 1;
        }
        //获取每页记录数
        Integer pageSize = queryPageBean.getPageSize();
        if (pageSize < 0 || pageSize == null) {
            pageSize = 10;
        }
        //分页
        PageHelper.startPage(currentPage, pageSize);
        //获取查询条件
        String queryString = queryPageBean.getQueryString();
        if (queryString == null) {
            queryString = "";
        } else {
            queryString = queryString.replaceAll(" ", "");
            Page<CheckGroup> page = checkGroupMapper.findAll("%" + queryString + "%");
            if (page.getTotal() < pageSize) {
                currentPage = 1;
            }
        }

        //分页
        PageHelper.startPage(currentPage, pageSize);
        //查询数据
        Page<CheckGroup> page = checkGroupMapper.findAll("%" + queryString + "%");

        return new PageResult(page.getTotal(), page);
    }

    /**
     * 编辑检查组数据回显
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> upload(Integer id) {
        //1.查询出所有的检查项
        List<CheckItem> checkItems = checkItemMapper.selectAll();
        //2.根据id查找对应的检查组
        CheckGroup checkGroup = checkGroupMapper.selectByPrimaryKey(id);
        //3.根据检查组id查找包含的检查项信息
        List<Integer> checkitemIds = checkGroupMapper.findById(id);

        //把所有获得信息封装进map集合
        Map<String, Object> map = new HashMap<>();
        map.put("checkItems", checkItems);
        map.put("checkGroup", checkGroup);
        map.put("checkitemIds", checkitemIds);
        return map;
    }

    /**
     * 修改检查组
     *
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @Override
    public boolean update(CheckGroup checkGroup, Integer[] checkitemIds) {
        //1.判断编码,名称,助记码是否重复
        CheckGroup c1 = checkGroupMapper.selectByPrimaryKey(checkGroup.getId());
        CheckGroup cg = new CheckGroup();
        //1.1判断编码是否修改
        if (!c1.getCode().equals(checkGroup.getCode())) {
            //编码已经修改
            //判断编码是否重复
            cg = new CheckGroup();
            cg.setName(checkGroup.getName());
            int countName = checkGroupMapper.selectCount(cg);
            if (countName > 0) {
                //说明项目编码重复
                throw new RuntimeException("编码重复，请重新输入");
            }

        }

        //1.2判断名称是否重复
        if (!c1.getName().equals(checkGroup.getName())) {
            //编码重复
            cg = new CheckGroup();
            cg.setName(checkGroup.getName());
            int countName = checkGroupMapper.selectCount(cg);
            if (countName > 0) {
                //说明项目编码重复
                throw new RuntimeException("名称重复，请重新输入");
            }
        }

        //1.3判断助记码是否重复
        if (!c1.getHelpCode().equals(checkGroup.getHelpCode())) {
            //编码重复
            cg = new CheckGroup();
            cg.setHelpCode(checkGroup.getHelpCode());
            int countName = checkGroupMapper.selectCount(cg);
            if (countName > 0) {
                //说明项目编码重复
                throw new RuntimeException("助记码重复，请重新输入");
            }
        }

        //2.修改中间表
        //2.1根据id先删除
        int count = checkGroupMapper.deletById(checkGroup.getId());
        if (count > 0) {
            //删除成功
            //2.2增加数据
            for (Integer checkitemId : checkitemIds) {
                Map<String, Object> map = new HashMap<>();
                map.put("checkgroupId", checkGroup.getId());
                map.put("checkitemId", checkitemId);
                checkGroupMapper.addGroupAndItem(map);
            }
            //2.3修改检查组信息
            int c = checkGroupMapper.updateByPrimaryKey(checkGroup);
            if (c > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查询所有检查组
     *
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        List<CheckGroup> checkGroups = checkGroupMapper.selectAll();
        return checkGroups;
    }
}
