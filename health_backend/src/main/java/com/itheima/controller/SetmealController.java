package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

/**
 * 套餐数据返回层(WEB层)
 *
 * @author lizefeng
 * @date 2019/11/6 19:31
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 图片上传
     * @param imgFile
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        //获取文件的名称
        String originalFilename = imgFile.getOriginalFilename();
        //截取文件的格式
        int lastIndexOf = originalFilename.lastIndexOf(".");
        //.jpg
        String substring = originalFilename.substring(lastIndexOf);
        substring = UUID.randomUUID() + substring;
        substring = substring.replaceAll("-", "");
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), substring);
            //将上传图片名称存入Redis，基于Redis的Set集合存储     
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,substring );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, substring);
    }

    /**
     * 新增套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds) {
        boolean b = true;
        try {
            b = setmealService.add(setmeal, checkgroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            //出现异常
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL, e.getMessage());
        }
        if (b) {
            //数据增加成功
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } else {
            //数据增加失败
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL, "服务器异常，请稍后再试");
        }
    }

    /**
     * 条件+分页查询数据
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult page = null;
        try {
            page = setmealService.findPage(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL,"网络延迟，请稍后再试");
        }
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,page);
    }
}
