package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemMapper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 检查项业务层实现类
 *
 * @author lizefeng
 * @date 2019/11/6 19:43
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemMapper checkItemMapper;

    /**
     * 新增检查项
     *
     * @param checkItem
     * @return
     */
    @Override
    public boolean add(CheckItem checkItem) {
        CheckItem c1 = new CheckItem();
        c1.setCode(checkItem.getCode());
        int countCode = checkItemMapper.selectCount(c1);
        if (countCode > 0) {
            //说明项目编码重复
            throw new RuntimeException("项目编码重复");
        }
        c1 = new CheckItem();
        c1.setName(checkItem.getName());
        int countName = checkItemMapper.selectCount(c1);
        if (countName > 0) {
            //说明项目编码重复
            throw new RuntimeException("项目名称重复");
        }

        int i = checkItemMapper.insert(checkItem);
//        int i = checkItemMapper.add(checkItem);

        return i > 0 ? true : false;
    }

    /**
     * 分页 + 条件查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //获取当前页
        Integer currentPage = queryPageBean.getCurrentPage();
        if (currentPage <= 0 || currentPage == null) {
            //如果当前页是负页，或者是个空
            currentPage = 1;
        }
        //获取每页记录数
        Integer pageSize = queryPageBean.getPageSize();
        if (pageSize <= 0 || pageSize == null) {
            //如果当前页是负页，或者是个空
            pageSize = 10;
        }

        //分页
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckItem> page = null;
        //获取查询条件
        String queryString = queryPageBean.getQueryString();
        if (queryString == null || queryString.length() == 0) {
            //如果当前查询条件为null
            queryString = "";
        } else {
            //如果当前条件不为空，去除所有空格
            queryString = queryString.replaceAll(" ", "");

            //判断
            page = checkItemMapper.findPage("%" + queryString + "%");
            if (page.getTotal() < pageSize) {
                //如果查询到的条件小于每页显示条数
                //把当前页变为第一页
                currentPage = 1;
                //分页
                PageHelper.startPage(currentPage, pageSize);
            }
        }

        //分页
        PageHelper.startPage(currentPage, pageSize);
        //条件查询
        page = checkItemMapper.findPage("%" + queryString + "%");

        return new PageResult(page.getTotal(), page);
    }

    /**
     * 根据id删除检查项
     * @param id
     * @return
     */
    @Override
    public boolean delete(Integer id) {
        //1.先查询中间表，看是否关联了检查组
        int checkItemMapperById = checkItemMapper.selectById(id);
        if (checkItemMapperById > 0){
            //说明关联了检查组，删除失败
            throw new RuntimeException("该项在检查组里，请先删除检查组");
        }
        //2.没有关联检查组，删除检查项
        int count = checkItemMapper.deleteByPrimaryKey(id);
        if (count > 0){
            //删除成功
            return true;
        }
        return false;
    }

    /**
     * 根据id查询检查项
     * @param id
     * @return
     */
    @Override
    public CheckItem findById(Integer id) {
        CheckItem checkItem = checkItemMapper.selectByPrimaryKey(id);
        if (checkItem == null){
            throw new RuntimeException("服务器异常，请稍后再试");
        }
        return checkItem;
    }

    /**
     * 修改检查项
     * @param checkItem
     * @return
     */
    @Override
    public boolean update(CheckItem checkItem) {
        //1.先判断有没有修改数据
        CheckItem c = checkItemMapper.selectByPrimaryKey(checkItem.getId());
        CheckItem c1 = new CheckItem();

        //1.1判断项目名称是否修改
        if (!c.getName().equals(checkItem.getName())){
            //说明修改数据，判断项目名称是否重复
            c1 = new CheckItem();
            c1.setName(checkItem.getName());
            int countName = checkItemMapper.selectCount(c1);
            if (countName > 0) {
                //说明项目编码重复
                throw new RuntimeException("项目名称重复");
            }
        }
        //修改检查项数据
        int count = checkItemMapper.updateByPrimaryKey(checkItem);
        return count > 0 ? true : false;
    }

    /**
     * 查询所有检查项
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        List<CheckItem> checkItems = checkItemMapper.selectAll();
        return checkItems;
    }
}
