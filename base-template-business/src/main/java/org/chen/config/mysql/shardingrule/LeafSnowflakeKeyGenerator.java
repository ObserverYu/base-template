package org.chen.config.mysql.shardingrule;

import org.chen.config.leaf.SnowflakeService;
import org.chen.util.SpringContextUtils;
import org.chen.util.leaf.common.Result;
import org.chen.util.leaf.common.Status;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;

import java.util.Properties;

/**
 * 
 *  
 * @author YuChen
 * @date 2020/8/25 10:05
 **/

@Slf4j
public class LeafSnowflakeKeyGenerator implements ShardingKeyGenerator {

    private SnowflakeService snowflakeService;

    /**
     * Generate key.
     *
     * @return generated key
     */
    @Override
    public Comparable<?> generateKey() {
        if(snowflakeService == null){
            getService();
        }
        Result res = snowflakeService.getId(null);
        if(res.getStatus().equals(Status.EXCEPTION)){
            log.error("获取分布式主键出现异常,res:{}",res);
            throw new RuntimeException("获取分布式主键出现异常");
        }
        return res.getId();
    }

    private synchronized void getService() {
        if(snowflakeService == null){
            snowflakeService = (SnowflakeService)SpringContextUtils.getApplicationContext().getBean("SnowflakeService");
        }
    }

    /**
     * Get algorithm type.
     *
     * @return type
     */
    @Override
    public String getType() {
        return "LEFT-SNOWFLAKE";
    }

    /**
     * Get properties.
     *
     * @return properties of algorithm
     */
    @Override
    public Properties getProperties() {
        return null;
    }

    /**
     * Set properties.
     *
     * @param properties properties of algorithm
     */
    @Override
    public void setProperties(Properties properties) {

    }
}
