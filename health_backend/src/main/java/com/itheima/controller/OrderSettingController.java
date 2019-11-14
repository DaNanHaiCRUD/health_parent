package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lizefeng
 * @date 2019/11/14 11:26
 */
@RequestMapping("/ordersetting")
@RestController
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 解析excel表格数据添加到数据库
     *
     * @param excelFile
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam(name = "excelFile") MultipartFile excelFile) {
        try {
            //读取文件信息
            List<String[]> strings = POIUtils.readExcel(excelFile);
            if (strings != null && strings.size() > 0) {
                List<OrderSetting> orderSettings = new ArrayList<>();
                for (int i = 0; i < strings.size(); i++) {
                    String[] s1 = strings.get(i);
                    orderSettings.add(new OrderSetting(new Date(s1[0]), Integer.parseInt(s1[1])));
                }
                orderSettingService.upload(orderSettings);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }

        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    /**
     * 查询数据库中的预约信息
     * @param date
     * @return
     */
    @RequestMapping("/getOrderSettingDate")
    public Result getOrderSettingDate(String date) {
        List<Map> orderSettingDate = null;
        try {
            orderSettingDate = orderSettingService.getOrderSettingDate(date);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL, orderSettingDate);
        }
        return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, orderSettingDate);
    }

    /**
     * 修改预约信息
     * @param orderSetting
     * @return
     */
    @RequestMapping("/updateOrderSettingNumber")
    public Result updateOrderSettingNumber(@RequestBody OrderSetting orderSetting) {
        try {
             orderSettingService.updateOrderSettingNumber(orderSetting);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL,"网络延迟，请稍后再试");
        }
        return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
    }

}
