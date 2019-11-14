import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.itheima.dao.CheckGroupMapper;
import com.itheima.dao.CheckItemMapper;
import com.itheima.dao.OrderSettingMapper;
import com.itheima.dao.SetmealMapper;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.OrderSetting;
import com.itheima.pojo.Setmeal;
import com.itheima.utils.QiniuUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisPool;

import javax.persistence.Temporal;
import javax.swing.plaf.synth.Region;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author lizefeng
 * @date 2019/11/6 20:13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-dao.xml")
public class mytest {

    @Autowired
    private CheckItemMapper mapper;
    @Autowired
    private CheckGroupMapper groupMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private OrderSettingMapper orderSettingMapper;


    @Test
    public void test1(){
        CheckGroup checkGroup = new CheckGroup();
//        checkGroup.setCode("0011");
//        checkGroup.setName("测试代码");
        checkGroup.setHelpCode("YBJC");
        CheckGroup checkGroup1 = groupMapper.selectOne(checkGroup);
        System.out.println(checkGroup1);
//        int i = groupMapper.addGroup(checkGroup);
//        System.out.println(i);
//        System.out.println(checkGroup.getId());
    }

    @Test
    public void test2() {
        String s = "e373b2eb-0e50-4e95-a09b-03f2c1ee1d351.jpg";
        int i = s.lastIndexOf(".");
        String substring = s.substring(i);
        substring = UUID.randomUUID() + substring;
        substring = substring.replaceAll("-","");
        System.out.println(substring);

    }

    @Test
    public void test3(){
        Setmeal setmeal = new Setmeal();
        setmeal.setName("测试");
        setmeal.setCode("0099");
        int i = setmealMapper.insertSetmeal(setmeal);
        System.out.println(setmeal.getId());
    }

    @Test
    public void test4(){
        PageHelper.startPage(1,10);
        Page<Setmeal> setmeals = (Page<Setmeal>) setmealMapper.selectAll();
        for (Setmeal setmeal : setmeals) {
            System.out.println(setmeal);
        }
        System.out.println(setmeals.getTotal());
    }

    @Test
    public void test04(){
        Date date = new Date();
        date.setYear(2019-(3919-2019));
        date.setMonth(10);
        date.setDate(14);
        /*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        System.out.println(format);*/

        /*OrderSetting orderSetting = new OrderSetting();
        orderSetting.setOrderDate(date);
        OrderSetting orderSetting1 = orderSettingMapper.selectOne(orderSetting);
        System.out.println(orderSetting1);*/
    }

}
