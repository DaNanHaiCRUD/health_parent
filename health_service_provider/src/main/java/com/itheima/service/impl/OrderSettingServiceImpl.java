package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingMapper;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lizefeng
 * @date 2019/11/14 11:39
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;

    /**
     * 导入预约数据
     *
     * @param orderSettings
     * @return
     */
    @Override
    public void upload(List<OrderSetting> orderSettings) {
        //1.判断是否存在设置的日期
        for (OrderSetting orderSetting : orderSettings) {
            int count = orderSettingMapper.selectCount(orderSetting);
            if (count > 0) {
                //存在设置的日期，修改可预约数量
                orderSettingMapper.updateOrderSettiing(orderSetting);
            } else {
                //不存在设置的日期，增加新数据
                orderSettingMapper.insertOrderSettiing(orderSetting);
            }
        }
    }

    /**
     * 查询数据库中的预约信息
     *
     * @param date
     * @return
     */
    @Override
    public List<Map> getOrderSettingDate(String date) {
        //对获取的数据进行封装
        String begin = date + "-1";
        String end = date + "-31";
        //查询数据库获取OrderSetting信息
        List<OrderSetting> orderSettings = orderSettingMapper.getOrderSettingDate(begin, end);
        List<Map> orderSettingMap = new ArrayList<>();
        if (orderSettings != null && orderSettings.size() > 0) {
            for (OrderSetting orderSetting : orderSettings) {
                Map<String, Object> map = new HashMap<>();
                map.put("date", orderSetting.getOrderDate().getDate());
                map.put("number", orderSetting.getNumber());
                map.put("reservations", orderSetting.getReservations());
                orderSettingMap.add(map);
            }
        }
        return orderSettingMap;
    }

    /**
     * 修改预约信息
     * @param orderSetting
     * @return
     */
    @Override
    public void updateOrderSettingNumber(OrderSetting orderSetting) {
        orderSettingMapper.updateOrderSettiing(orderSetting);
    }
}
