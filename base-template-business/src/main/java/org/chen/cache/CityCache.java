package org.chen.cache;

import lombok.extern.slf4j.Slf4j;
import org.chen.domain.entity.City;
import org.chen.service.ICityService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 地址缓存
 *  
 * @author YuChen
 * @date 2020/7/13 10:35
 **/

@Slf4j
public class CityCache implements BaseCache<List<City>,ICityService> {

    // 读写锁
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock ();
    private final ReentrantReadWriteLock.ReadLock readerLock = lock.readLock();
    private final ReentrantReadWriteLock.WriteLock writerLock = lock.writeLock();
    // 地址
    private static List<City> list = null;

    private ICityService cityService;

    @Override
    public void init(ICityService cityService){
        this.cityService = cityService;
        update();
    }
    /**
    * 更新
    *
    * @param
    * @return
    * @author YuChen
    * @date 2020/7/13 10:49
    */
    @Override
    public void update() {
        writerLock.lock();
        try{
            CityCache.list = cityService.list();
        }finally {
            writerLock.unlock();
        }
    }

    /**
    * 获取
    *
    * @param
    * @return
    * @author YuChen
    * @date 2020/7/13 10:49
    */
    @Override
    public List<City> getCache(){
        // 如果数据库查询时间过长  可用双缓存重构 消除接口在获取时的可能的锁停顿 有空再说
        readerLock.lock();
        List<City> res = new ArrayList<>();
        try{
            if(CityCache.list == null){
                return null;
            }
            if(CityCache.list.size() > 5000){
                log.warn("数量过多,可能造成GC压力,需要重构");
            }
            for(City address : CityCache.list){
                res.add(address.clone());
            }
        }finally {
            readerLock.unlock();
        }
        return res;
    }
}
