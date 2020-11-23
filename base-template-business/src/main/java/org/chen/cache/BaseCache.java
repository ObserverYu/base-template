package org.chen.cache;

/**
 * 缓存接口
 *  
 * @author YuChen
 * @date 2020/7/22 11:48
 **/
 
public interface BaseCache<D,T> {
    D getCache();

    void init(T t);

    void update();
}
