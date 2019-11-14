package com.itheima.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.JedisPool;

/**
 * @author lizefeng
 * @date 2019/11/13 19:11
 */
public class MyTest01 {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring-jobs.xml");

    }

}
