package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @author lizefeng
 * @date 2019/11/12 20:04
 */
public class JobDemo {

    @Autowired
    private JedisPool jedisPool;

    public void run(){
        //根据redis中保存得两个set集合进行差值计算，获得垃圾图片名称集合
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if (set != null){
            for (String s : set) {
                QiniuUtils.deleteFileFromQiniu(s);
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,s);
                System.out.println("删除图片"+s);
            }
        }
    }

}
