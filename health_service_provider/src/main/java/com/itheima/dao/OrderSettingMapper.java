package com.itheima.dao;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author lizefeng
 * @date 2019/11/14 11:42
 */
public interface OrderSettingMapper extends Mapper<OrderSetting> {

    /**
     * 修改预约数据
     * @param orderSetting
     */
    void updateOrderSettiing(OrderSetting orderSetting);

    /**
     * 新增预约数据
     * @param orderSetting
     */
    void insertOrderSettiing(OrderSetting orderSetting);

    /**
     * 查询数据库中的预约信息
     * @param begin
     * @param end
     * @return
     */
    List<OrderSetting> getOrderSettingDate(@Param("begin") String begin, @Param("end") String end);

}
