package org.chen.config.mysql.shardingrule;

import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;

/**
 * 用户范围分片算法
 *  
 * @author YuChen
 * @date 2020/8/24 9:47
 **/
 
public class UserRangeShardingAlgorithm implements RangeShardingAlgorithm<Long>{

    /**
     * Sharding.
     *
     * @param availableTargetNames available data sources or tables's names
     * @param shardingValue        sharding value
     * @return sharding results for data sources or tables's names
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
        return null;
    }
}
