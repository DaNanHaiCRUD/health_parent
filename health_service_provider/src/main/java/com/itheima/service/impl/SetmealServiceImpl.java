package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.SetmealMapper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lizefeng
 * @date 2019/11/12 9:21
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 新增套餐
     *
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @Override
    public boolean add(Setmeal setmeal, Integer[] checkgroupIds) {
        //1判断编码/名称/助记码是否重复
        //1.1判断编码是否重复
        Setmeal s1 = new Setmeal();
        s1.setCode(setmeal.getCode());
        int count = setmealMapper.selectCount(s1);
        if (count > 0) {
            //编码重复
            throw new RuntimeException("编码重复，请重新输入");
        }

        //1.2判断名称是否重复
        s1 = new Setmeal();
        s1.setName(setmeal.getName());
        count = setmealMapper.selectCount(s1);
        if (count > 0) {
            //名称重复
            throw new RuntimeException("名称重复，请重新输入");
        }

        //1.3判断助记码是否重复
        s1 = new Setmeal();
        s1.setHelpCode(setmeal.getHelpCode());
        count = setmealMapper.selectCount(s1);
        if (count > 0) {
            //助记码重复
            throw new RuntimeException("助记码重复，请重新输入");
        }

        //2.新增套餐组,并返回id值
        int insert = setmealMapper.insertSetmeal(setmeal);
        if (insert > 0) {
            //成功增加
            //新增中间表
            for (Integer checkgroupId : checkgroupIds) {
                Map<String, Object> map = new HashMap<>();
                map.put("setmealId",setmeal.getId());
                map.put("checkgroupId",checkgroupId);
                setmealMapper.insertSetmealAndCheckgroup(map);
            }
            //把图片信息存储到redis里面
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
            return true;
        }
        return false;
    }

    /**
     * 条件+分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //获取当前页
        Integer currentPage = queryPageBean.getCurrentPage();
        if (currentPage == null || currentPage <= 0){
            currentPage = 1;
        }
        //获取每页显示条数
        Integer pageSize = queryPageBean.getPageSize();
        if (pageSize == null || pageSize <= 0){
            pageSize = 10;
        }
        //获取查询条件
        String queryString = queryPageBean.getQueryString();
        if (queryString == null){
            queryString = "";
        }else{
            queryString = queryString.replaceAll(" ","");
        }

        //分页
        PageHelper.startPage(currentPage,pageSize);
        //查询数据库
        Page<Setmeal> setmeals = (Page<Setmeal>) setmealMapper.selectAll();

        return new PageResult(setmeals.getTotal(),setmeals);
    }
}
