//package org.chen.cache;
//
//import lombok.extern.slf4j.Slf4j;
//import org.chen.domain.entity.City;
//import org.chen.service.ICityService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
///**
// * 缓存bean初始化配置
// *
// * @author YuChen
// * @date 2020/7/22 11:47
// **/
//
//@Configuration
//@Slf4j
//public class CacheInitConfig {
//
//
//    @Bean
//    public BaseCache<List<City>,ICityService> serviceAddressCache(ICityService cityService){
//        BaseCache<List<City>,ICityService> res = null;
//        int count = cityService.count();
//        log.info("city数据量:{}",count);
//        //res = new CityCache();
//        res = new CityCacheWithoutLock();
//        res.init(cityService);
//        return res;
//    }
//
//}
