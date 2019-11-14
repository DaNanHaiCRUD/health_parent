package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 检查项数据返回层(WEB层)
 *
 * @author lizefeng
 * @date 2019/11/6 19:31
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    /**
     * 新增检查项
     *
     * @param checkItem
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {

        boolean b = true;
        try {
            b = checkItemService.add(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            //出现异常
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL, e.getMessage());
        }

        if (b) {
            //数据增加成功
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } else {
            //数据增加失败
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL, "服务器异常，请稍后再试");
        }
    }

    /**
     * 分页+条件查询
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = null;
        try {
            pageResult = checkItemService.findPage(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL, "服务器正忙，请稍后再试");
        }
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);
    }

    /**
     * 根据id删除检查项
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        boolean b = true;
        try {
            b = checkItemService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            //出现异常
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL, e.getMessage());
        }

        if (b) {
            //数据删除成功
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } else {
            //数据删除失败
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL, "服务器异常，请稍后再试");
        }
    }

    /**
     * 根据id查询检查项
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        CheckItem c = null;
        try {
            c = checkItemService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            //出现异常
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL, e.getMessage());
        }
        return new Result(true, null, c);

    }

    /**
     * 修改检查项
     *
     * @param checkItem
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody CheckItem checkItem) {
        boolean b = true;
        try {
            b = checkItemService.update(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            //出现异常
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL, e.getMessage());
        }
        if (b) {
            //数据增加成功
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } else {
            //数据增加失败
            return new Result(false, MessageConstant.EDIT_CHECKITEM_SUCCESS, "服务器异常，请稍后再试");
        }
    }

    /**
     * 查询所有检查项
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll() {
        List<CheckItem> b = null;
        try {
            b = checkItemService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            //出现异常
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL, "服务器异常，请稍后再试");
        }

        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,b);
    }
}
