package org.chen.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.chen.config.leaf.SnowflakeService;
import org.chen.domain.entity.Banner;
import org.chen.domain.entity.OrderTest;
import org.chen.domain.entity.ShopTest;
import org.chen.domain.entity.Voice;
import org.chen.domain.result.OrderInsertResult;
import org.chen.framework.annotion.LoggingFlag;
import org.chen.framework.annotion.SaveRequestTimeFlag;
import org.chen.manager.OrderTestManger;
import org.chen.service.IBannerService;
import org.chen.service.IOrderTestService;
import org.chen.service.IShopTestService;
import org.chen.service.IVoiceService;
import org.chen.util.leaf.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Properties;

/**
 * @author YuChen
 * @date 2020/4/16 17:57
 **/

@Slf4j
@Controller
@RequestMapping(value = "/test/",produces = "application/json;charset=UTF-8")
public class TestController {

    @Autowired
    private IOrderTestService orderTestService;

    @Autowired
    private IBannerService bannerService;

    @Autowired
    private IVoiceService voiceService;

    @Autowired
    private IShopTestService shopTestService;

    @Autowired
    private OrderTestManger orderTestManger;

    @Autowired
    private SnowflakeService snowflakeService;

    @PostMapping("/testInsertOrder2")
    @LoggingFlag(logging = true)
    @SaveRequestTimeFlag
    @ResponseBody
    public Long testInsertOrder2(Long count){
        long start = System.currentTimeMillis();
        for (long i = 0; i < count; i++) {
            OrderTest orderTest = new OrderTest();
            long userId = RandomUtil.randomLong(1, 1000);
            orderTest.setUserId(userId);
            orderTestService.save(orderTest);
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    @PostMapping("/testInsertOrder3")
    @LoggingFlag(logging = true)
    @SaveRequestTimeFlag
    @ResponseBody
    public Long testInsertOrder3(Long count){
        long start = System.currentTimeMillis();
        for (long i = 0; i < count; i++) {
            OrderTest orderTest = new OrderTest();
            Result id = snowflakeService.getId(null);
            orderTest.setOrderId(id.getId());
            long userId = RandomUtil.randomLong(1, 1000);
            orderTest.setUserId(userId);
            orderTestService.save(orderTest);
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    @PostMapping("/testInsertOrder")
    @LoggingFlag(logging = true)
    @SaveRequestTimeFlag
    @ResponseBody
    public OrderTest testInsertOrder(@RequestBody OrderTest orderTest){
        orderTest.setOrderId(null);
        orderTestService.save(orderTest);
        return orderTest;
    }

    @PostMapping("/testInsertOrderTransaction")
    @LoggingFlag(logging = true)
    @SaveRequestTimeFlag
    @ResponseBody
    public OrderInsertResult testInsertOrderTransaction(@RequestBody OrderTest orderTest){
        return orderTestManger.testInsertOrderTransaction(orderTest);
    }

    @PostMapping("/testInsertOrderTransaction2")
    @LoggingFlag(logging = true)
    @SaveRequestTimeFlag
    @ResponseBody
    public OrderInsertResult testInsertOrderTransaction2(@RequestBody OrderTest orderTest){
        return orderTestManger.testInsertOrderTransaction2(orderTest);
    }


    @GetMapping("/testGetOrderById")
    @LoggingFlag(logging = true)
    @SaveRequestTimeFlag
    @ResponseBody
    public List<OrderTest> testGetOrderById(@RequestBody OrderTest orderTest){
        if(orderTest.getOrderId() == null && orderTest.getUserId() == null){
            return null;
        }
        QueryWrapper<OrderTest> queryWrapper = new QueryWrapper<>();
        Long orderId = orderTest.getOrderId();
        Long userId = orderTest.getUserId();
        if(orderId != null){
            queryWrapper.eq("order_id",orderId);
        }
        if(userId != null){
            queryWrapper.eq("user_id",userId);
        }
        return orderTestService.list(queryWrapper);
    }

    @GetMapping("/testListBanner")
    @LoggingFlag(logging = true)
    @SaveRequestTimeFlag
    @ResponseBody
    public List<Banner> testListBanner(){
        return bannerService.list();
    }


    @PostMapping("/testInsertVoice")
    @LoggingFlag(logging = true)
    @SaveRequestTimeFlag
    @ResponseBody
    public Voice testInsertVoice(@RequestBody Voice voice){
        voiceService.save(voice);
        return voice;
    }

    @GetMapping("/testGetVoice")
    @LoggingFlag(logging = true)
    @SaveRequestTimeFlag
    @ResponseBody
    public Voice testGetVoice(@RequestBody Voice voice){
        String name = voice.getName();
        if(StrUtil.isBlank(name)){
            return null;
        }
        QueryWrapper<Voice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        return voiceService.getOne(queryWrapper);
    }

    @GetMapping("/testGetShop")
    @LoggingFlag(logging = true)
    @SaveRequestTimeFlag
    @ResponseBody
    public ShopTest testGetShop(@RequestBody ShopTest shopTest){
        Long sId = shopTest.getSId();
        if(sId == null){
            return null;
        }
        return shopTestService.getById(sId);
    }

    @PostMapping("/testInsertShop")
    @LoggingFlag(logging = true)
    @SaveRequestTimeFlag
    @ResponseBody
    public ShopTest testInsertShop(@RequestBody ShopTest shopTest){
        shopTestService.save(shopTest);
        return shopTest;
    }

    @GetMapping("/getIdBySnowflake")
    @LoggingFlag(logging = true)
    @SaveRequestTimeFlag
    @ResponseBody
    public Long getIdBySnowflake()throws BlockException{
        Result id = snowflakeService.getId(null);
        return id.getId();
    }

    @GetMapping("/testLog")
    @LoggingFlag(logging = true)
    @SaveRequestTimeFlag
    @ResponseBody
    public void testLog()throws BlockException{
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
    }


    public static void main(String[] args){
        watchSnowflackId();
    }

    public static void watchSnowflackId(){
        SnowflakeShardingKeyGenerator snowflakeShardingKeyGenerator = new SnowflakeShardingKeyGenerator();
        Comparable<?> comparable = snowflakeShardingKeyGenerator.generateKey();
        Properties properties = snowflakeShardingKeyGenerator.getProperties();
        System.out.println("==================================== shardingsphere 自己的实现  workerId = 0 =================================");
        System.out.println("起始时间: Tue Nov 01 00:00:00 CST 2016   1477929600000L");
        System.out.println("id:"+comparable);
        Long s = (Long) comparable;
        String trans = trans(s);
        System.out.println("二进制:"+trans);
        System.out.println("二进制位数:"+trans.length());

        properties.setProperty("worker.id","3");
        snowflakeShardingKeyGenerator.setProperties(properties);
        System.out.println("==================================== shardingsphere 自己的实现  workerId = 3 ==================================");
        System.out.println("起始时间: Tue Nov 01 00:00:00 CST 2016   1477929600000L");
        Comparable<?> comparable1 = snowflakeShardingKeyGenerator.generateKey();
        System.out.println("id:"+comparable1);
        Long s1 = (Long) comparable1;
        String trans1 = trans(s1);
        System.out.println("二进制:"+trans1);
        System.out.println("二进制位数:"+trans1.length());

        System.out.println("====================================== hutool 的实现  workerId = 3 dataCenterId = 3 ============================");
        System.out.println("起始时间: Thu, 04 Nov 2010 01:42:54 GMT   1288834974657L");
        Snowflake snowflake = new Snowflake(3,3);
        long s2 = snowflake.nextId();
        String trans2 = trans(s2);
        System.out.println("id:"+s2);
        System.out.println("二进制:"+trans2);
        System.out.println("二进制位数:"+trans2.length());

        System.out.println("======================================= meituan 的实现  workerId = 0 ============================================");
        System.out.println("起始时间: Thu, 04 Nov 2010 01:42:54 GMT   1288834974657L");
        long s3 = 1296355248486481986L;
        String trans3 = trans(s3);
        System.out.println("id:"+s3);
        System.out.println("二进制:"+trans3);
        System.out.println("二进制位数:"+trans3.length());
    }

    public static String trans(Long l){
        int i = 1;
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < 64; j++) {
            long r = l & i;
            stringBuilder.append(r);
            l = l >> 1;
        }
        return stringBuilder.reverse().toString();
    }
}
