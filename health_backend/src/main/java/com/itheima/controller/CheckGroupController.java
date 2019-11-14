package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 检查组WEB层
 * @author lizefeng
 * @date 2019/11/10 11:12
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 新增检查组
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        boolean b = true;
        try {
            b = checkGroupService.add(checkGroup, checkitemIds);
        } catch (Exception e) {
            e.printStackTrace();
            //出现异常
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL, e.getMessage());
        }
        if (b) {
            //数据增加成功
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } else {
            //数据增加失败
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL, "服务器异常，请稍后再试");
        }
    }

    /**
     * 分页+条件查询检查组
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = null;
        try {
            pageResult = checkGroupService.findPage(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_SUCCESS,"网络延迟，请稍后再试");
        }
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }

    /**
     * 编辑检查组数据回显
     * @param id
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(Integer id){
        Map<String, Object> upload = null;
        try {
            upload = checkGroupService.upload(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,null,"网络延迟，请稍后再试");
        }
        return new Result(true,null,upload);
    }

    /**
     * 修改检查组
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        boolean b = true;
        try {
            b = checkGroupService.update(checkGroup, checkitemIds);
        } catch (Exception e) {
            e.printStackTrace();
            //出现异常
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL, e.getMessage());
        }
        if (b) {
            //数据增加成功
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } else {
            //数据增加失败
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_SUCCESS, "服务器异常，请稍后再试");
        }
    }

    /**
     * 查询所有检查组
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll(){
        List<CheckGroup> checkGroups = null;
        try {
            checkGroups = checkGroupService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL,"网络延迟，请稍后再试");
        }
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroups);
    }
}
