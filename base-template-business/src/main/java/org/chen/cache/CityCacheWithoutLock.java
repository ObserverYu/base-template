package org.chen.cache;

import org.chen.domain.entity.City;
import org.chen.service.ICityService;
import lombok.extern.slf4j.Slf4j;
import org.chen.domain.entity.City;
import org.chen.service.ICityService;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 地址缓存-双缓存-无读锁无读停顿
 *
 * @author YuChen
 * @date 2020/7/13 10:35
 **/

@Slf4j
public class CityCacheWithoutLock implements BaseCache<List<City>, ICityService> {

    private static final ReentrantLock lock = new ReentrantLock();

    // 地址 双缓存防止锁停顿
    private static List<City> list1 = null;
    private static List<City> list2 = null;

    // 标记
    private static volatile int use;


    private ICityService cityService;

    @Override
    public void init(ICityService cityService) {
        this.cityService = cityService;
        update();
        use = 1;
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
        lock.lock();
        try {
            switch (CityCacheWithoutLock.use) {
                case 1:
                    CityCacheWithoutLock.list2 = cityService.list();
                    CityCacheWithoutLock.use = 2;
                    break;
                case 2:
                    CityCacheWithoutLock.list1 = cityService.list();
                    CityCacheWithoutLock.use = 1;
                case 0:
                    CityCacheWithoutLock.list1 = cityService.list();
                    CityCacheWithoutLock.use = 1;
                    break;
                default:
                    break;
            }
        } finally {
            lock.unlock();
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
    public List<City> getCache() {
        switch (CityCacheWithoutLock.use) {
            case 1:
                return CityCacheWithoutLock.list1;
            case 2:
                return CityCacheWithoutLock.list2;
            default:
                return null;
        }
    }
}
