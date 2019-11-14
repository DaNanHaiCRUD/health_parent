package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author lizefeng
 * @date 2019/11/14 11:37
 */
public interface OrderSettingService {

    /**
     * 导入预约数据
     * @param orderSettings
     * @return
     */
    void upload(List<OrderSetting> orderSettings);

    /**
     * 查询数据库中的预约信息
     * @param date
     * @return
     */
    List<Map> getOrderSettingDate(String date);

    /**
     * 修改预约信息
     * @param orderSetting
     * @return
     */
    void updateOrderSettingNumber(OrderSetting orderSetting);
}
